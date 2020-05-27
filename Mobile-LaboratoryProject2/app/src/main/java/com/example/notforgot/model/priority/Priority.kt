package com.example.notforgot.model.priority

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Priority (@PrimaryKey val id: Int, val name: String, val color: String)