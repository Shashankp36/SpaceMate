package com.example.spacemate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferencesManager = SharedPreferencesManager(this)
        if (!sharedPreferencesManager.isLoggedIn()) {
            // Redirect to the login page
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()

            }, 1500)
        }else{
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
            finish()
        }

    }
}