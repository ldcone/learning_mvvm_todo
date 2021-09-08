package com.example.learning_mvvm_todo.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learning_mvvm_todo.data.entity.ToDoEntity
import com.example.learning_mvvm_todo.domain.todo.DeleteToDoItemUseCase
import com.example.learning_mvvm_todo.domain.todo.GetToDoItemUseCase
import com.example.learning_mvvm_todo.domain.todo.InsertToDoItemUseCase
import com.example.learning_mvvm_todo.domain.todo.UpdateToDoUseCase
import com.example.learning_mvvm_todo.presentation.BaseViewModel
import com.example.learning_mvvm_todo.presentation.list.ToDoListState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

internal class DetailViewModel(
    var detailMode: DetailMode,
    var id:Long = -1,
    private val getToDoItemUseCase: GetToDoItemUseCase,
    private val deleteToDoItemUseCase: DeleteToDoItemUseCase,
    private val updateToDoUseCase: UpdateToDoUseCase,
    private val insertToDoItemUseCase: InsertToDoItemUseCase
):BaseViewModel() {

    private var _toDoDetailLiveData = MutableLiveData<ToDoDetailState>(ToDoDetailState.UnInitialized)
    val todoDetailLiveData: LiveData<ToDoDetailState> = _toDoDetailLiveData

    override fun fetchData(): Job = viewModelScope.launch {
        _toDoDetailLiveData.postValue(ToDoDetailState.Loading)
        when(detailMode){
            DetailMode.WRITE->{
                _toDoDetailLiveData.postValue(ToDoDetailState.Write)

            }
            DetailMode.DETAIL->{
                try{
                    getToDoItemUseCase(id)?.let{
                        _toDoDetailLiveData.postValue(ToDoDetailState.Success(it))
                    }?: kotlin.run{
                        _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                    _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                }

            }

        }

    }

    fun deleteTodo(): Job = viewModelScope.launch {
        _toDoDetailLiveData.postValue(ToDoDetailState.Loading)
        try{
            deleteToDoItemUseCase(id)
            _toDoDetailLiveData.postValue(ToDoDetailState.Delete)
        }catch (e:Exception){
            e.printStackTrace()
            _toDoDetailLiveData.postValue(ToDoDetailState.Error)
        }
    }

    fun writeToDo(title: String, description: String) = viewModelScope.launch {
        _toDoDetailLiveData.postValue(ToDoDetailState.Loading)
        when(detailMode){
            DetailMode.WRITE->{
                try {
                    val toDoEntity = ToDoEntity(
                    title = title,
                    description = description
                    )
                    id = insertToDoItemUseCase(toDoEntity)
                    _toDoDetailLiveData.postValue(ToDoDetailState.Success(toDoEntity))
                    detailMode = DetailMode.DETAIL
                }catch (e: Exception){
                    e.printStackTrace()
                    _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                }

            }
            DetailMode.DETAIL->{

                try{
                    getToDoItemUseCase(id)?.let {
                        val updateToDoEntity  = it.copy(
                            title = title,
                            description = description
                        )
                        updateToDoUseCase(updateToDoEntity)
                        _toDoDetailLiveData.postValue(ToDoDetailState.Success(updateToDoEntity))
                    } ?: kotlin.run {
                        _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                    _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                }

            }

        }
    }


}