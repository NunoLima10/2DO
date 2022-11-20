package com.example.a2do

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

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = Todos[position]
        holder.ivPriority.setImageResource(todo.priority_image)
        holder.cbTodoItem.text = todo.title
        holder.cbTodoItem.isChecked = todo.isChecked
    }

    override fun getItemCount(): Int {
        return Todos.size
    }
}