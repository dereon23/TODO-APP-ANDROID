package com.alanturing.cpifp.todo

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alanturing.cpifp.todo.adapter.TasksAdapter
import com.alanturing.cpifp.todo.data.TaskLocalRepository
import com.alanturing.cpifp.todo.databinding.ActivityMainBinding
import com.alanturing.cpifp.todo.model.Task


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val taskRepository = TaskLocalRepository.getInstance()
    private lateinit var taskAdapter: TasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskAdapter = TasksAdapter(taskRepository.tasks)

        binding.tasks.adapter = taskAdapter

        binding.fabCreateTask.setOnClickListener {
            val intent = Intent(this, CreateTaskActivity::class.java)
            startActivityForResult(intent, CREATE_TASK_REQUEST_CODE)
        }

        taskAdapter.setOnEditClickListener { task ->
            val intent = Intent(this, EditTaskActivity::class.java)
            intent.putExtra(EditTaskActivity.EXTRA_TASK, task)
            startActivityForResult(intent, EDIT_TASK_REQUEST_CODE)
        }
    }

    companion object {
        private const val CREATE_TASK_REQUEST_CODE = 1
        private const val EDIT_TASK_REQUEST_CODE = 2
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CREATE_TASK_REQUEST_CODE && resultCode == RESULT_OK) {
            val newTask = data?.getSerializableExtra(CreateTaskActivity.EXTRA_TASK) as? Task
            newTask?.let {
                taskRepository.add(it)
                taskAdapter.notifyDataSetChanged()
            }
        } else if (requestCode == EDIT_TASK_REQUEST_CODE && resultCode == RESULT_OK) {
            val updatedTask = data?.getSerializableExtra(EditTaskActivity.EXTRA_TASK) as? Task
            updatedTask?.let {
                taskRepository.update(it)
                taskAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        taskAdapter.notifyDataSetChanged()
    }

    // manera 2
    // https://stackoverflow.com/questions/33671196/floatingactionbutton-with-text-instead-of-image
    fun textAsBitmap(text: String?, textSize: Float, textColor: Int): Bitmap? {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = textSize
        paint.color = textColor
        paint.textAlign = Paint.Align.LEFT
        val baseline = -paint.ascent()
        val width = (paint.measureText(text) + 0.0f).toInt()
        val height = (baseline + paint.descent() + 0.0f).toInt()
        val image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(image)
        canvas.drawText(text!!, 0f, baseline, paint)
        return image
    }

}

