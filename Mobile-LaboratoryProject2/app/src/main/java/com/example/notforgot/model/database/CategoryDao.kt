package com.example.notforgot.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.notforgot.model.category.Category
import com.example.notforgot.model.task.Task

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getAllCategory(): Array<Category>

    @Insert
    fun addCategory(category: Category)

    @Insert
    fun setAllCategory(category: Category)

    @Query("DELETE FROM category")
    fun deleteAllCategory()

}