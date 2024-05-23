package com.example.androidtaskapp

import MainViewModel
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.androidtaskapp.databinding.FragmentMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel

    private lateinit var binding: FragmentMainBinding

    private lateinit var goToTasks: Button

    private lateinit var addTask: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

//        this.binding.task = mainViewModel
//        this.binding.lifecycleOwner = viewLifecycleOwner

        goToTasks = view.findViewById<Button>(R.id.goToTasksButton)
        addTask = view.findViewById<FloatingActionButton>(R.id.addTaskButton)

        goToTasks.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_mainFragment_to_taskSelection)
        }

        addTask.setOnClickListener {
            val navController = view.findNavController()
            navController.navigate(R.id.action_mainFragment_to_newTaskFragment)
        }

        // Observe the tasks data
        mainViewModel.tasks.observe(viewLifecycleOwner, Observer { taskList ->
            // Update UI with taskList
            Log.e("MainFragment", "Observed task list: $taskList")
        })

        // Observe status counts
        mainViewModel.statusZeroCount.observe(viewLifecycleOwner, Observer { count ->
            Log.e("MainFragment", "Status 0 count: $count")
            view.findViewById<TextView>(R.id.textNewCount).text = "$count tasks"
        })

        mainViewModel.statusOneCount.observe(viewLifecycleOwner, Observer { count ->
            Log.e("MainFragment", "Status 1 count: $count")
            view.findViewById<TextView>(R.id.textInProgressCount).text = "$count tasks"
        })

        mainViewModel.statusTwoCount.observe(viewLifecycleOwner, Observer { count ->
            Log.e("MainFragment", "Status 2 count: $count")
            view.findViewById<TextView>(R.id.textDoneCount).text = "$count tasks"
        })

        return view
    }
}
