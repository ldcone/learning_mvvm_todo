package com.example.learning_mvvm_todo.viewmodel.todo

import com.example.learning_mvvm_todo.ViewModelTest
import com.example.learning_mvvm_todo.data.entity.ToDoEntity
import com.example.learning_mvvm_todo.domain.todo.InsertToDoItemUseCase
import com.example.learning_mvvm_todo.domain.todo.InsertToDoListUseCase
import com.example.learning_mvvm_todo.presentation.detail.DetailMode
import com.example.learning_mvvm_todo.presentation.detail.DetailViewModel
import com.example.learning_mvvm_todo.presentation.detail.ToDoDetailState
import com.example.learning_mvvm_todo.presentation.list.ListViewModel
import com.example.learning_mvvm_todo.presentation.list.ToDoListState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import org.koin.experimental.property.inject
import java.lang.Exception

/**
 * [DetailViewModel]를 테스트 하기 위한 unit test class
 *
 * 1. initData()
 * 2. test viewModel fetch
 * 3. test delete todo
 * 4. test update todo
 */

@ExperimentalCoroutinesApi
internal class DetailViewModelTest: ViewModelTest() {
    private val id =1L
    private val detailViewModel by inject<DetailViewModel>{parametersOf(DetailMode.DETAIL,id)}
    private val listviewModel by inject<ListViewModel>()
    private val insertToDoItemUseCase: InsertToDoItemUseCase by inject ()
    private val todo = ToDoEntity(
        id = id,
        title ="title $id",
        description = "description $id",
        hasCompleted = false
    )

    @Before
    fun init(){
        initData()
    }
    private fun initData() = runBlockingTest {
        insertToDoItemUseCase(todo)
    }

    @Test
    fun test_viewModel_fetch() = runBlockingTest {
        val testObservable = detailViewModel.todoDetailLiveData.test()
        detailViewModel.fetchData()

        testObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Success(todo)
            )
        )
    }

    @Test
    fun test_delete_todo() = runBlockingTest {
        val detailTestObserverable = detailViewModel.todoDetailLiveData.test()
        detailViewModel.deleteTodo()

        detailTestObserverable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Delete
            )
        )
        val listTestObserver = listviewModel.todoListLiveData.test()
        listviewModel.fetchData()
        listTestObserver.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Success(listOf())
            )
        )

    }

    @Test
    fun test_update_todo() = runBlockingTest {
        val testObservable = detailViewModel.todoDetailLiveData.test()

        val updateTitle = "title 1 update"
        val updateDescription = " description 1 update"

        val updateToDo = todo.copy(
            title = updateTitle,
            description = updateDescription
        )

        detailViewModel.writeToDo(
            title = updateTitle,
            description = updateDescription
        )

        testObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Success(updateToDo)
            )
        )
    }


}