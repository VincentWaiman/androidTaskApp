package com.example.androidtaskapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TaskApiService {

    @GET("/api/get-all-task")
    fun getTasks(): Call<TaskResponse>

    @POST("/api/add-new-task")
    fun postTask(@Body task: TaskInfo): Call<TaskInfo>
}

