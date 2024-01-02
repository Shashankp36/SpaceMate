package com.example.spacemate


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ContactActivity : AppCompatActivity() , CustomAdapter2.OnItemClickListener{

    private val data = ArrayList<ItemsViewModel2>()

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)


        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview1)

        recyclerview.layoutManager = LinearLayoutManager(this)

        val dbHelper = DatabaseHelper2(this, null)


        val sharedPreferences = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("userEmail", "")
        val userName = sharedPreferences.getString("userName" ,"")




        val cursor = dbHelper.getDataByOwner(userName)
        Log.d("ContactActivity" , "userName : $userName , userEmail : $userEmail  ${cursor.moveToLast()}")
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper2.ID_col))
                val Oname1 = cursor.getString(cursor.getColumnIndex(DatabaseHelper2.Owner_name_col))
                val Bmail = cursor.getString(cursor.getColumnIndex(DatabaseHelper2.Booker_email_col))
                val phn = cursor.getString(cursor.getColumnIndex(DatabaseHelper2.Booker_phn_col))
                val Bname = cursor.getString(cursor.getColumnIndex(DatabaseHelper2.Booker_name_col))
                val houseName = cursor.getString(cursor.getColumnIndex(DatabaseHelper2.House_name_col))
                val location = cursor.getString(cursor.getColumnIndex(DatabaseHelper2.Location_col))
                val rent = cursor.getString(cursor.getColumnIndex(DatabaseHelper2.Rent_col))

                Log.d("ContactActivity", "ID: $id, Owner: $Oname1, Email: $Bmail, Booker: $Bname, House: $houseName, Location: $location, Rent: $rent")

                data.add(ItemsViewModel2(
                    Bname,
                    Bmail,
                    phn,
                    location,
                    rent,
                    houseName
                ))
            } while (cursor.moveToNext())
            val adapter = CustomAdapter2(data)
            adapter.setOnItemClickListener(this)
            recyclerview.adapter = adapter
        }else {
            Log.d("ContactActivity", "No data retrieved for user: $userName")
        }

        cursor.close()
        dbHelper.close()

    }
    override fun onItemClick(position: Int) {
        val item = data[position]
        //Toast.makeText(this, "Clicked on ${item.hName}", Toast.LENGTH_SHORT).show()

        //val bundle = Bundle()

        //bundle.putString("hName", item.hName)

//        val intent = Intent(this, Rent::class.java)
//        intent.putExtras(bundle)
//        startActivity(intent)
        val phoneNumber = "123456789" // Replace with the desired phone number
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${item.phn}")
        startActivity(intent)

    }

}