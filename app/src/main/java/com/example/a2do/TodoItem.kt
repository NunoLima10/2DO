package com.example.a2do

data class TodoItem(
    var title: String,
    var isChecked: Boolean = false,
    val priority_image: Int
)
