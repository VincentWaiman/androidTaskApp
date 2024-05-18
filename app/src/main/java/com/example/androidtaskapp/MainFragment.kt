package com.example.androidtaskapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {

    private lateinit var retrofit: Retrofit

    private lateinit var taskApiService: TaskApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        this.taskApiService = this.retrofit.create(TaskApiService::class.java)

        getTasks()
    }
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

    private fun getTasks() {
        val call = taskApiService.getTasks()

        call.enqueue(object : Callback<TaskResponse> {
            override fun onFailure(call: Call<TaskResponse>, t: Throwable) {
                Log.e("MainActivity", "Failed to get search results", t)
            }

            override fun onResponse(
                call: Call<TaskResponse>,
                response: Response<TaskResponse>
            ) {
                if (response.isSuccessful) {
                    val taskList = response.body()?.task
                    Log.e("MainActivity", "Response Successful \n $taskList")
                } else {
                    Log.e("MainActivity", "Failed to get results \n ${response.errorBody()?.toString() ?: ""}")
                }
            }
        })
    }
}