package com.example.learning_mvvm_todo.presentation.detail

import com.example.learning_mvvm_todo.data.entity.ToDoEntity

sealed class ToDoDetailState{
    object UnInitialized:ToDoDetailState()

    object Loading: ToDoDetailState()

    data class Success(
        val toDoItem: ToDoEntity
    ): ToDoDetailState()

    object Delete: ToDoDetailState()
    object Modify: ToDoDetailState()
    object Error: ToDoDetailState()
    object Write: ToDoDetailState()
}
