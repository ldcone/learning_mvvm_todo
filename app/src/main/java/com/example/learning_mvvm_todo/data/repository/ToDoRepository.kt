package com.example.learning_mvvm_todo.data.repository

import com.example.learning_mvvm_todo.data.entity.ToDoEntity

/**
 * 1.insertToDoList
 * 2.getToDoList
 * 3.updateToDoItem
 */

interface ToDoRepository {

    suspend fun getToDoList(): List<ToDoEntity>

    suspend fun inserToDoItem(toDoItem: ToDoEntity) :Long

    suspend fun insertToDoList(toDoLIst:List<ToDoEntity>)

    suspend fun updateToDoItem(toDoItem: ToDoEntity)

    suspend fun getToDoItem(itemId: Long): ToDoEntity?

    suspend fun deleteAll()

    suspend fun deleteToDoItem(id: Long)

}