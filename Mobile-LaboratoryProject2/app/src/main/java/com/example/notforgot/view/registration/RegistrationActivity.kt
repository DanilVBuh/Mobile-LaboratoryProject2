package com.example.notforgot.view.registration

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notforgot.R
import com.example.notforgot.presenter.registration.RegistrationPresenter
import com.example.notforgot.view.authorisation.AuthorisationActivity
import com.example.notforgot.view.taskList.MainActivity
import kotlinx.android.synthetic.main.authorization_layout.*
import kotlinx.android.synthetic.main.registration_layout.*

class RegistrationActivity : AppCompatActivity(), RegistrationView {

    lateinit var presenter: RegistrationPresenter

    override fun openAuthorisationActivity() {
        startActivity(Intent(this, AuthorisationActivity::class.java))
    }

    override fun openMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_layout)

        presenter = RegistrationPresenter(this, applicationContext)

        registrationButton.setOnClickListener {
            presenter.onRegistrationButtonClick(
                registrationEmail.text.toString(),
                registrationName.text.toString(),
                registrationPassword.text.toString(),
                registrationPasswordRepeated.text.toString()
            )
        }

        backToAuthorisationButton.setOnClickListener{
            presenter.onLogInButtonClick()
        }
    }

    override fun onDestroy() {
        presenter.onDestroyActivity()
        super.onDestroy()
    }

    override fun registrationAlreadyCompleted() {
        openMainActivity()
        finish()
    }
}
