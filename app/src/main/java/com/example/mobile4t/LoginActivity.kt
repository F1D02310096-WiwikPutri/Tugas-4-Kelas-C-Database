package com.example.mobile4t

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity :
    AppCompatActivity() {

    private lateinit var sharedPref:
            SharedPreferences

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_login
        )

        sharedPref =
            getSharedPreferences(
                "LOGIN_PREF",
                MODE_PRIVATE
            )

        checkLoginSession()

        setupLoginButton()
    }

    private fun checkLoginSession() {

        val isLogin =
            sharedPref.getBoolean(
                "IS_LOGIN",
                false
            )

        if (isLogin) {

            startActivity(

                Intent(
                    this,
                    MainActivity::class.java
                )
            )

            finish()
        }
    }

    private fun setupLoginButton() {

        val edtUsername =
            findViewById<EditText>(
                R.id.edtUsername
            )

        val edtPassword =
            findViewById<EditText>(
                R.id.edtPassword
            )

        val cbRemember =
            findViewById<CheckBox>(
                R.id.cbRemember
            )

        val btnLogin =
            findViewById<Button>(
                R.id.btnLogin
            )

        btnLogin.setOnClickListener {

            val username =
                edtUsername.text.toString()

            val password =
                edtPassword.text.toString()

            if (

                username == "admin" &&

                password == "123456"

            ) {

                sharedPref.edit()

                    .putBoolean(
                        "IS_LOGIN",
                        cbRemember.isChecked
                    )

                    .apply()

                Toast.makeText(

                    this,

                    "Login berhasil",

                    Toast.LENGTH_SHORT

                ).show()

                startActivity(

                    Intent(
                        this,
                        MainActivity::class.java
                    )
                )

                finish()

            } else {

                Toast.makeText(

                    this,

                    "Username atau Password salah",

                    Toast.LENGTH_SHORT

                ).show()
            }
        }
    }
}