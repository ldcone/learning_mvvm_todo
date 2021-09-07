package com.example.learning_mvvm_todo.data.repository

import android.view.KeyEvent
import com.example.learning_mvvm_todo.data.entity.ToDoEntity
import com.example.learning_mvvm_todo.data.local.db.dao.ToDoDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefalutToDoRepository(
    private val toDoDao: ToDoDao,
    private val ioDispatcher: CoroutineDispatcher
):ToDoRepository {
    override suspend fun getToDoList(): List<ToDoEntity> = withContext(ioDispatcher){
        toDoDao.getAll()
    }

    override suspend fun insertToDoList(toDoLIst: List<ToDoEntity>) = withContext(ioDispatcher){
        toDoDao.insert(toDoLIst)
    }

    override suspend fun inserToDoItem(toDoItem: ToDoEntity): Long = withContext(ioDispatcher){
        toDoDao.insert(toDoItem)
    }

    override suspend fun updateToDoItem(toDoItem: ToDoEntity):Boolean = withContext(ioDispatcher){
        toDoDao.update(toDoItem)
    }

    override suspend fun getToDoItem(itemId: Long): ToDoEntity?= withContext(ioDispatcher) {
        toDoDao.getById(itemId)
    }

    override suspend fun deleteAll() {
        toDoDao.deleteAll()
    }

    override suspend fun deleteToDoItem(id: Long): Boolean = withContext(ioDispatcher){
        toDoDao.delete(id)
    }


}