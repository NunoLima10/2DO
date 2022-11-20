package com.example.a2do

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var todoList: ArrayList<TodoItem>
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var btnAddToDo: Button
    private lateinit var editText: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var swipeToDeleteCallback: SwipeToDeleteCallback
    private lateinit var itemTouchHelper: ItemTouchHelper

    private fun getPriority():Int{
        when(radioGroup.checkedRadioButtonId){
            R.id.radioButtonUrgent -> return R.drawable.ic_todo_priority_urgent
            R.id.radioButtonMedium -> return R.drawable.ic_todo_priority_medium
            R.id.radioButtonLow -> return  R.drawable.ic_todo_priority_low
        }
        return  R.drawable.ic_todo_priority_low
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        todoList = ArrayList()
        todoAdapter = TodoAdapter(todoList)
        recyclerView.adapter = todoAdapter

        swipeToDeleteCallback = object :SwipeToDeleteCallback(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position =  viewHolder.adapterPosition
                todoAdapter.deleteTodo(position)

            }
        }
        itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)


        btnAddToDo = findViewById(R.id.btnAddToDo)
        editText = findViewById(R.id.editText)
        radioGroup = findViewById(R.id.radioGroup)





        btnAddToDo.setOnClickListener {
            if (!editText.text.isEmpty()){
                val todoTitle = editText.text.toString()
                editText.text.clear()
                val newTodo = TodoItem(todoTitle, false, getPriority())

                todoAdapter.addTodo(newTodo)
            }
        }
    }

}