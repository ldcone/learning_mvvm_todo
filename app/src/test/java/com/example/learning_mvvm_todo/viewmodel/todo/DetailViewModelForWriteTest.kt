package com.example.learning_mvvm_todo.viewmodel.todo

import com.example.learning_mvvm_todo.ViewModelTest
import com.example.learning_mvvm_todo.data.entity.ToDoEntity
import com.example.learning_mvvm_todo.presentation.detail.DetailMode
import com.example.learning_mvvm_todo.presentation.detail.DetailViewModel
import com.example.learning_mvvm_todo.presentation.detail.ToDoDetailState
import com.example.learning_mvvm_todo.presentation.list.ListViewModel
import com.example.learning_mvvm_todo.presentation.list.ToDoListState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

/**
 * [DetailViewModel]을 테스트하기 위한 unit test class
 *
 * 1. test ViewModel fetch
 * 2. test Insert todo
 *
 */

@ExperimentalCoroutinesApi
internal class DetailViewModelForWriteTest: ViewModelTest() {
    private val id =0L
    private val detailViewModel by inject<DetailViewModel>{ parametersOf(DetailMode.WRITE,id) }
    private val listviewModel by inject<ListViewModel>()

    private val todo = ToDoEntity(
        id = id,
        title ="title $id",
        description = "description $id",
        hasCompleted = false
    )

    @Test
    fun test_viewmodel_fetch() = runBlockingTest {
        val testObservable = detailViewModel.todoDetailLiveData.test()

        detailViewModel.fetchData()

        testObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Write
            )
        )
    }
    @Test
    fun test_insert_todo()= runBlockingTest {
        val detailTestObservable = detailViewModel.todoDetailLiveData.test()
        val listTestObservable = listviewModel.todoListLiveData.test()

        detailViewModel.writeToDo(
            title = todo.title,
            description = todo.description
        )

        detailTestObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Success(todo)
            )
        )
        assert(detailViewModel.detailMode == DetailMode.DETAIL)
        assert(detailViewModel.id == id)

        //뒹로나가서 리스트 보기
        listviewModel.fetchData()
        listTestObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Success(listOf(todo))
            )
        )

    }




}