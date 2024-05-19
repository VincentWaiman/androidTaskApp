package com.example.androidtaskapp

import TaskViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * A simple [Fragment] subclass.
 * Use the [TaskSelection.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskSelection : Fragment() {

    private val allTasksExampleModels = ArrayList<TasksExample>()
    private val tasksExampleModels = ArrayList<TasksExample>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TasksRecyclerViewAdapter

    private val taskViewModel: TaskViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_task_selection, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        adapter = TasksRecyclerViewAdapter(tasksExampleModels)
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
                    filterTasksByCategory("0")
                    true
                }
                R.id.urgentButton -> {
                    filterTasksByCategory("1")
                    true
                }
                R.id.importantButton -> {
                    filterTasksByCategory("2")
                    true
                }
                else -> false
            }
        }

        // Observe the tasks LiveData
        taskViewModel.tasks.observe(viewLifecycleOwner) { taskList ->
            // Update the RecyclerView with the new task list
            val updatedTasks = taskList.map { taskInfo ->
                TasksExample(taskInfo.title, taskInfo.description, taskInfo.category)
            }
            allTasksExampleModels.clear()
            allTasksExampleModels.addAll(updatedTasks)
            filterTasksByCategory("0")
        }

        // Load tasks from ViewModel
        taskViewModel.getTasks()


        return view
    }
    private fun filterTasksByCategory(category: String) {
        val filteredTasks = if (category == "All") {
            allTasksExampleModels
        } else {
            allTasksExampleModels.filter { it.category == category }
        }
        updateRecyclerView(filteredTasks)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateRecyclerView(filteredTasks: List<TasksExample>) {
        tasksExampleModels.clear()
        tasksExampleModels.addAll(filteredTasks)
        adapter.notifyDataSetChanged()
    }

}