package com.example.learning_mvvm_todo.presentation.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isGone
import com.example.learning_mvvm_todo.R
import com.example.learning_mvvm_todo.databinding.ActivityDetail2Binding
import com.example.learning_mvvm_todo.databinding.ActivityDetailBinding
import com.example.learning_mvvm_todo.databinding.ActivityListBinding
import com.example.learning_mvvm_todo.presentation.BaseActivity
import kotlinx.coroutines.CoroutineScope
import org.koin.android.viewmodel.compat.ScopeCompat.viewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

internal class DetailActivity :BaseActivity<DetailViewModel>(){

    companion object{
        const val TODO_ID_KEY = "ToDoId"
        const val DETAIL_MODE_KEY = "DetailMode"

        const val FETCH_REQUEST_CODE =10

        fun getIntent(context: Context, detailMode: DetailMode) = Intent(context,DetailActivity::class.java).apply{
            putExtra(DETAIL_MODE_KEY, detailMode)
        }

        fun getIntent(context: Context, id: Long, detailMode: DetailMode) = Intent(context, DetailActivity::class.java).apply {
            putExtra(TODO_ID_KEY,id)
            putExtra(DETAIL_MODE_KEY,detailMode)
        }
    }

    override val viewModel: DetailViewModel by viewModel{
        parametersOf(
            intent.getSerializableExtra(DETAIL_MODE_KEY),
            intent.getLongExtra(TODO_ID_KEY,-1)
        )
    }

    private lateinit var binding: ActivityDetail2Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetail2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setResult(Activity.RESULT_OK)

    }

    override fun observeData() = viewModel.todoDetailLiveData.observe(this){
        when(it){
            is ToDoDetailState.UnInitialized ->{
                initViews(binding)
            }

            is ToDoDetailState.Loading ->{
                handleLoadingState()
            }

            is ToDoDetailState.Success ->{
                handleSuccessState(it)
            }

            is ToDoDetailState.Modify ->{
                handleModifyState()
            }

            is ToDoDetailState.Delete ->{
                Toast.makeText(this, "??????????????? ?????????????????????.",Toast.LENGTH_SHORT).show()
                finish()
            }

            is ToDoDetailState.Error ->{
                Toast.makeText(this, "????????? ??????????????????.",Toast.LENGTH_SHORT).show()
                finish()
            }

            is ToDoDetailState.Write ->{
                handleWirteState()

            }
        }

    }

    private fun initViews(binding: ActivityDetail2Binding) = with(binding){
        titleInput.isEnabled = false
        descriptionInput.isEnabled = false

        deleteButton.isGone = true
        modifyButton.isGone = true
        updateButton.isGone = true

        deleteButton.setOnClickListener{
            viewModel.deleteTodo()
        }

        modifyButton.setOnClickListener{
            viewModel.setModifyMode()
        }

        updateButton.setOnClickListener{
            viewModel.writeToDo(
                title = titleInput.text.toString(),
                description = descriptionInput.text.toString()
            )
        }
    }

    private fun handleLoadingState() = with(binding){
        progressBar.isGone = false
    }

    private fun handleModifyState() = with(binding){
        titleInput.isEnabled = true
        descriptionInput.isEnabled = true

        deleteButton.isGone = true
        modifyButton.isGone = true
        updateButton.isGone = false
    }

    private fun handleWirteState() = with(binding){
        titleInput.isEnabled = true
        descriptionInput.isEnabled = true

        updateButton.isGone = false
    }

    private fun handleSuccessState(state: ToDoDetailState.Success) = with(binding){
        progressBar.isGone = true

        titleInput.isEnabled = false
        descriptionInput.isEnabled = false

        deleteButton.isGone = false
        modifyButton.isGone = false
        updateButton.isGone = true

        val toDoItem = state.toDoItem
        titleInput.setText(toDoItem.title)
        descriptionInput.setText(toDoItem.description)
    }




}