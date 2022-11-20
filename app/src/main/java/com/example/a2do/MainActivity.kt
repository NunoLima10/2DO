package com.example.a2do

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var todoList: ArrayList<TodoItem>
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        todoList = ArrayList()

        todoList.add(
            TodoItem("Terminar todo list kotlin",false,R.drawable.ic_todo_priority)
        )

        todoAdapter = TodoAdapter(todoList)
        recyclerView.adapter = todoAdapter



    }
}