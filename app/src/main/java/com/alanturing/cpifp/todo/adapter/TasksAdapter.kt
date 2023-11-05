package com.alanturing.cpifp.todo.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alanturing.cpifp.todo.databinding.TodoItemBinding
import com.alanturing.cpifp.todo.model.Task

class TasksAdapter(private val datos: List<Task>) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {
    private var onEditClickListener: ((Task) -> Unit)? = null

    inner class TaskViewHolder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTask(task: Task) {
            binding.titleTextView.text = task.title
            binding.descriptionTextView.text = task.description
            binding.completedSwitch.isChecked = task.isCompleted

            binding.editButton.setOnClickListener {
                onEditClickListener?.invoke(task)
            }

            binding.completedSwitch.setOnCheckedChangeListener { _, isChecked ->
                task.isCompleted = isChecked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }



    override fun getItemCount(): Int {
        return datos.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = datos[position]
        holder.bindTask(task)

        holder.binding.shareButton.setOnClickListener {
            val shareText = "${task.title}\n${task.description}"
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            holder.itemView.context.startActivity(Intent.createChooser(sendIntent, null))
        }

    }

    fun setOnEditClickListener(listener: (Task) -> Unit) {
        onEditClickListener = listener
    }
}
