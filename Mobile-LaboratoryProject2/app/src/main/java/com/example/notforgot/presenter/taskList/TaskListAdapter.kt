package com.example.notforgot.presenter.taskList

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notforgot.R
import com.example.notforgot.model.task.Task

class TaskListAdapter (val tasks: List<Task>): RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {



    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val colorView: View = itemView.findViewById(R.id.categoryColorView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.titleTextView.text = tasks.get(position).title
        holder.descriptionTextView.text = tasks.get(position).description
        //holder.colorView.setBackgroundColor(Color.parseColor(tasks.get(position).priority_color))
    }
}