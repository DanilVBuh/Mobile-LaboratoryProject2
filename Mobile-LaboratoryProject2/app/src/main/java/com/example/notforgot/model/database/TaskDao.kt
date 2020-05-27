package com.example.notforgot.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.notforgot.model.category.Category
import com.example.notforgot.model.task.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    fun getAllTask(): Array<Task>
//
//    @Query("SELECT * FROM category")
//    fun getAllCategory(): Array<Category>

    @Query("DELETE FROM task ")
    fun deleteAllTask()

    @Insert
    fun addTask(task: Task)


}