package com.example.learning_mvvm_todo.di

import com.example.learning_mvvm_todo.data.repository.TestToDoRepository
import com.example.learning_mvvm_todo.data.repository.ToDoRepository
import com.example.learning_mvvm_todo.domain.todo.*
import com.example.learning_mvvm_todo.presentation.detail.DetailMode
import com.example.learning_mvvm_todo.presentation.detail.DetailViewModel
import com.example.learning_mvvm_todo.presentation.list.ListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appTestModule = module {
    //ViewModel
    viewModel { ListViewModel(get(),get(),get()) }
    viewModel {(detailMode: DetailMode, id:Long) -> DetailViewModel(
        detailMode = detailMode,
        id= id,
        get(),
        get(),
        get(),
        get()
    )}

    //UseCase
    factory { GetToDoListUseCase(get())}
    factory { InsertToDoListUseCase(get())}
    factory { InsertToDoItemUseCase(get())}
    factory { UpdateToDoUseCase(get() )}
    factory { GetToDoItemUseCase(get())}
    factory { DeleteAllToDoItemUseCase(get()) }
    factory { DeleteToDoItemUseCase(get()) }


    //Repository
    single<ToDoRepository> {TestToDoRepository()}
}