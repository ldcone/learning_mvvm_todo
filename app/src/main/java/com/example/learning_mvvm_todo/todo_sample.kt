package com.example.learning_mvvm_todo

import android.app.Application
import com.example.learning_mvvm_todo.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class todo_sample: Application() {
    override fun onCreate() {
        super.onCreate()
        // TODO Koin Trigger
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@todo_sample)
            modules(appModule)
        }
    }
}