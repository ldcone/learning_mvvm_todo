package com.example.learning_mvvm_todo.data.repository

import com.example.learning_mvvm_todo.data.entity.ToDoEntity

class TestToDoRepository:ToDoRepository {

    private val toDoList: MutableList<ToDoEntity> = mutableListOf()

    override suspend fun  getToDoList(): List<ToDoEntity>{
        return toDoList
    }

    override suspend fun inserToDoItem(toDoItem: ToDoEntity): Long {
        this.toDoList.add(toDoItem)
        return toDoItem.id
    }

    override suspend fun insertToDoList(toDoLIst: List<ToDoEntity>) {
        this.toDoList.addAll(toDoLIst)
    }



    override suspend fun updateToDoItem(toDoItem: ToDoEntity) :Boolean{
        val foundToDoEntity = toDoList.find {  it.id == toDoItem.id}
        return if(foundToDoEntity == null) false
        else{
            this.toDoList[toDoList.indexOf(foundToDoEntity)]=toDoItem
            true
        }

    }

    override suspend fun getToDoItem(itemId: Long): ToDoEntity? {
        return toDoList.find { it.id == itemId }
    }

    override suspend fun deleteAll() {
        toDoList.clear()
    }

    override suspend fun deleteToDoItem(id: Long): Boolean {
        val foundToDoEntity = toDoList.find {  it.id == id}
        return if(foundToDoEntity == null) false
        else{
            this.toDoList.removeAt(toDoList.indexOf(foundToDoEntity))
            true
        }    }


}