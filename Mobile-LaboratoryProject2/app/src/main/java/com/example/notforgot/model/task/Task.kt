package com.example.notforgot.model.task

import android.net.IpPrefix
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notforgot.model.category.Category
import com.example.notforgot.model.priority.Priority

@Entity
data class Task(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val done: Int,
    val deadline: Long,
    @Embedded(prefix = "category_") val category: Category,
    @Embedded(prefix = "priority_") val priority: Priority,
    val created: Long
)
//data class Task(val name: String, val age: Int, @PrimaryKey(autoGenerate = true) val id: Int = 0)