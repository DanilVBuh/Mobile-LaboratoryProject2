package com.example.notforgot.presenter.taskList

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notforgot.model.PreferencesHandler
import com.example.notforgot.model.Repository
import com.example.notforgot.model.category.Category
import com.example.notforgot.model.server.TaskAPIService
import com.example.notforgot.model.task.Task
import com.example.notforgot.model.task.TaskResponse
import com.example.notforgot.view.taskList.TaskListView
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TaskListPresenter (var view: TaskListView?, val context: Context) {

    val repository = Repository(context)
    private lateinit var dataset: List<Task>

    fun getLayoutManager(): LinearLayoutManager {
        return LinearLayoutManager(view as Context)
    }

    fun onFabClick() {
        view?.openCreateTaskActivity()
    }

    fun onMainActivityRefresh(){
//        if(PreferencesHandler(context).readString().isEmpty()){
//            view?.openRegistrationActivity()
//            return
//        }
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

        val request = service.getAllTasks()

        request.enqueue(object : Callback<Array<Task>> {
            override fun onFailure(call: Call<Array<Task>>, t: Throwable) {
                println(t)
            }

            override fun onResponse(call: Call<Array<Task>>, response: Response<Array<Task>>) {
                println(response.body())
                val body = response.body()
                if (body != null) {
                    //Toast.makeText(context, "poluchilosy?", Toast.LENGTH_SHORT).show()
                    dataset = List(body.size)
                    { i -> body[i]
                    }
                    view?.setupScreenForTaskList(dataset)
                }
                //myRecyclerView.adapter = CustomAdapter(dataset)
            }

        })
    }

    fun onActivityResume() {

        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader(
                    "Authorization",
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

        val request = service.getAllTasks()

        request.enqueue(object : Callback<Array<Task>> {
            override fun onFailure(call: Call<Array<Task>>, t: Throwable) {
                println(t)
            }

            override fun onResponse(call: Call<Array<Task>>, response: Response<Array<Task>>) {
                println(response.body())
                val body = response.body()
                if (body != null) {
                    val repository = Repository(context)
                    repository.deleteAllTasks { }
                    for (t in body) {
                        repository.saveNewTask(t) {}
                    }
                }
            }


        })

                repository.getAllTasks {
                    val data = it.map { t ->
                        Task(
                            t.id,
                            t.title,
                            t.description,
                            t.done,
                            t.deadline,
                            t.category,
                            t.priority,
                            t.created
                        )


                    }
                    view?.setupScreenForTaskList(data)
                }

    }

    fun onDestroyActivity() {
        view = null
    }
}