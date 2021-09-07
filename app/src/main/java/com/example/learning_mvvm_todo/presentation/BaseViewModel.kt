package com.example.learning_mvvm_todo.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

internal abstract class BaseViewModel: ViewModel() {
    abstract fun fetchData(): Job
    abstract fun deleteTodo(): Job
}