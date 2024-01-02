package com.example.spacemate


import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class booking : AppCompatActivity() , CustomAdapter.OnItemClickListener{
    private lateinit var myDB: DatabaseHelper
    private val name = ArrayList<String>()
    private val house_name = ArrayList<String>()
    private val location = ArrayList<String>()
    private val rent = ArrayList<String>()
    private val data = ArrayList<ItemsViewModel>()
    private lateinit var catTV : TextView

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)


        catTV = findViewById(R.id.selectCategoryText)
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel


        // This loop will create 20 Views containing
        // the image with the count of view
        //var x = SharedPreferencesManager(this)
        /*val dbHelper = DatabaseHelper(this, null)
        val cursor = dbHelper.getName()

        while (cursor.moveToNext()) {

            data.add(ItemsViewModel(R.drawable.pic1,
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getBlob(5)
                ))

        }
        val adapter = CustomAdapter(data)
        adapter.setOnItemClickListener(this)
        recyclerview.adapter = adapter*/

        val dbHelper = DatabaseHelper(this, null)

        val sh = getSharedPreferences("category", MODE_PRIVATE)
        val category = sh.getString("cat", "")
        catTV.setText(category)

        val cursor = dbHelper.getDataByCategory(category)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID_col))
                val name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME_col))
                val houseName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.House_name_col))
                val location = cursor.getString(cursor.getColumnIndex(DatabaseHelper.Location_col))
                val rent = cursor.getString(cursor.getColumnIndex(DatabaseHelper.Rent_col))
                val imageBytes = cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.Image_col))

                val bathroom = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Bathroom_col))
                val bedroom = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Bedroom_col))
                val ele = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ele_col))
                val gyeser = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.geyser_col))
                val water = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.water_col))
                val park = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.parking_col))
                val wifi = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.wifi_col))



                data.add(ItemsViewModel(R.drawable.pic1,
                    name,
                    houseName,
                    location,
                    rent,
                    imageBytes
                ))

                // Now you have the data for "ranjeet," you can use it as needed.
                // For example, create objects or display the data in your UI.
            } while (cursor.moveToNext())
            val adapter = CustomAdapter(data)
            adapter.setOnItemClickListener(this)
            recyclerview.adapter = adapter
        }

        cursor.close()
        dbHelper.close()

    }
     override fun onItemClick(position: Int) {
        val item = data[position]
        Toast.makeText(this, "Clicked on ${item.hName}", Toast.LENGTH_SHORT).show()

         val bundle = Bundle()

         bundle.putString("hName", item.hName)

         val intent = Intent(this, Rent::class.java)
         intent.putExtras(bundle)
         startActivity(intent)

    }

}