package com.example.androidtaskapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
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
        val addTaskButton = view.findViewById<Button>(R.id.addTaskButton)
        val backToMainButton = view.findViewById<Button>(R.id.cancelButton)
        val category= resources.getStringArray(R.array.categoryTypes)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_menu, category)
        taskCategory.setAdapter(arrayAdapter)

        addTaskButton.setOnClickListener {
//            put function here
        }

        backToMainButton.setOnClickListener {
            // ask for a nav controller
            val navController = view.findNavController()
            // navigate into certain destination
            navController.navigate(R.id.action_newTaskFragment_to_mainFragment)
        }
        return view
    }

}