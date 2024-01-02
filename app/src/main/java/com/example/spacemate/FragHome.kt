package com.example.spacemate

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale

class FragHome : Fragment() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationTextView: TextView
    private val permissionId = 2
    lateinit var cv1 : MaterialCardView
    lateinit var cv2 : MaterialCardView
    lateinit var cv3 : MaterialCardView
    lateinit var cv4 : MaterialCardView
    lateinit var cv5 : MaterialCardView
    lateinit var cv6 : MaterialCardView
    lateinit var cv7 : MaterialCardView
    lateinit var cv8 : MaterialCardView
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_frag_home, container, false)
        val imageView = view.findViewById<ImageView>(R.id.profile)
        locationTextView = view.findViewById(R.id.location)
        imageView.setOnClickListener {
            val intent = Intent(activity, Profile::class.java)
            startActivity(intent)
        }
        val sharedPreference1  = this.activity?.getSharedPreferences("category", AppCompatActivity.MODE_PRIVATE)
        val myEdit = sharedPreference1?.edit()
        cv1 = view.findViewById(R.id.cv1)
        cv1.setOnClickListener {
            val intent = Intent(activity ,booking::class.java )
            val sharedPreference1  = this.activity?.getSharedPreferences("category", AppCompatActivity.MODE_PRIVATE)
            val myEdit = sharedPreference1?.edit()
            myEdit?.putString("cat", "Family")
            myEdit?.apply()
            startActivity(intent)
        }

        cv2 = view.findViewById(R.id.cv2)
        cv2.setOnClickListener {
            val intent = Intent(activity ,booking::class.java )
            val sharedPreference1  = this.activity?.getSharedPreferences("category", AppCompatActivity.MODE_PRIVATE)
            val myEdit = sharedPreference1?.edit()
            myEdit?.putString("cat", "Hotel")
            myEdit?.apply()
            startActivity(intent)
        }

        cv3 = view.findViewById(R.id.cv3)
        cv3.setOnClickListener {
            val intent = Intent(activity ,booking::class.java )
            val sharedPreference1  = this.activity?.getSharedPreferences("category", AppCompatActivity.MODE_PRIVATE)
            val myEdit = sharedPreference1?.edit()
            myEdit?.putString("cat", "Office")
            myEdit?.apply()
            startActivity(intent)
        }

        cv4 = view.findViewById(R.id.cv4)
        cv4.setOnClickListener {
            val intent = Intent(activity ,booking::class.java )
            val sharedPreference1  = this.activity?.getSharedPreferences("category", AppCompatActivity.MODE_PRIVATE)
            val myEdit = sharedPreference1?.edit()
            myEdit?.putString("cat", "Hostel")
            myEdit?.apply()
            startActivity(intent)
        }
        cv5 = view.findViewById(R.id.cv5)
        cv5.setOnClickListener {
            val intent = Intent(activity ,booking::class.java )
            val sharedPreference1  = this.activity?.getSharedPreferences("category", AppCompatActivity.MODE_PRIVATE)
            val myEdit = sharedPreference1?.edit()
            myEdit?.putString("cat", "PG")
            myEdit?.apply()
            startActivity(intent)
        }

        cv6 = view.findViewById(R.id.cv6)
        cv6.setOnClickListener {
            val intent = Intent(activity ,booking::class.java )
            val sharedPreference1  = this.activity?.getSharedPreferences("category", AppCompatActivity.MODE_PRIVATE)
            val myEdit = sharedPreference1?.edit()
            myEdit?.putString("cat", "Bachelor")
            myEdit?.apply()
            startActivity(intent)
        }

        cv7 = view.findViewById(R.id.cv7)
        cv7.setOnClickListener {
            val intent = Intent(activity ,booking::class.java )
            val sharedPreference1  = this.activity?.getSharedPreferences("category", AppCompatActivity.MODE_PRIVATE)
            val myEdit = sharedPreference1?.edit()
            myEdit?.putString("cat", "Shop")
            myEdit?.apply()
            startActivity(intent)
        }

        cv8 = view.findViewById(R.id.cv8)
        cv8.setOnClickListener {
            val intent = Intent(activity ,booking::class.java )
            val sharedPreference1  = this.activity?.getSharedPreferences("category", AppCompatActivity.MODE_PRIVATE)
            val myEdit = sharedPreference1?.edit()
            myEdit?.putString("cat", "Library")
            myEdit?.apply()
            startActivity(intent)
        }
        val drawable = requireContext().resources.getDrawable(R.drawable.location)
        drawable.setBounds(0, 0, 40, 40) // Set the drawable size to 40dp x 40dp
        locationTextView.setCompoundDrawables(drawable, null, null, null)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getLocation()

        val add: FloatingActionButton = view.findViewById(R.id.fab3)
        val home: FloatingActionButton = view.findViewById(R.id.fab1)
        val delete: FloatingActionButton = view.findViewById(R.id.fab2)
        var visi = false
        add.setOnClickListener{
            if(!visi){
                delete.show()
                home.show()
                home.visibility = View.VISIBLE
                delete.visibility = View.VISIBLE
                add.setImageDrawable(resources.getDrawable(R.drawable.cancel_24))
                visi = true
            }else{
                delete.hide()
                home.hide()
                home.visibility = View.GONE
                delete.visibility = View.GONE
                add.setImageDrawable(resources.getDrawable(R.drawable.ic_add))
                visi = false
            }
            home.setOnClickListener{
                //Toast.makeText(this@FragHome , "clicked on Home " , Toast.LENGTH_LONG).show()
                val intent = Intent(activity, AddHouse::class.java)
                startActivity(intent)
            }
            delete.setOnClickListener{
                //Toast.makeText(this , "clicked on setting " , Toast.LENGTH_LONG).show()
                val intent = Intent(activity, DeleteHouseActivity::class.java)
                startActivity(intent)
            }
        }
        /*val fab: FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener {

            val intent = Intent(activity, AddHouse::class.java)
            startActivity(intent)
        }*/

        return view
    }
    @SuppressLint("MissingPermission")
    private  fun getLocation(){
        if(checkPermissions()){
            if(isLocationEnabled()){
                fusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { location: Location? ->
                        location?.let {
                            val geocoder = Geocoder(requireContext(), Locale.getDefault())
                            val list: MutableList<Address>? =
                                geocoder.getFromLocation(location.latitude, location.longitude, 1)
                            if (list!!.isNotEmpty()) {
                                val city = list[0].locality
                                locationTextView.text = "$city"
                            }
                        }
                    }
            }else{
                Toast.makeText(requireContext(), "Please turn on Location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }else{
            requestPermissions()
        }
    }
    private fun isLocationEnabled():Boolean{
        val locationManager: LocationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    private fun checkPermissions(): Boolean {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }
}


