package com.example.androidtaskapp

import TaskViewModel
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels

/**
 * A simple [Fragment] subclass.
 * Use the [TaskDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskDetailsFragment : Fragment() {

    private val taskViewModel: TaskViewModel by viewModels()

    private var id: Int = 0

    private lateinit var textStatus: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_task_details, container, false)

        // Find the EditText views by their IDs
        val textTitle = view.findViewById<TextView>(R.id.textTitle)
        val textDescription = view.findViewById<TextView>(R.id.textDescription)
        val textCategory = view.findViewById<TextView>(R.id.textCategory)
        textStatus = view.findViewById(R.id.textStatus)
        val textCreatedTime = view.findViewById<TextView>(R.id.textCreatedTime)
        val textFinishedTime = view.findViewById<TextView>(R.id.textFinishedTime)
        val textDuration = view.findViewById<TextView>(R.id.textDuration)

        val args = this.arguments
        id = args?.get("id") as Int
        val title = args.get("title")
        val description = args.get("description")
        val category = args.get("category")
        val status = args.get("status")
        val createdTime = args.get("createdTime")
        val finishedTime = args.get("finishedTime")
        val duration = args.get("duration")

        // Find the Button views by their IDs
        val btnStart = view.findViewById<Button>(R.id.btnStart)
        val btnDone = view.findViewById<Button>(R.id.btnDone)

        textTitle.text = title.toString()
        textDescription.text = description.toString()
        textCategory.text = getCategoryText(category)
        textStatus.text = getStatusText(status)
        textCreatedTime.text = createdTime.toString()
        textFinishedTime.text = finishedTime.toString()
        textDuration.text = duration.toString()

        // Show or hide buttons based on status
        when (status) {
            "0" -> {
                // Status is 0, show Start button, hide Done button, enable both buttons
                btnStart.visibility = View.VISIBLE
                btnDone.visibility = View.GONE
                btnStart.isEnabled = true
                btnDone.isEnabled = true
            }
            "1" -> {
                // Status is 1, hide Start button, show Done button, enable both buttons
                btnStart.visibility = View.GONE
                btnDone.visibility = View.VISIBLE
                btnStart.isEnabled = true
                btnDone.isEnabled = true
            }
            "2" -> {
                // Status is 2, hide both buttons, disable both buttons
                btnStart.visibility = View.GONE
                btnDone.visibility = View.GONE
                btnStart.isEnabled = false
                btnDone.isEnabled = false
            }
        }

        // Set onClickListener for the Start button
        btnStart.setOnClickListener {
            taskViewModel.updateTaskStatus(this.id, "1")// Update status to "In Progress" (1)
            taskViewModel.getTasks()

            // Show a toast message to indicate the status change
            showToast("Task is now In Progress")
            requireActivity().supportFragmentManager.popBackStack()
        }

        // Set onClickListener for the Done button
        btnDone.setOnClickListener {
            taskViewModel.updateTaskStatus(this.id, "2")// Update status to "Done" (2)
            taskViewModel.getTasks()

            // Show a toast message to indicate the status change
            showToast("Task is now In Progress")
            requireActivity().supportFragmentManager.popBackStack()
        }

        return view
    }

    // Function to show toast message
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun getStatusText(status: Any?): String {
        return when (status) {
            "0" -> "New"
            "1" -> "In Progress"
            "2" -> "Done"
            else -> "Unknown"
        }
    }

    private fun getCategoryText(category: Any?): String {
        return when (category) {
            "0" -> "Normal"
            "1" -> "Urgent"
            "2" -> "Important"
            else -> "Unknown"
        }
    }

}
