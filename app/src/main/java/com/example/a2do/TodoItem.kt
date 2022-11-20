package com.example.a2do

data class TodoItem(
    val title: String,
    val isChecked: Boolean = false,
    val priority_image: Int
)
