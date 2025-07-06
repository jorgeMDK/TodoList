package com.example.todolist

data class Task(
    val title: String,
    val description: String,
    val repeat: Int,
    val date: String,
    val alertEnable: Boolean,
    val alert: String,
    val selectedDay: Int
)