package com.example.androidtaskapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TasksRecyclerViewAdapter(private val tasksInfoModels: ArrayList<TaskInfo>) : RecyclerView.Adapter<TasksRecyclerViewAdapter.MyViewHolder>() {

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
        val task = tasksInfoModels[position]
        holder.titleTextView.text = task.title
        holder.descTextView.text = task.description

        // Change the color of viewSquare based on the task status
        val statusColor = when (task.status) {
            "0" -> android.R.color.holo_red_light // Red color for status 0
            "1" -> android.R.color.holo_orange_light // Yellow color for status 1
            "2" -> android.R.color.holo_green_light // Green color for status 2
            else -> android.R.color.transparent // Default color if status is unknown
        }
        holder.viewSquare.setBackgroundResource(statusColor)
    }

    override fun getItemCount(): Int {
        return tasksInfoModels.size
    }

    inner class MyViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        val descTextView: TextView = itemView.findViewById(R.id.textViewDescription)
        val viewSquare: View = itemView.findViewById(R.id.viewSquare)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
}

