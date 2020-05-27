package com.example.notforgot.presenter.authorisation

import android.content.Context
import android.widget.Toast
import com.example.notforgot.model.PreferencesHandler
import com.example.notforgot.model.server.TaskAPIService
import com.example.notforgot.model.user.Authorisor
import com.example.notforgot.model.user.Register
import com.example.notforgot.view.authorisation.AuthorisationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthorisationPresenter(var view: AuthorisationView?, val context: Context) {

    fun onSignInButtonClick() {
        view?.openRegistrationActivity()
    }

    fun onDestroyActivity() {
        view = null
    }

    fun onAuthoriseButtonClick(
        emailText: String,
        passwordText: String
    ) {
        if (emailText.isEmpty())
            return
        if (passwordText.isEmpty())
            return
        if(!isEmailValid(emailText))
            return

        val retrofit = Retrofit.Builder()
            .baseUrl("http://practice.mobile.kreosoft.ru/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(TaskAPIService::class.java)

        val request = service.joinToBase(emailText,passwordText)
        request.enqueue(object : Callback<Authorisor> {
            override fun onFailure(call: Call<Authorisor>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                println(t)
            }

            override fun onResponse(call: Call<Authorisor>, response: Response<Authorisor>) {
                println(response.body())
                val body = response.body()
                if(body != null) {
                    PreferencesHandler(context).saveString(response.body()?.api_token.toString())
                    Toast.makeText(context, PreferencesHandler(context).readString(), Toast.LENGTH_SHORT).show()
                    view?.authorisationAlreadyCompleted()

                }
            }

        })
//        view?.authorisationAlreadyCompleted()
    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}