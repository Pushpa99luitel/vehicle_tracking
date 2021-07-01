package com.pushpa.vehicletracking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import com.pushpa.vehicletracking.model.User
import com.pushpa.vehicletracking.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupActivity : AppCompatActivity() {
    private lateinit var etusername: EditText
    private lateinit var etemail:EditText
    private lateinit var etpassword:EditText
    private lateinit var etconfirmpassword:EditText
    private lateinit var register: Button
    private lateinit var linearsnack: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        etusername=findViewById(R.id.etusername)
        etemail=findViewById(R.id.etemail)
        etpassword=findViewById(R.id.etpassword)
        etconfirmpassword=findViewById(R.id.etconfirmpassword)
        register=findViewById(R.id.register)
        linearsnack=findViewById(R.id.linearsnack)

        register.setOnClickListener{
            checkEmpty()

            userAdd()
        }
    }

    private fun userAdd() {
        val username=etusername.text.toString()
        val email=etemail.text.toString()
        val password=etpassword.text.toString()
        val cpass = etconfirmpassword.text.toString()

        if (password != cpass){
            etpassword.error = "Password Doesnot Match"
            etconfirmpassword.error = "Password Doesnot Match"
            etpassword.requestFocus()
            etconfirmpassword.requestFocus()
        }else{
            CoroutineScope(Dispatchers.IO).launch{
                val user = User(username = username,email = email,password = password)
                try{
                    val userRepository = UserRepository()
                    val response = userRepository.registerUser(user)
                    if(response.success == true){
                        withContext(Dispatchers.Main){
                            startActivity(Intent(this@SignupActivity,LoginActivity::class.java))
                            Toast.makeText(this@SignupActivity, "User Registered Successfully", Toast.LENGTH_SHORT).show()
                        }
                    }
                }catch (ex:Exception){
                    withContext(Dispatchers.Main){
                        val snack =
                            Snackbar.make(
                                linearsnack,
                                "$ex",
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

    private fun checkEmpty() {
        val userame=etusername.text.toString()
        val email=etemail.text.toString()
        val password=etpassword.text.toString()
        val cpass = etconfirmpassword.text.toString()

        if (userame.trim().isEmpty()){
            etusername.error="required"
        }
        else if(email.trim().isEmpty()){
            etemail.error="required"
        }
        else if(password.trim().isEmpty()){
            etpassword.error="required"
        }
        else if(cpass.trim().isEmpty()){
            etconfirmpassword.error="required"
        }
    }
}