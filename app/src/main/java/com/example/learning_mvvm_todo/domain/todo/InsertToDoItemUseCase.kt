package com.example.learning_mvvm_todo.domain.todo

import com.example.learning_mvvm_todo.data.entity.ToDoEntity
import com.example.learning_mvvm_todo.data.repository.ToDoRepository
import com.example.learning_mvvm_todo.domain.UseCase

internal class InsertToDoItemUseCase(
    private val toDoRepository: ToDoRepository
):UseCase {
    suspend operator fun invoke(toDoItem: ToDoEntity): Long{
        return toDoRepository.inserToDoItem(toDoItem)
    }
}