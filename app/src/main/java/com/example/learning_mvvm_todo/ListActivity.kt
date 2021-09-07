package com.example.learning_mvvm_todo

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.learning_mvvm_todo.databinding.ActivityListBinding
import com.example.learning_mvvm_todo.presentation.BaseActivity
import com.example.learning_mvvm_todo.presentation.list.ListViewModel
import com.example.learning_mvvm_todo.presentation.list.ToDoListState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

internal class ListActivity : BaseActivity<ListViewModel>(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    private lateinit var binding: ActivityListBinding

    override val viewModel: ListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initViews() ->
    override fun observeData() {
        viewModel.todoListLiveData.observe(this){
            when(it){
                is ToDoListState.UnInitialized -> {
                    initViews(binding)
                }
                is ToDoListState.Loading ->{
                    handleLoadingState()
                }
                is ToDoListState.Success->{
                    handleSuccessState(it)
                }
                is ToDoListState.Error -> {
                    handleErrorState()
                }
            }
        }
    }


}