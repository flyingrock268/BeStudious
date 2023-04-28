package com.example.beStudious.TaskList

interface TaskItemClickListener {

    fun editTaskItem(taskItem: TaskItem)
    fun completeTaskItem(taskItem: TaskItem)
    fun undoTaskItem(taskItem: TaskItem)
    fun removeTaskItem(taskItem: TaskItem)
}