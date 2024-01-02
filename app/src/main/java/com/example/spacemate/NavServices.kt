package com.example.spacemate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class NavServices : AppCompatActivity() {
    lateinit var cv1:CardView
    lateinit var cv2:CardView
    lateinit var cv3:CardView
    lateinit var cv4:CardView
    lateinit var cv5:CardView
    lateinit var cv6:CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_services)
        val imageView = findViewById<ImageView>(R.id.profile)
        val backbtn = findViewById<ImageView>(R.id.back)
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)

        val images = listOf(
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image1
        )
        val adapter1 = ImageAdapter(images)
        viewPager.adapter = adapter1
        viewPager.setPageTransformer(HorizontalPeekPageTransformer())
        imageView.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }
        cv1=findViewById(R.id.cv1)
        cv2=findViewById(R.id.cv2)
        cv3=findViewById(R.id.cv3)
        cv4=findViewById(R.id.cv4)
        cv5=findViewById(R.id.cv5)
        cv6=findViewById(R.id.cv6)
//        cv1.setOnClickListener{
//            val intent = Intent(this, ServiceActivity::class.java)
//            startActivity(intent)
//        }

        cv1.setOnClickListener {
            openForm(1) // Pass 1 to indicate the first CardView
        }

        cv2.setOnClickListener {
            openForm(2)
        }
        cv3.setOnClickListener {
            openForm(3)
        }
        cv4.setOnClickListener {
            openForm(4)
        }
        cv5.setOnClickListener {
            openForm(5)
        }
        cv6.setOnClickListener {
            openForm(6)
        }

    }

    private fun openForm(cardViewIndex: Int) {
        val intent = Intent(this, FormActivity::class.java)
        intent.putExtra("cardViewIndex", cardViewIndex)
        startActivity(intent)

    }
}