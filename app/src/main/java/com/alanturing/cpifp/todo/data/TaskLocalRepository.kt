package com.alanturing.cpifp.todo.data

import com.alanturing.cpifp.todo.model.Task

class TaskLocalRepository() {
    companion object {
        private var _INSTANCE: TaskLocalRepository? = null
        fun getInstance(): TaskLocalRepository {
            return _INSTANCE ?: TaskLocalRepository()
        }
    }

    private val _tasks = mutableListOf<Task>()

    val tasks: List<Task>
        get() = _tasks

    fun add(task: Task) {
        _tasks.add(task)
    }

    fun delete(id: Int) {
        _tasks.removeAll { it.id == id }
    }

    fun update(task: Task) {
        val index = _tasks.indexOfFirst { it.id == task.id }
        if (index != -1) {
            _tasks[index] = task
        }
    }
}
