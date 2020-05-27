package com.example.notforgot.model

import android.content.Context
import androidx.room.Delete
import androidx.room.Room
import com.example.notforgot.model.category.Category
import com.example.notforgot.model.database.AppDatabase
import com.example.notforgot.model.priority.Priority
import com.example.notforgot.model.task.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Repository(val context: Context) {

    private val db: AppDatabase = Room.databaseBuilder(context,
        AppDatabase::class.java, TASK_DATABASE).build()

    fun getAllCategories(onGetCategory: (categories: Array<Category>)-> Unit) {

        GlobalScope.launch(Dispatchers.Main) {

            val categories = withContext(Dispatchers.IO) {db.categoryDao().getAllCategory()}

            onGetCategory(categories)

        }
    }

    fun getAllPriorities(onGetPriority: (priorities: Array<Priority>)-> Unit) {

        GlobalScope.launch(Dispatchers.Main) {

            val priorities = withContext(Dispatchers.IO) {db.priorityDao().getAllPriority()}

            onGetPriority(priorities)

        }
    }

    fun deleteAllPriorities(onDelete: ()-> Unit) {

        GlobalScope.launch(Dispatchers.IO) {
            db.priorityDao().deleteAllPriority()
            onDelete()
        }

    }

    fun setAllPriorities(newPriority: Priority, onSave: ()-> Unit) {

        GlobalScope.launch(Dispatchers.IO) {
            db.priorityDao().setAllPriority(newPriority)
            onSave()
        }
    }

    fun getAllTasks(onGetTask: (tasks: Array<Task>)-> Unit){

        GlobalScope.launch(Dispatchers.Main) {

            val tasks = withContext(Dispatchers.IO) {db.taskDao().getAllTask()}
            onGetTask(tasks)
        }
    }

    fun setAllCategories(newCategory: Category, onSave: ()-> Unit) {

        GlobalScope.launch(Dispatchers.IO) {
            db.categoryDao().setAllCategory(newCategory)
            onSave()
        }
    }

    fun saveNewCategory(newCat: Category, onSave: ()-> Unit) {

        GlobalScope.launch(Dispatchers.IO) {
            db.categoryDao().addCategory(newCat)
            onSave()
        }
    }

    fun deleteAllTasks(onDelete: ()-> Unit){
        GlobalScope.launch(Dispatchers.IO) {
            db.taskDao().deleteAllTask()
            onDelete()
        }
    }

    fun deleteAllCategories(onDelete: ()-> Unit) {

        GlobalScope.launch(Dispatchers.IO) {
            db.categoryDao().deleteAllCategory()
            onDelete()
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