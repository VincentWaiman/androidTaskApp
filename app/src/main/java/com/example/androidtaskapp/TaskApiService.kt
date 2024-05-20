package com.example.androidtaskapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskApiService {

    @GET("/api/get-all-task")
    fun getTasks(): Call<TaskResponse>

    @POST("/api/add-new-task")
    fun postTask(@Body task: TaskInfo): Call<TaskInfo>

    @PUT("/api/edit-task/{id}")
    fun updateTaskStatus(@Path("id") id: Int, @Body statusUpdate: TaskStatusUpdate): Call<Void>
}

