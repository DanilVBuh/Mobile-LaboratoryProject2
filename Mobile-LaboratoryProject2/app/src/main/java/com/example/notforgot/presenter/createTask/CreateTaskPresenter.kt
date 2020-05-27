package com.example.notforgot.presenter.createTask

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.notforgot.R
import com.example.notforgot.model.PreferencesHandler
import com.example.notforgot.model.Repository
import com.example.notforgot.model.category.Category
import com.example.notforgot.model.category.CategoryResponse
import com.example.notforgot.model.priority.Priority
import com.example.notforgot.model.server.TaskAPIService
import com.example.notforgot.model.task.Task
import com.example.notforgot.view.createTask.CreateTaskView
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class CreateTaskPresenter(var view: CreateTaskView?, val context: Context) {

    val repository = Repository(context)
    //private lateinit var categories: Array<Category>
    //private lateinit var priorities: List<Priority>
    val colors = arrayOf("Red","Green","Blue","Yellow","Black","Crimson","Orange")

    fun onSetSpinners(){
        repository.getAllCategories {
            val data = it.map { category ->
                Category(
                    category.id,
                    category.name
                )
            }

            view?.setupCategorySpinner(data)
        }
        repository.getAllPriorities {
            val data = it.map { category ->
                Priority(
                    category.id,
                    category.name,
                    category.color
                )
            }
            view?.setupPrioritySpinner(data)
        }
    }

    fun onAddCategory(newName: String){
        if (newName.isEmpty())
            return

        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization",
                    """Bearer ${PreferencesHandler(context).readString()}"""
                )
                .build()
            chain.proceed(newRequest)
        }.build()
        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("http://practice.mobile.kreosoft.ru/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(TaskAPIService::class.java)

        val request = service.addCategory(newName)

        request.enqueue(object : Callback<Category> {
            override fun onFailure(call: Call<Category>, t: Throwable) {
                println(t)
            }

            override fun onResponse(call: Call<Category>, response: Response<Category>) {
                println(response.body())
                val body = response.body()
                if (body != null) {
                    val repository = Repository(context)
                    repository.saveNewCategory(body){}
                }
            }

        })
    }

    fun onSaveButtonClick(titleText: String, description: String) {

        if (titleText.isEmpty())
            return

        //var task = Task(titleText, description, 1, 1, 1, 1)

        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization",
                    "Bearer" + PreferencesHandler(context).readString()
                )
                .build()
            chain.proceed(newRequest)
        }.build()
        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("http://practice.mobile.kreosoft.ru/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(TaskAPIService::class.java)

        Toast.makeText(context, "NOT  WELL", Toast.LENGTH_SHORT).show()

        val request = service.addTask(titleText, description, 1, 12565496, 1, 1)
        request.enqueue(object : Callback<Task> {
            override fun onFailure(call: Call<Task>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                println(t)
            }

            override fun onResponse(call: Call<Task>, response: Response<Task>) {
                println(response.body())
                Toast.makeText(context, "WELL", Toast.LENGTH_SHORT).show()

                val body = response.body()
                if(body != null) {
                    val repository = Repository(context)
                    repository.saveNewTask(body){}
                }
            }

        })
//        repository.getAllTasks {
//            val data = it.map { t ->
//                Task(
//                    t.id,
//                    t.title,
//                    t.description,
//                    t.done,
//                    t.deadline,
//                    t.created,
//                    t.priority,
//                    t.category
//
//
//                )
//            }
//
//            view?.setupCategorySpinner(data)
//        }
//        view?.taskAlreadyCreated()



//        repository.saveNewTask(task) {
//            view?.taskAlreadyCreated()
//        }
    }

    fun onSetDate(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                Toast.makeText(context, """$dayOfMonth - ${monthOfYear + 1} - $year""", Toast.LENGTH_LONG).show()
            },
            year,
            month,
            day
        )
        dpd.show()
    }

    fun onDestroyActivity() {
        view = null
    }


}