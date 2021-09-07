package com.example.learning_mvvm_todo.domain.todo

import com.example.learning_mvvm_todo.data.repository.ToDoRepository
import com.example.learning_mvvm_todo.domain.UseCase

internal class DeleteAllToDoItemUseCase(
    private val toDoRepository: ToDoRepository
): UseCase {

    suspend operator fun invoke(){
        return toDoRepository.deleteAll()
    }

}