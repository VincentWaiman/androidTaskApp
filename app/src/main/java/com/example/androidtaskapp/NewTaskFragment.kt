package com.example.androidtaskapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import com.example.androidtaskapp.ApiClient.taskApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

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
            val title = view.findViewById<EditText>(R.id.editTextTitle).text.toString()
            val description = view.findViewById<EditText>(R.id.editTextDescription).text.toString()
            val category = taskCategory.text.toString()

            val createdTime = System.currentTimeMillis()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val dateString = dateFormat.format(createdTime)

            val task = TaskInfo(
                title = title,
                description = description,
                status = "0",
                category = when (category) {
                    "New" -> "0"
                    "Urgent" -> "1"
                    "Important" -> "2"
                    else -> "0"
                },
                createdTime = dateString
            )
            addTask(task)

            // ask for a nav controller
            val navController = view.findNavController()
            // navigate into certain destination
            navController.navigate(R.id.action_newTaskFragment_to_mainFragment)
        }

        backToMainButton.setOnClickListener {
            // ask for a nav controller
            val navController = view.findNavController()
            // navigate into certain destination
            navController.navigate(R.id.action_newTaskFragment_to_mainFragment)
        }
        return view
    }

    private fun addTask(task: TaskInfo) {
        val call = taskApiService.postTask(task)

        call.enqueue(object : Callback<TaskInfo> {
            override fun onFailure(call: Call<TaskInfo>, t: Throwable) {
                Log.e("NewTaskFragment", "Failed to add task", t)
            }

            override fun onResponse(call: Call<TaskInfo>, response: Response<TaskInfo>) {
                if (response.isSuccessful) {
                    val addedTask = response.body()
                    Log.e("NewTaskFragment", "Task added successfully \n $addedTask")
                } else {
                    Log.e("NewTaskFragment", "Failed to add task \n ${response.errorBody()?.string() ?: ""}")
                }
            }
        })
    }
}