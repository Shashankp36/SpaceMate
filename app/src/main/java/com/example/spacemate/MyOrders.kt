package com.example.spacemate

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
class MyOrders : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_orders)

        sharedPreferences = getSharedPreferences("FormData", Context.MODE_PRIVATE)

        val cardContainer = findViewById<LinearLayout>(R.id.cardContainer)
        val cardView = layoutInflater.inflate(R.layout.card_container, null) as CardView
        val nameTextView = cardView.findViewById<TextView>(R.id.nameTextView)
        val addressTextView = cardView.findViewById<TextView>(R.id.addressTextView)
        val dateTextView = cardView.findViewById<TextView>(R.id.dateTextView)
        val timeTextView = cardView.findViewById<TextView>(R.id.timeTextView)
        val titleTextView = cardView.findViewById<TextView>(R.id.titleTextView) // Replace with the actual ID of your title TextView

        val name = sharedPreferences.getString("name", "")
        val address = sharedPreferences.getString("address", "")
        val selectedDate = sharedPreferences.getString("selectedDate", "")
        val selectedTime = sharedPreferences.getString("selectedTime", "")
        val cardViewTitle = sharedPreferences.getString("cardViewTitle", "")
        nameTextView.text = name
        addressTextView.text = address
        dateTextView.text = selectedDate
        timeTextView.text = selectedTime
        titleTextView.text = cardViewTitle
        cardContainer.addView(cardView) // Add the CardView to the LinearLayout or your desired container
    }
}
