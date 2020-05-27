package com.example.notforgot.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.notforgot.model.category.Category
import com.example.notforgot.model.priority.Priority
import com.example.notforgot.model.task.Task

@Dao
interface PriorityDao {

    @Query("SELECT * FROM priority")
    fun getAllPriority(): Array<Priority>

    @Insert
    fun setAllPriority(priority: Priority)

    @Query("DELETE FROM priority")
    fun deleteAllPriority()

}