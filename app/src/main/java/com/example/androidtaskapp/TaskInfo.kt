package com.example.androidtaskapp

import java.io.Serializable

data class TaskInfo(
    val id: Int? = null,
    val title: String,
    val description: String,
    val category: String,
    val status: String,
    val createdTime: String,
    val finishedTime: String = "",
    val duration: String = ""
)
