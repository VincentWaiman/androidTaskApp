package com.example.androidtaskapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TasksRecyclerViewAdapter(private val tasksExampleModels: ArrayList<TasksExample>) : RecyclerView.Adapter<TasksRecyclerViewAdapter.MyViewHolder>() {

    private lateinit var clickListener:onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        clickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_row, parent, false)
        return MyViewHolder(view,clickListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = tasksExampleModels[position]
        holder.titleTextView.text = task.title
        holder.descTextView.text = task.description
    }

    override fun getItemCount(): Int {
        return tasksExampleModels.size
    }

    inner class MyViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        val descTextView: TextView = itemView.findViewById(R.id.textViewDescription)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
}

