package com.example.notforgot.model.category

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Category (@PrimaryKey val id: Int, val name: String)