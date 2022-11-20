package com.example.a2do

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(private val Todos:MutableList<TodoItem>)
    :RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){

    class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val ivPriority: ImageView = itemView.findViewById(R.id.ivPriority)
        val cbTodoItem: CheckBox = itemView.findViewById(R.id.cbTodoItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_to_do, parent,false)
        return TodoViewHolder(view)
    }

    private fun toggleScratched(cbTodoItem:CheckBox){
        if (cbTodoItem.isChecked){
            cbTodoItem.paintFlags = cbTodoItem.paintFlags or STRIKE_THRU_TEXT_FLAG
        }
        else{
            cbTodoItem.paintFlags = cbTodoItem.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = Todos[position]
        holder.apply {
            cbTodoItem.text = todo.title
            cbTodoItem.isChecked = todo.isChecked
            ivPriority.setImageResource(todo.priority_image)
            
            toggleScratched(cbTodoItem)
            cbTodoItem.setOnCheckedChangeListener { _, b ->
                toggleScratched(cbTodoItem)
                todo.isChecked = b
            }
        }
    }
    fun addTodo(todo:TodoItem){
        Todos.add(todo)
        notifyItemInserted(Todos.size - 1)
    }
    fun deleteTodo(position: Int){
        Todos.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int {
        return Todos.size
    }
}