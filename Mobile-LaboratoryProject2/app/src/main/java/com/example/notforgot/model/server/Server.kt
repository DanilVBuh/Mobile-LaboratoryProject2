package com.example.notforgot.model.server

import android.content.Context
import androidx.room.Room
import com.example.notforgot.model.database.AppDatabase
import com.example.notforgot.model.task.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Server(val context: Context) {

    private val db: AppDatabase = Room.databaseBuilder(context,
        AppDatabase::class.java, TASK_DATABASE).build()

    fun getAllTasks(onGetTask: (tasks: Array<Task>)-> Unit) {

        GlobalScope.launch(Dispatchers.Main) {

            val tasks = withContext(Dispatchers.IO) {db.taskDao().getAllTask()}

            onGetTask(tasks)

        }
    }

    fun deleteAllTasks(){

        GlobalScope.launch(Dispatchers.Main){

            val tasks = withContext(Dispatchers.IO) {db.taskDao().deleteAllTask()}

        }
    }

    fun saveNewTask(newTask: Task, onSave: ()-> Unit) {

        GlobalScope.launch(Dispatchers.IO) {
            db.taskDao().addTask(newTask)
            onSave()
        }
    }

    companion object {
        private val TASK_DATABASE = "taskDatabase"
    }
}