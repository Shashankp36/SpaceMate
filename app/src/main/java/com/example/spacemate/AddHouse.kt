package com.example.spacemate

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.io.ByteArrayOutputStream
import java.util.Locale

class AddHouse : AppCompatActivity() {
    private lateinit var houseNameInputLayout: EditText
    private lateinit var locationInputLayout: EditText
    private lateinit var rentInputLayout: EditText
    private lateinit var spinner: Spinner
    private lateinit var bedRoom: EditText
    private lateinit var bathroom: EditText
    private lateinit var checkboxElectricity: MaterialCheckBox
    private lateinit var checkboxGeyser: MaterialCheckBox
    private lateinit var checkboxWater: MaterialCheckBox
    private lateinit var checkboxParking: MaterialCheckBox
    private lateinit var checkboxWifi: MaterialCheckBox
    private lateinit var buttonAddHouse: Button
    private lateinit var captureImage: Button
    private lateinit var byteArr : ByteArray
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_PICK_IMAGE = 2
    var categoryInputLayout : String = ""

    private var a =0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_house)

        houseNameInputLayout = findViewById(R.id.editTextHouseName)
        //categoryInputLayout = findViewById(R.id.editTextCategory)
        bedRoom = findViewById(R.id.editTextBedroom)
        bathroom = findViewById(R.id.editTextbathroom)
        locationInputLayout = findViewById(R.id.editTextLocation)
        rentInputLayout = findViewById(R.id.editTextRent)
        checkboxElectricity = findViewById(R.id.checkboxElectricity)
        checkboxGeyser = findViewById(R.id.checkboxGeyser)
        checkboxWater = findViewById(R.id.checkboxWater)
        checkboxParking = findViewById(R.id.checkboxParking)
        checkboxWifi = findViewById(R.id.checkboxWifi)
        buttonAddHouse = findViewById(R.id.buttonAddHouse)
        captureImage = findViewById(R.id.captureImage)


        spinner = findViewById<Spinner>(R.id.spinnerCategory)

        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        spinner.adapter = adapter

        // You can also listen for item selection events if needed
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                categoryInputLayout = spinner.selectedItem.toString()
                // Do something with the selected category
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        buttonAddHouse.setOnClickListener { addHouse() }
        captureImage.setOnClickListener {
            val options = arrayOf("Capture Image", "Pick from Gallery")
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Choose Image Source")
            builder.setItems(options) { _, which ->
                when (which) {
                    0 -> captureImage()
                    1 -> pickImageFromGallery()
                }
            }
            builder.show()
        }
    }
    private fun captureImage() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_PICK_IMAGE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    byteArr = getByteArrayFromBitmap(imageBitmap)
                }
                REQUEST_PICK_IMAGE -> {
                    val imageUri = data?.data
                    val imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                    byteArr = getByteArrayFromBitmap(imageBitmap)
                }
            }
        }
    }
        /*captureImage.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }*/




    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            byteArr = getByteArrayFromBitmap(imageBitmap)
        }
    }*/

    private fun getByteArrayFromBitmap(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }


    /*companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private val REQUEST_PICK_IMAGE = 2
        private const val LAST_IMAGE_ID_KEY = "last_saved_image_id"
    }*/
    private fun addHouse() {

        val category  = categoryInputLayout
        val houseName = houseNameInputLayout.text.toString()
        val bathroom1 = bathroom.text.toString().toInt()
        val bedroom1 = bedRoom.text.toString().toInt()
        val location = locationInputLayout.text.toString()
        val rent = rentInputLayout.text.toString()



        val hasElectricity = if (checkboxElectricity.isChecked) 1 else 0
        val hasGeyser = if (checkboxGeyser.isChecked) 1 else 0
        val hasWater = if (checkboxWater.isChecked) 1 else 0
        val hasParking = if (checkboxParking.isChecked) 1 else 0
        val hasWifi = if (checkboxWifi.isChecked) 1 else 0


        val sharedPreferences = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("userName", "")
        val sharedPreference1 = getSharedPreferences("category", MODE_PRIVATE)
        val myEdit = sharedPreference1.edit()
        myEdit.putString("cat", category)
        myEdit.apply()

        val dbHelper = DatabaseHelper(this, null)
        dbHelper.addName(userName!!,"",category,houseName,location, rent,byteArr,
            bathroom1 , bedroom1 ,hasElectricity , hasGeyser
        ,hasWater,hasParking,
            hasWifi)
        Toast.makeText(this, "House added", Toast.LENGTH_LONG).show()
        dbHelper.close()
    }




}