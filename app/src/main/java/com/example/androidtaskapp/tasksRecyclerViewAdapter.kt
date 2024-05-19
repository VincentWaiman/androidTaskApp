package com.example.androidtaskapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TasksRecyclerViewAdapter(private val tasksExampleModels: ArrayList<TasksExample>) : RecyclerView.Adapter<TasksRecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = tasksExampleModels[position]
        holder.titleTextView.text = task.title
        holder.descTextView.text = task.description
    }

    override fun getItemCount(): Int {
        return tasksExampleModels.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        val descTextView: TextView = itemView.findViewById(R.id.textViewDescription)
    }
}

