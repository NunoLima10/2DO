package com.example.a2do


import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TodoDbHelper(context: MainActivity) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "toDoList.db"
        const val DATABASE_VERSION = 1
    }



    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(MainActivity.TodoContract.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + MainActivity.TodoContract.TABLE_NAME)
        onCreate(db)
    }

    fun addTodoItem(todoItem: TodoItem): Long {
        val values = ContentValues().apply {
            put(MainActivity.TodoContract.COLUMN_TITLE, todoItem.title)
            put(MainActivity.TodoContract.COLUMN_PRIORITY, todoItem.priority_image)
            put(MainActivity.TodoContract.COLUMN_COMPLETED, todoItem.isChecked)
        }

        return writableDatabase.insert(MainActivity.TodoContract.TABLE_NAME, null, values)
    }

    @SuppressLint("Range")
    fun getAllTodoItems(): ArrayList<TodoItem> {
        val todoList = ArrayList<TodoItem>()
        val selectQuery = "SELECT * FROM ${MainActivity.TodoContract.TABLE_NAME}"
        val cursor = readableDatabase.rawQuery(selectQuery, null)

        cursor.use {
            while (it.moveToNext()) {
                val title = it.getString(it.getColumnIndex(MainActivity.TodoContract.COLUMN_TITLE))
                val priority = it.getInt(it.getColumnIndex(MainActivity.TodoContract.COLUMN_PRIORITY))
                val completed = it.getInt(it.getColumnIndex(MainActivity.TodoContract.COLUMN_COMPLETED))

                val todoItem = TodoItem(title, completed == 1, priority)

                todoList.add(todoItem)
            }
        }

        return todoList
    }
    //E preciso atualizar o banco de dados quando o usuario marcar a tarefa como checked

    fun updateTodoItem(todoItem: TodoItem) {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(MainActivity.TodoContract.COLUMN_TITLE, todoItem.title)
            put(MainActivity.TodoContract.COLUMN_PRIORITY, todoItem.priority_image)
            put(MainActivity.TodoContract.COLUMN_COMPLETED, todoItem.isChecked)
        }

        db.update(MainActivity.TodoContract.TABLE_NAME, values, "${MainActivity.TodoContract.COLUMN_TITLE}=?", arrayOf(todoItem.title.toString()))

        db.close()
    }

    fun deleteTodoItem(id: TodoItem) {
        writableDatabase.delete(MainActivity.TodoContract.TABLE_NAME,
            "${MainActivity.TodoContract.COLUMN_ID}=?",
            arrayOf(id.toString()))
    }



}

private fun SQLiteDatabase.update(tableName: String, values: ContentValues) {

}
