package com.example.beStudious.TaskList

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.beStudious.databinding.TaskItemCellBinding

class TaskItemAdapter(

    private val taskItems: List<TaskItem>,
    private val clickListener: TaskItemClickListener

): RecyclerView.Adapter<taskItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): taskItemViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = TaskItemCellBinding.inflate(from,parent, false)
        return taskItemViewHolder(parent.context, binding, clickListener)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: taskItemViewHolder, position: Int) {
        holder.bindTaskItem(taskItems[position])
    }

    override fun getItemCount(): Int =taskItems.size
}