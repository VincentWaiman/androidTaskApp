package com.example.androidtaskapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val goToTasks = view.findViewById<Button>(R.id.goToTasksButton)
        val addTask = view.findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.addTaskButton)

        goToTasks.setOnClickListener{
            // ask for a nav controller
            val navController = view.findNavController()
            // navigate into certain destination
            navController.navigate(R.id.action_mainFragment_to_taskSelection)
        }

        addTask.setOnClickListener{
            // ask for a nav controller
            val navController = view.findNavController()
            // navigate into certain destination
            navController.navigate(R.id.action_mainFragment_to_newTaskFragment)
        }
        return view
    }
}