package com.alanturing.cpifp.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.alanturing.cpifp.todo.databinding.ActivityEditTaskBinding
import com.alanturing.cpifp.todo.model.Task

class EditTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditTaskBinding
    private lateinit var task: Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        task = (intent.getSerializableExtra(EXTRA_TASK) as? Task)!!

        if (task == null) {
            finish()
            return
        }

        binding.editTextTitle.setText(task.title)
        binding.editTextDescription.setText(task.description)

        val checkBoxCompleted = binding.checkBoxCompleted
        checkBoxCompleted.isChecked = task.isCompleted

        binding.buttonUpdate.setOnClickListener {
            val updatedTitle = binding.editTextTitle.text.toString()
            val updatedDescription = binding.editTextDescription.text.toString()
            val updatedCompleted = checkBoxCompleted.isChecked

            if (updatedTitle.isNotEmpty() && updatedDescription.isNotEmpty()) {
                task = task.copy(
                    title = updatedTitle,
                    description = updatedDescription,
                    isCompleted = updatedCompleted
                )

                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_TASK, task)
                setResult(RESULT_OK, resultIntent)
                finish()
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
