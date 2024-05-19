package com.example.androidtaskapp

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController

/**
 * A simple [Fragment] subclass.
 * Use the [NewTaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewTaskFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_task, container, false)

        val taskCategory = view.findViewById<AutoCompleteTextView>(R.id.taskCategory)
        val taskTitle = view.findViewById<EditText>(R.id.editTextTitle)
        val taskDescription = view.findViewById<EditText>(R.id.editTextDescription)
        val addTaskButton = view.findViewById<Button>(R.id.addTaskButton)
        val backToMainButton = view.findViewById<Button>(R.id.cancelButton)
        val category = resources.getStringArray(R.array.categoryTypes)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu, category)
        taskCategory.setAdapter(arrayAdapter)

        addTaskButton.setOnClickListener {
            // Check if any field is empty
            if (taskTitle.text.isEmpty() || taskDescription.text.isEmpty() || taskCategory.text.isEmpty()) {
                // Show a Toast message if any field is empty
                Toast.makeText(requireContext(), "Please fill all fields to add a task", Toast.LENGTH_SHORT).show()
            } else {
                // Put your add task function here
                // For example, save the task to your database or list

                // Navigate back to the main fragment
                val navController = view.findNavController()
            }
        }

        backToMainButton.setOnClickListener {
            // Ask for a nav controller
            val navController = view.findNavController()
            // Navigate to the main fragment
            navController.navigate(R.id.action_newTaskFragment_to_mainFragment)
        }
        return view
    }
}
