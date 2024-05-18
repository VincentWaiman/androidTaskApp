package com.example.androidtaskapp

import retrofit2.Call
import retrofit2.http.GET

interface TaskApiService {

    @GET("/api/get-all-task")
    fun getTasks(): Call<TaskResponse>

//    @POST("/api/add-new-task")
//    fun postTask(@Body TaskInfo taskInfo): Call<TaskInfo>
}

