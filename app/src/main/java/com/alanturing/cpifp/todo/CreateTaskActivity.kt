package com.alanturing.cpifp.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.alanturing.cpifp.todo.data.TaskLocalRepository
import com.alanturing.cpifp.todo.databinding.ActivityCreateTaskBinding
import com.alanturing.cpifp.todo.model.Task

class CreateTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateTaskBinding
    private val taskRepository = TaskLocalRepository.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCreate.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val description = binding.editTextDescription.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                val newTask = Task(taskRepository.tasks.size + 1, title, description, false)
                taskRepository.add(newTask)

                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_TASK, newTask)
                setResult(RESULT_OK, resultIntent)
                finish() // finalizacion de actividad
            }
        }

        binding.buttonCancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    companion object {
        const val EXTRA_TASK = "extra_task"
    }
}

