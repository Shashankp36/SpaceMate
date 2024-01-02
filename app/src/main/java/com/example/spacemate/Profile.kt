package com.example.spacemate

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Profile : AppCompatActivity() {
    private lateinit var ti1: TextInputLayout
    private lateinit var ti2: TextInputLayout
    private lateinit var ti3: TextInputLayout
    private lateinit var nameTV: TextView
    private lateinit var emailTV: TextView
    //private lateinit var nameTextView: TextView
    //private lateinit var emailTextView: TextView
    //private lateinit var phoneNumberTextView: TextView

    private lateinit var userDatabase: UserDatabase
    private lateinit var logout:Button
    private lateinit var update:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        userDatabase = UserDatabase.getDatabase(this)

        ti1 = findViewById(R.id.full_name_profile) as TextInputLayout
        ti2 = findViewById(R.id.email) as TextInputLayout
        ti3 = findViewById(R.id.phoneNumber1) as TextInputLayout
        nameTV = findViewById(R.id.fullname_field)
        emailTV = findViewById(R.id.username_field)

        //nameTextView = findViewById(R.id.nametV)
        //emailTextView = findViewById(R.id.emailTV)
        //phoneNumberTextView = findViewById(R.id.phoneTV)
        val sharedPreferences = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("userEmail", "")

        if (!userEmail.isNullOrEmpty()) {
            // Query the user by email and display their details
            CoroutineScope(Dispatchers.IO).launch {
                val user = userDatabase.userDao().getUserByEmail(userEmail)

                if (user != null) {
                    runOnUiThread {

                        ti1.editText?.setText(user.name)
                        ti2.editText?.setText(user.email)
                        ti3.editText?.setText(user.phoneNumber)
                        nameTV.text = user.name
                        emailTV.text = user.email
                        //nameTextView.text = "Name: " + user.name
                        //emailTextView.text = "Email: " + user.email
                        //phoneNumberTextView.text = "Phone: " + user.phoneNumber
                    }
                } else {
                   
                }
            }
        }

        update = findViewById(R.id.b1)
        /*update.setOnClickListener {
            val user1 = userDatabase.userDao().getUserByEmail(userEmail!!)

            val user = User(name = ti1.editText.toString(),
                email = ti2.editText.toString(),
                password = user1!!.password,
                phoneNumber = ti3.editText.toString())

            CoroutineScope(Dispatchers.IO).launch {
                userDatabase.userDao().update(user)
            }
            val sharedPreferences = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("userEmail", ti2.editText.toString()) // userEmail is the email of the logged-in user
            editor.apply()


        }*/













        logout=findViewById(R.id.logout)
        logout.setOnClickListener {
            val sharedPreferencesManager = SharedPreferencesManager(this)
            sharedPreferencesManager.setLoggedIn(false)
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}