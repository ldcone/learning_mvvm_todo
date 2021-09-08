package com.example.learning_mvvm_todo.domain.todo

import com.example.learning_mvvm_todo.data.entity.ToDoEntity
import com.example.learning_mvvm_todo.data.repository.ToDoRepository
import com.example.learning_mvvm_todo.domain.UseCase

internal class UpdateToDoUseCase(
     private val toDoRepository:ToDoRepository
 ):UseCase {

     suspend operator fun invoke(toDoEntity: ToDoEntity){
         return toDoRepository.updateToDoItem(toDoEntity)
     }
}