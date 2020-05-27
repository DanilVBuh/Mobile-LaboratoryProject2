package com.example.notforgot.view.taskList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notforgot.R
import com.example.notforgot.model.PreferencesHandler
import com.example.notforgot.model.Repository
import com.example.notforgot.model.category.Category
import com.example.notforgot.model.priority.Priority
import com.example.notforgot.model.server.TaskAPIService
import com.example.notforgot.model.task.Task
import com.example.notforgot.presenter.taskList.TaskListAdapter
import com.example.notforgot.presenter.taskList.TaskListPresenter
import com.example.notforgot.view.authorisation.AuthorisationActivity
import com.example.notforgot.view.createTask.CreateTaskActivity
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), TaskListView  {

    lateinit var presenter: TaskListPresenter
    private lateinit var categories: Array<Category>
    private lateinit var priorities: Array<Priority>
//    val repository = Repository(this)

    override fun openCreateTaskActivity() {
        startActivity(Intent(this, CreateTaskActivity::class.java))
    }

    override fun openAuthorisationActivity() {
        startActivity(Intent(this, AuthorisationActivity::class.java))
    }

    override fun setupScreenForTaskList(tasks: List<Task>) {
        val adapter = TaskListAdapter(tasks)
        taskRecyclerView.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(PreferencesHandler(applicationContext).readString().isEmpty()){
            openAuthorisationActivity()
        }




        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization",
                    """Bearer ${PreferencesHandler(applicationContext).readString()}"""
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

        val crequest = service.getAllCategories()

        crequest.enqueue(object : Callback<Array<Category>> {
            override fun onFailure(call: Call<Array<Category>>, t: Throwable) {
                println(t)
            }

            override fun onResponse(call: Call<Array<Category>>, response: Response<Array<Category>>) {
                println(response.body())
                val body = response.body()
                if (body != null) {
                    categories = body
                    val repository = Repository(applicationContext)
                    repository.deleteAllCategories {  }
                    for(c in categories){
                        repository.setAllCategories(c){}
                    }
                }
            }

        })

        val prequest = service.getAllPriorities()

        prequest.enqueue(object : Callback<Array<Priority>> {
            override fun onFailure(call: Call<Array<Priority>>, t: Throwable) {
                println(t)
            }

            override fun onResponse(call: Call<Array<Priority>>, response: Response<Array<Priority>>) {
                println(response.body())
                val body = response.body()
                if (body != null) {
                    priorities = body
                    val repository = Repository(applicationContext)
                    repository.deleteAllPriorities {  }
                    for(p in priorities){
                        repository.setAllPriorities(p){}
                    }
                }
            }

        })

//        val repository = Repository(applicationContext)
//        var list: List<Category>
//        repository.getAllCategories {
//            list = it.map { category ->
//                Category(
//                    category.id,
//                    category.name
//                )
//            }
//            Toast.makeText(applicationContext, list[1].name, Toast.LENGTH_SHORT).show()
//        }


        presenter = TaskListPresenter(this, applicationContext)


        taskRecyclerView.layoutManager = presenter.getLayoutManager()

        button.setOnClickListener{
            openAuthorisationActivity()
        }

        button2.setOnClickListener{
            Toast.makeText(applicationContext, PreferencesHandler(applicationContext).readString(), Toast.LENGTH_SHORT).show()
        }

        button3.setOnClickListener{
            PreferencesHandler(applicationContext).saveString("")
            openAuthorisationActivity()
            onDestroy()
        }

        addNewTaskFab.setOnClickListener {
            presenter.onFabClick()
        }

    }

    override fun onResume() {
        super.onResume()
        presenter.onActivityResume()
        //presenter.onMainActivityRefresh()
    }

    override fun onDestroy() {
        presenter.onDestroyActivity()
        super.onDestroy()
    }
}
