package com.example.notforgot.model.server

import androidx.room.Insert
import com.example.notforgot.model.category.Category
import com.example.notforgot.model.category.CategoryResponse
import com.example.notforgot.model.priority.Priority
import com.example.notforgot.model.task.Task
import com.example.notforgot.model.task.TaskResponse
import com.example.notforgot.model.user.Authorisor
import com.example.notforgot.model.user.Register
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface TaskAPIService {
    @FormUrlEncoded
    @POST("register")
    fun createNewUser(
        @Field("email") email:String,
        @Field("name") name:String,
        @Field("password") password:String
    ): Call<Register>

    @FormUrlEncoded
    @POST("login")
    fun joinToBase(
        @Field("email") email:String,
        @Field("password") password:String
    ): Call<Authorisor>

    @GET("tasks")
    fun getAllTasks(): Call<Array<Task>>

    @GET("categories")
    fun getAllCategories(): Call<Array<Category>>

    @GET("priorities")
    fun getAllPriorities(): Call<Array<Priority>>

    @FormUrlEncoded
    @POST("categories")
    fun addCategory(
        @Field("name") name:String
    ): Call<Category>

    @FormUrlEncoded
    @POST("tasks")
    fun addTask(
        @Field("title") title:String,
        @Field("description") description:String,
        @Field("done") done:Int,
        @Field("deadline") deadline:Int,
        @Field("category_id") category_id:Int,
        @Field("priority_id") priority_id:Int
    ): Call<Task>
}