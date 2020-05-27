package com.example.notforgot.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notforgot.model.category.Category
import com.example.notforgot.model.priority.Priority
import com.example.notforgot.model.task.Task

@Database(entities = [Task::class, Category::class, Priority::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao
    abstract fun priorityDao(): PriorityDao

}