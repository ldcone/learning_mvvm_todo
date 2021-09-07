package com.example.learning_mvvm_todo.presentation.list

import com.example.learning_mvvm_todo.data.entity.ToDoEntity

sealed class ToDoListState {
    object UnInitialized: ToDoListState()
    object Loading: ToDoListState()
    data class Success(
        val toDoList: List<ToDoEntity>
    ): ToDoListState()

    object  Error: ToDoListState()
}