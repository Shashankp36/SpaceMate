package com.example.spacemate

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import com.example.spacemate.DatabaseHelper.Companion.Bathroom_col
import com.example.spacemate.DatabaseHelper.Companion.Bedroom_col
import com.example.spacemate.DatabaseHelper.Companion.House_name_col
import com.example.spacemate.DatabaseHelper.Companion.ID_col
import com.example.spacemate.DatabaseHelper.Companion.Image_col
import com.example.spacemate.DatabaseHelper.Companion.Location_col
import com.example.spacemate.DatabaseHelper.Companion.NAME_col
import com.example.spacemate.DatabaseHelper.Companion.Rent_col
import com.example.spacemate.DatabaseHelper.Companion.ele_col
import com.example.spacemate.DatabaseHelper.Companion.geyser_col
import com.example.spacemate.DatabaseHelper.Companion.parking_col
import com.example.spacemate.DatabaseHelper.Companion.water_col
import com.example.spacemate.DatabaseHelper.Companion.wifi_col
import com.google.android.material.card.MaterialCardView

class Rent : AppCompatActivity() {
    lateinit var ownerName : TextView
    lateinit var houseNameTV : TextView
    lateinit var locationTV : TextView
    lateinit var rentTV : TextView
    lateinit var cv9: MaterialCardView
    lateinit var cv1: MaterialCardView
    lateinit var cv2: MaterialCardView
    lateinit var cv3: MaterialCardView
    lateinit var cv4: MaterialCardView
    lateinit var cv5: MaterialCardView
    lateinit var cv6: MaterialCardView
    lateinit var cv7: MaterialCardView
    lateinit var cv8: MaterialCardView
    lateinit var tv1 : TextView
    lateinit var tv2 : TextView
    lateinit var tv3 : TextView
    lateinit var tv5 : TextView
    lateinit var tv6 : TextView
    lateinit var tv7 : TextView
    lateinit var tv8 : TextView
    lateinit var tv9 : TextView
    lateinit var tv4 : TextView
    private lateinit var Oname : String
    private lateinit var Oemail : String
    private lateinit var Bname : String
    private lateinit var Hname : String
    private lateinit var Hlocation : String
    private lateinit var Hrent : String



    lateinit var rentBtn : Button
    @SuppressLint("Range", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent)
        rentBtn = findViewById(R.id.rent1)
        ownerName = findViewById(R.id.ownerName)
        houseNameTV = findViewById(R.id.houseName)
        locationTV = findViewById(R.id.location)
        rentTV = findViewById(R.id.rent)
        cv1 = findViewById(R.id.cv1)
        cv2 = findViewById(R.id.cv2)
        cv3 = findViewById(R.id.cv3)
        cv4 = findViewById(R.id.cv4)
        cv5 = findViewById(R.id.cv5)
        cv6 = findViewById(R.id.cv6)
        cv7 = findViewById(R.id.cv7)
        cv8 = findViewById(R.id.cv8)
        cv9 = findViewById(R.id.cv9)

        tv1 = findViewById(R.id.payment_desc1)
        tv2 = findViewById(R.id.payment_desc2)
        tv3= findViewById(R.id.payment_desc3)
        tv4 = findViewById(R.id.payment_desc4)
        tv5 = findViewById(R.id.payment_desc5)
        tv6 = findViewById(R.id.payment_desc6)
        tv7 = findViewById(R.id.payment_desc7)
        tv8 = findViewById(R.id.payment_desc8)
        tv9 = findViewById(R.id.payment_desc9)


        val dbHelper = DatabaseHelper(this, null)
        val bundle = intent.extras
        val houseNameToSearch = bundle!!.getString("hName","ranjeet")
        val cursor = dbHelper.getDataByHouseName(houseNameToSearch)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(ID_col))
                val name = cursor.getString(cursor.getColumnIndex(NAME_col))
                val houseName = cursor.getString(cursor.getColumnIndex(House_name_col))
                val location = cursor.getString(cursor.getColumnIndex(Location_col))
                val rent = cursor.getString(cursor.getColumnIndex(Rent_col))
                val imageBytes = cursor.getBlob(cursor.getColumnIndex(Image_col))

                val bathroom = cursor.getInt(cursor.getColumnIndex(Bathroom_col)).toString()
                val bedroom = cursor.getInt(cursor.getColumnIndex(Bedroom_col)).toString()
                val ele = cursor.getInt(cursor.getColumnIndex(ele_col))
                val gyeser = cursor.getInt(cursor.getColumnIndex(geyser_col))
                val water = cursor.getInt(cursor.getColumnIndex(water_col))
                val park = cursor.getInt(cursor.getColumnIndex(parking_col))
                val wifi = cursor.getInt(cursor.getColumnIndex(wifi_col))




//                var bathTV : TextView = findViewById(R.id.bathroom)
//                var bedTV : TextView = findViewById(R.id.bedroom)
//                bathTV.setText(bathroom.toString())
//                bedTV.setText(bedroom.toString())

                Oname = name
                Hname = houseName
                Hlocation = location
                Hrent = rent
                ownerName.setText("Owner: $name")
                houseNameTV.setText("House: $houseName")
                locationTV.setText("Location: $location")
                rentTV.setText("Rent: ₹$rent per month")



                tv1.setText("$bedroom Bedrooms")
                tv2.setText("$bathroom Bathrooms")
                if(ele==0){
                    tv3.setText("Not Available")
                }
                if(gyeser==0){
                    tv4.setText("Not Available")
                }
                if(water==0){
                    tv5.setText("Not Available")
                }
                if(park==0){
                    tv6.setText("Not Available")
                }
                if(wifi==0){
                    tv7.setText("Not Available")
                }



                // Now you have the data for "ranjeet," you can use it as needed.
                // For example, create objects or display the data in your UI.
            } while (cursor.moveToNext())
        }

        cursor.close()
        dbHelper.close()

        rentBtn.setOnClickListener {
            val sharedPreferences = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
            val userEmail = sharedPreferences.getString("userEmail", "")
            val userName = sharedPreferences.getString("userName" ,"")
            val userPhn = sharedPreferences.getString("userPhn" ,"")
            val dbHelper2 = DatabaseHelper2(this, null)
            ownerName.text.toString()
            if(Oname ==  userName){
                Toast.makeText(this, "You cannot rent your own house", Toast.LENGTH_LONG).show()

            }else{
                dbHelper2.addName(Oname ,
                    userEmail!!,userPhn, userName!!,
                    Hname,
                    Hlocation,
                    Hrent)
                Toast.makeText(this, "We have sent your contact information to the owner", Toast.LENGTH_LONG).show()
                dbHelper2.close()
            }


        }


    }
}