package com.example.spacemate

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.spacemate.R
import com.google.android.material.card.MaterialCardView

class Setting : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        val order = findViewById<MaterialCardView>(R.id.cv1)
        val emi = findViewById<MaterialCardView>(R.id.cv2)
        val contact = findViewById<MaterialCardView>(R.id.cv3)

        order.setOnClickListener {
            // Define the destination activity you want to start
            val intent = Intent(this, MyOrders::class.java)
            startActivity(intent)
        }
        emi.setOnClickListener {
            val intent = Intent(this, EmiCalc::class.java)
            startActivity(intent)
        }
        contact.setOnClickListener {
            val intent = Intent(this, ContactActivity::class.java)
            startActivity(intent)
        }
    }
}