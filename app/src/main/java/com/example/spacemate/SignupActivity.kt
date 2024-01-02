package com.example.spacemate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {
    private lateinit var nameET: EditText
    private lateinit var emailET: EditText
    private lateinit var passET: EditText
    private lateinit var phET: EditText
    private lateinit var signupBtn: Button
    private lateinit var loginBtn: Button
    private lateinit var userDatabase: UserDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        userDatabase = UserDatabase.getDatabase(this)

        nameET = findViewById(R.id.nameET)
        emailET = findViewById(R.id.emailET)
        passET = findViewById(R.id.passET)
        phET= findViewById(R.id.phET)
        signupBtn = findViewById(R.id.signup_btn)
        loginBtn=findViewById(R.id.loginbtn)

        loginBtn.setOnClickListener{
            val intent1= Intent(this,LoginActivity::class.java)
            startActivity(intent1)
            finish()
        }
        signupBtn.setOnClickListener {
            val name = nameET.text.toString()
            val email = emailET.text.toString()
            val password = passET.text.toString()
            val phoneNumber = phET.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && phoneNumber.isNotEmpty()) {
                // Check if the email already exists
                CoroutineScope(Dispatchers.IO).launch {
                    val existingUser = userDatabase.userDao().getUserByEmail(email)
                    if (existingUser == null) {

                        val user = User(name = name, email = email, password = password, phoneNumber = phoneNumber)
                        userDatabase.userDao().insertUser(user)
                                runOnUiThread {
                            Toast.makeText(this@SignupActivity, "Successfully Registered", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {

                        runOnUiThread {
                            Toast.makeText(this@SignupActivity, "Email already registered", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_LONG).show()
            }
        }
    }
}