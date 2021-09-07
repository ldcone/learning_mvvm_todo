package com.example.learning_mvvm_todo.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learning_mvvm_todo.data.entity.ToDoEntity
import com.example.learning_mvvm_todo.domain.todo.DeleteAllToDoItemUseCase
import com.example.learning_mvvm_todo.domain.todo.GetToDoListUseCase
import com.example.learning_mvvm_todo.domain.todo.UpdateToDoUseCase
import com.example.learning_mvvm_todo.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


/**
 * 필요한 UseCase
 * 1. [GetToDoLIstUseCase]
 * 2. [UpdateToDoUseCase]
 * 3. [DeleteAllToDoItemUseCase]
 */
internal class ListViewModel(
    private val getToDoListUseCase: GetToDoListUseCase,
    private val updateToDoUseCase: UpdateToDoUseCase,
    private val deleteAllToDoItemUseCase: DeleteAllToDoItemUseCase
): BaseViewModel(){
//    private var _toDoListLiveData = MutableLiveData<List<ToDoEntity>>()
    private var _toDoListLiveData = MutableLiveData<ToDoListState>(ToDoListState.UnInitialized)
//    val todoListLiveData: LiveData<List<ToDoEntity>> = _toDoListLiveData
    val todoListLiveData: LiveData<ToDoListState> = _toDoListLiveData

    override fun fetchData(): Job = viewModelScope.launch{
        _toDoListLiveData.postValue(ToDoListState.Loading)
        _toDoListLiveData.postValue(ToDoListState.Success(getToDoListUseCase()))
//        _toDoListLiveData.postValue(getToDoListUseCase()!!)
    }

    override fun deleteTodo(): Job {
        TODO("Not yet implemented")
    }

    fun updateEntity(todoEntity:ToDoEntity)= viewModelScope.launch {
        updateToDoUseCase(todoEntity)

//        val success = updateToDoUseCase(todoEntity)
    }

    fun deleteAll() = viewModelScope.launch{
        _toDoListLiveData.postValue(ToDoListState.Loading)
        deleteAllToDoItemUseCase()
        _toDoListLiveData.postValue(ToDoListState.Success(getToDoListUseCase()))

//        deleteAllToDoItemUseCase()
//        _toDoListLiveData.postValue(listOf())
    }
}