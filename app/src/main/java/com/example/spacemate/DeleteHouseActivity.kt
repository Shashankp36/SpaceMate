package com.example.spacemate

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class DeleteHouseActivity : AppCompatActivity() {
    private lateinit var btn : Button
    private lateinit var et  : EditText
    @SuppressLint("MissingInflatedId", "Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_house)
        btn = findViewById(R.id.buttonUnlist)
        et = findViewById(R.id.editTextHouseNamed)

        btn.setOnClickListener {
            val dbHelper = DatabaseHelper(this, null)
            val hName1 = et.text.toString()
            val cursor = dbHelper.getDataByHouseName(hName1)

            if (cursor.moveToFirst()) {
                do {
                    val name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME_col))
                    val houseName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.House_name_col))
                    val location = cursor.getString(cursor.getColumnIndex(DatabaseHelper.Location_col))
                    val rent = cursor.getString(cursor.getColumnIndex(DatabaseHelper.Rent_col))

                    val sharedPreferences = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
                    val userName = sharedPreferences.getString("userName", "")

                    if (userName == name) {
                        dbHelper.delete(hName1)
                        Toast.makeText(this, "House deleted", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "You are not the owner", Toast.LENGTH_LONG).show()
                    }
                } while (cursor.moveToNext())
            } else {
                Toast.makeText(this, "House not found", Toast.LENGTH_LONG).show()
            }

            cursor.close()
            dbHelper.close()
        }




    }
}