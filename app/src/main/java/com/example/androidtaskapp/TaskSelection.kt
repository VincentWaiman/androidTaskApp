package com.example.androidtaskapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * A simple [Fragment] subclass.
 * Use the [TaskSelection.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskSelection : Fragment() {
    val tasksExampleModels = ArrayList<TasksExample>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_task_selection, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        setUpTasksModels()
        val adapter = TasksRecyclerViewAdapter(tasksExampleModels)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter.setOnItemClickListener(object : TasksRecyclerViewAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                context?.let {
                    Toast.makeText(it, "You clicked on item No. $position", Toast.LENGTH_SHORT).show()
                }
            }
        })

        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.normalButton -> {
                    // Handle Normal button click
                    Toast.makeText(requireContext(), "Normal selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.urgentButton -> {
                    // Handle Urgent button click
                    Toast.makeText(requireContext(), "Urgent selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.importantButton -> {
                    // Handle Important button click
                    Toast.makeText(requireContext(), "Important selected", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }



        return view
    }
    private fun setUpTasksModels() {
        var taskTitles = resources.getStringArray(R.array.taskTitlesExamples)
        var taskDescs = resources.getStringArray(R.array.taskDescExamples)

        for (i in taskTitles.indices) {
            tasksExampleModels.add(TasksExample(taskTitles[i], taskDescs[i]))
        }
        Log.d("TaskSelection", "Number of tasks: ${tasksExampleModels.size}")

    }

}