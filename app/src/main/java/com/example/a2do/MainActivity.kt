package com.example.a2do

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
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

    object TodoContract {
        const val TABLE_NAME = "todos"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_PRIORITY = "priority"
        const val COLUMN_COMPLETED = "completed"

        const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_TITLE TEXT," +
                "$COLUMN_PRIORITY INTEGER," +
                "$COLUMN_COMPLETED INTEGER)"
    }

    override fun onResume() {
        super.onResume()

        // Initialize database helper
        val dbHelper = TodoDbHelper(this)

        // Retrieve all items from the database and update the todoList
        todoList.clear()
        todoList.addAll(dbHelper.getAllTodoItems())

        // Notify the adapter that the data has changed
        todoAdapter.notifyDataSetChanged()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize database helper
        val dbHelper = TodoDbHelper(this)

        // Retrieve all items from the database and add them to the todoList
        todoList = dbHelper.getAllTodoItems()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        todoAdapter = TodoAdapter(todoList)
        recyclerView.adapter = todoAdapter

        swipeToDeleteCallback = object : SwipeToDeleteCallback() {
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

        btnAddToDo.setOnClickListener {
            if (!editText.text.isNullOrEmpty()) {
                val todoTitle = editText.text.toString()
                editText.text.clear()
                val priority = getPriority()

                val dbHelper = TodoDbHelper(this)

                val newTodo = TodoItem(todoTitle, false, priority)
                dbHelper.addTodoItem(newTodo)
                todoAdapter.addTodo(newTodo)
            }
        }
    }
}
