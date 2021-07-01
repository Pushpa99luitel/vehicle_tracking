package com.pushpa.vehicletracking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.pushpa.vehicletracking.api.ServiceBuilder
import com.pushpa.vehicletracking.model.User
import com.pushpa.vehicletracking.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var etemail: EditText
    private lateinit var etPassword: EditText
    private lateinit var remember: TextView
    private lateinit var etForgotPassword: TextView
    private lateinit var signUp: TextView
    private lateinit var btnLogin: Button
    private lateinit var linearsnack: RelativeLayout
    var rememberme = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etemail = findViewById(R.id.etemail)
        etPassword = findViewById(R.id.etPassword)
        remember = findViewById(R.id.remember)
        etForgotPassword = findViewById(R.id.etForgotPassword)
        signUp = findViewById(R.id.signUp)
        btnLogin = findViewById(R.id.btnLogin)
        linearsnack = findViewById(R.id.linearsnack)

        btnLogin.setOnClickListener(this)
        remember.setOnClickListener(this)
        etForgotPassword.setOnClickListener(this)
        signUp.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogin -> {
                if (validation()) {
                    val email = etemail.text.toString()
                    val password = etPassword.text.toString()

                    CoroutineScope(Dispatchers.IO).launch {
                        val user = User(email = email, password = password)
                        try {
                            val userRepository = UserRepository()
                            val response = userRepository.loginUser(user)
                            if (response.success == true) {
                                ServiceBuilder.token = "Bearer " + response.token
                                withContext(Main) {
                                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                    if (rememberme) {
                                        saveLogin()
                                    }
                                }
                            }
                        } catch (ex: Exception) {
                            withContext(Main) {
                                val snack =
                                        Snackbar.make(
                                                linearsnack,
                                                "Invalid credentials",
                                                Snackbar.LENGTH_LONG
                                        )
                                snack.setAction("OK", View.OnClickListener {
                                    snack.dismiss()
                                })
                                snack.show()
                            }
                        }
                    }
                }
            }
            R.id.etForgotPassword -> {

            }
            R.id.signUp -> {
                startActivity(Intent(this, SignupActivity::class.java))
            }
            R.id.remember -> {
                rememberme = true
            }
        }
    }

    private fun validation(): Boolean {
        var flag =true
        if (TextUtils.isEmpty(etemail.text)){
            etemail.error = "Enter Email"
            etemail.requestFocus()
            flag = false
        }
        else if (TextUtils.isEmpty(etPassword.text)){
            etPassword.error = "Enter Password"
            etPassword.requestFocus()
            flag = false
        }
        return flag
    }

    private fun saveLogin() {
        val email = etemail.text.toString()
        val pass = etPassword.text.toString()

        val sharedpf = getSharedPreferences("MyPref", MODE_PRIVATE)
        val editor = sharedpf.edit()
        editor.putString("email", email)
        editor.putString("password", pass)
        editor.apply()
    }
}

