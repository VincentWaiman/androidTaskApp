package com.example.androidtaskapp

data class TaskInfo(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val status: String,
    val createdTime: String,
    val finishedTime: String,
    val duration: String
)
