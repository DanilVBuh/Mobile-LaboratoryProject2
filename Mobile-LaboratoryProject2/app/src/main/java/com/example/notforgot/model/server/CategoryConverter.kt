package com.example.notforgot.model.server

import androidx.room.TypeConverter
import com.example.notforgot.model.category.Category
import java.util.*
import java.util.Arrays.asList
import java.util.stream.Collectors


class CategoryConverter
//
//@TypeConverter
//fun fromCategory(category: Category): Array<String> {
//    return tasks.stream().collect(Collectors.joining(","))
//}
//
//@TypeConverter
//fun toCategory(data: String): List<String> {
//    return Arrays.asList(data.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
//}