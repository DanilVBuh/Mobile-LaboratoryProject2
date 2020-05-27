package com.example.notforgot.presenter.registration

import android.content.Context
import android.widget.Toast
import com.example.notforgot.model.PreferencesHandler
import com.example.notforgot.model.user.Register
import com.example.notforgot.model.server.TaskAPIService
import com.example.notforgot.view.registration.RegistrationView
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegistrationPresenter(var view: RegistrationView?, val context: Context) {



    fun onRegistrationButtonClick(
        emailText: String,
        nameText: String,
        passwordText: String,
        passwordRepeatedText: String
    ) {
        if (emailText.isEmpty())
            return
        if (nameText.isEmpty())
            return
        if (passwordText.isEmpty())
            return
        if (passwordRepeatedText.isEmpty())
            return
        if (passwordRepeatedText != passwordText)
            return
        if(!isEmailValid(emailText))
            return

        val retrofit = Retrofit.Builder()
            .baseUrl("http://practice.mobile.kreosoft.ru/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(TaskAPIService::class.java)

        val request = service.createNewUser(emailText,nameText,passwordText)
        request.enqueue(object : Callback<Register> {
            override fun onFailure(call: Call<Register>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                println(t)
            }

            override fun onResponse(call: Call<Register>, response: Response<Register>) {
                //println(response.body())
                val body = response.body()
                if(body != null) {
                    PreferencesHandler(context).saveString(response.body()?.api_token.toString())
                    Toast.makeText(context, PreferencesHandler(context).readString(), Toast.LENGTH_SHORT).show()
                    view?.registrationAlreadyCompleted()

//                    val client = OkHttpClient.Builder().addInterceptor { chain ->
//                        val newRequest = chain.request().newBuilder()
//                            .addHeader("Authorization",
//                                """Bearer ${PreferencesHandler(context).readString()}"""
//                            )
//                            .build()
//                        chain.proceed(newRequest)
//                    }.build()
//                    val retrofit = Retrofit.Builder()
//                        .client(client)
//                        .baseUrl("http://practice.mobile.kreosoft.ru/api/")
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build()
                }
            }

        })
//        view?.registrationAlreadyCompleted()
    }

    fun onLogInButtonClick(){
        view?.openAuthorisationActivity()

    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun onDestroyActivity() {
        view = null
    }
}