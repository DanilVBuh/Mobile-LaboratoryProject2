package com.example.notforgot.view.authorisation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notforgot.R
import com.example.notforgot.presenter.authorisation.AuthorisationPresenter
import com.example.notforgot.view.registration.RegistrationActivity
import com.example.notforgot.view.taskList.MainActivity
import kotlinx.android.synthetic.main.authorization_layout.*
import kotlinx.android.synthetic.main.registration_layout.*

class AuthorisationActivity : AppCompatActivity(),AuthorisationView {

    lateinit var presenter: AuthorisationPresenter

    override fun openRegistrationActivity() {
        startActivity(Intent(this, RegistrationActivity::class.java))
    }

    override fun openMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.authorization_layout)

        presenter = AuthorisationPresenter(this, applicationContext)

        newRegistrationButton.setOnClickListener{
            presenter.onSignInButtonClick()
        }

        authorisationButton.setOnClickListener {
            presenter.onAuthoriseButtonClick(
                authorisationEmail.text.toString(),
                authorisationPassword.text.toString()
            )
        }

    }

    override fun onDestroy() {
        presenter.onDestroyActivity()
        super.onDestroy()
    }

    override fun authorisationAlreadyCompleted(){
        openMainActivity()
        finish()
    }
}
