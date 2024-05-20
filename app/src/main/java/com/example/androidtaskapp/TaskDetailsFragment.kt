package com.example.androidtaskapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

/**
 * A simple [Fragment] subclass.
 * Use the [TaskDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskDetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_task_details, container, false)

        // Find the EditText views by their IDs
        val textTitle = view.findViewById<EditText>(R.id.textTitle)
        val textDescription = view.findViewById<EditText>(R.id.textDescription)
        val textCategory = view.findViewById<EditText>(R.id.textCategory)
        val textStatus = view.findViewById<EditText>(R.id.textStatus)
        val textCreatedTime = view.findViewById<EditText>(R.id.textCreatedTime)
        val textFinishedTime = view.findViewById<EditText>(R.id.textFinishedTime)
        val textDuration = view.findViewById<EditText>(R.id.textDuration)

        return view
    }
}
