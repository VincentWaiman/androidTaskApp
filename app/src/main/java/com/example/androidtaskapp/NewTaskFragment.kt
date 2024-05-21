package com.example.androidtaskapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.androidtaskapp.databinding.FragmentNewTaskBinding
import java.text.SimpleDateFormat
import java.util.Locale

class NewTaskFragment : Fragment() {

    private lateinit var newTaskModel: NewTaskModel

    private lateinit var addNewTaskButton: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_task, container, false)

        newTaskModel = ViewModelProvider(this).get(NewTaskModel::class.java)

        val taskCategory = view.findViewById<AutoCompleteTextView>(R.id.taskCategory)
        val taskTitle = view.findViewById<EditText>(R.id.editTextTitle)
        val taskDescription = view.findViewById<EditText>(R.id.editTextDescription)
        addNewTaskButton = view.findViewById<Button>(R.id.addTaskButton)
        val backToMainButton = view.findViewById<Button>(R.id.cancelButton)
        val category = resources.getStringArray(R.array.categoryTypes)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu, category)
        taskCategory.setAdapter(arrayAdapter)

        addNewTaskButton.setOnClickListener {
            newTaskModel.validateAndAddTask(
                taskTitle = taskTitle.text.toString(),
                taskDescription = taskDescription.text.toString(),
                taskCategory = taskCategory.text.toString(),
                onSuccess = {
                    Toast.makeText(requireContext(), "Task added successfully", Toast.LENGTH_SHORT).show()
                    view.findNavController().navigate(R.id.action_newTaskFragment_to_mainFragment)
                },
                onFailure = { error ->
                    Toast.makeText(requireContext(), error.message ?: "Failed to add task", Toast.LENGTH_SHORT).show()
                }
            )
        }

        backToMainButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_newTaskFragment_to_mainFragment)
        }

        return view
    }
}
