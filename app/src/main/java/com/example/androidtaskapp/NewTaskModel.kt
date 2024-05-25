package com.example.androidtaskapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.example.androidtaskapp.ApiClient.taskApiService
import java.text.SimpleDateFormat
import java.util.Locale

class NewTaskModel : ViewModel() {

    fun validateAndAddTask(
        taskTitle: String,
        taskDescription: String,
        taskCategory: String,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        if (taskTitle.isEmpty() || taskDescription.isEmpty() || taskCategory.isEmpty()) {
            onFailure(Throwable("Please fill all fields to add a task"))
            return
        }

        val category = when (taskCategory) {
            "New" -> "0"
            "Urgent" -> "1"
            "Important" -> "2"
            else -> "0"
        }
        val createdTime = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val dateString = dateFormat.format(createdTime)

        val task = TaskInfo(
            title = taskTitle,
            description = taskDescription,
            status = "0",
            category = category,
            createdTime = dateString
        )

        addTask(task, onSuccess, onFailure)
    }

    fun addTask(task: TaskInfo, onSuccess: () -> Unit, onFailure: (Throwable) -> Unit) {
        val call = taskApiService.postTask(task)

        call.enqueue(object : Callback<TaskInfo> {
            override fun onFailure(call: Call<TaskInfo>, t: Throwable) {
                Log.e("NewTaskModel", "Failed to add task", t)
                onFailure(t)
            }

            override fun onResponse(call: Call<TaskInfo>, response: Response<TaskInfo>) {
                if (response.isSuccessful) {
                    val addedTask = response.body()
                    Log.e("NewTaskModel", "Task added successfully \n $addedTask")
                    onSuccess()
                } else {
                    Log.e("NewTaskModel", "Failed to add task \n ${response.errorBody()?.string() ?: ""}")
                    onFailure(Throwable(response.errorBody()?.string()))
                }
            }
        })
    }
}
