package com.example.spacemate
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var signup: Button
    private lateinit var login: Button
    private lateinit var email: EditText
    private lateinit var pass: EditText

    private lateinit var userDatabase: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userDatabase = UserDatabase.getDatabase(this)

        signup = findViewById(R.id.signupbtn)
        login = findViewById(R.id.loginbtn)
        email = findViewById(R.id.email)
        pass = findViewById(R.id.pass)

        signup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
        login.setOnClickListener {
            val inputEmail = email.text.toString()
            val inputPassword = pass.text.toString()


            CoroutineScope(Dispatchers.IO).launch {
                val user = userDatabase.userDao().getUserByEmail(inputEmail)


                if (user != null && user.password == inputPassword) {
                    val sharedPreferencesManager = SharedPreferencesManager(this@LoginActivity)
                    sharedPreferencesManager.setLoggedIn(true)
                    runOnUiThread {

                        Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, Dashboard::class.java)
                        val sharedPreferences = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("userEmail", inputEmail) // userEmail is the email of the logged-in user
                        editor.putString("userName", user.name)
                        editor.putString("userPhn", user.phoneNumber)
                        editor.apply()
                        startActivity(intent)
                        finish()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
