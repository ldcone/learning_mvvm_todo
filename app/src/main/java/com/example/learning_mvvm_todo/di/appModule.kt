package com.example.learning_mvvm_todo.di

import android.content.Context
import androidx.room.Room
import com.example.learning_mvvm_todo.data.local.db.ToDoDatabase
import com.example.learning_mvvm_todo.data.repository.DefalutToDoRepository
import com.example.learning_mvvm_todo.data.repository.ToDoRepository
import com.example.learning_mvvm_todo.domain.todo.*
import com.example.learning_mvvm_todo.domain.todo.DeleteAllToDoItemUseCase
import com.example.learning_mvvm_todo.domain.todo.DeleteToDoItemUseCase
import com.example.learning_mvvm_todo.domain.todo.GetToDoItemUseCase
import com.example.learning_mvvm_todo.domain.todo.GetToDoListUseCase
import com.example.learning_mvvm_todo.domain.todo.InsertToDoItemUseCase
import com.example.learning_mvvm_todo.domain.todo.InsertToDoListUseCase
import com.example.learning_mvvm_todo.domain.todo.UpdateToDoUseCase
import com.example.learning_mvvm_todo.presentation.detail.DetailMode
import com.example.learning_mvvm_todo.presentation.detail.DetailViewModel
import com.example.learning_mvvm_todo.presentation.list.ListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {

    viewModel { ListViewModel(get(),get(),get()) }
    viewModel {(detailMode: DetailMode, id:Long) -> DetailViewModel(
        detailMode = detailMode,
        id= id,
        get(),
        get(),
        get(),
        get()
    )
    }

    //UseCase
    factory { GetToDoListUseCase(get()) }
    factory { InsertToDoListUseCase(get()) }
    factory { InsertToDoItemUseCase(get()) }
    factory { UpdateToDoUseCase(get() ) }
    factory { GetToDoItemUseCase(get()) }
    factory { DeleteAllToDoItemUseCase(get()) }
    factory { DeleteToDoItemUseCase(get()) }


    //Repository
    single<ToDoRepository> {DefalutToDoRepository(get(), get())}

    single{ provideDB(androidApplication())}
    single { providedToDoDao(get()) }
}

internal fun provideDB(context: Context):ToDoDatabase =
    Room.databaseBuilder(context,ToDoDatabase::class.java,ToDoDatabase.DB_NAME).build()

internal fun providedToDoDao(database: ToDoDatabase) = database.toDoDao()