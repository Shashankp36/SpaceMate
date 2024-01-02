package com.example.spacemate

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.FileProvider
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.math.log


class FormActivity : AppCompatActivity() {
    lateinit var submit: Button
    lateinit var editTextName:EditText
    lateinit var editTextAddress:EditText
    lateinit var textViewCardViewTitle:TextView
    lateinit var calendar: Calendar
    lateinit var textViewSelectedDate: TextView
    lateinit var textViewSelectedTime: TextView
    lateinit var sharedPreferences: SharedPreferences
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        submit=findViewById(R.id.buttonSubmit)
        editTextName=findViewById(R.id.editTextName)
        editTextAddress=findViewById(R.id.editTextAddress)

        textViewCardViewTitle=findViewById(R.id.textViewCardViewTitle)

        val cardViewIndex = intent.getIntExtra("cardViewIndex", -1)

        when (cardViewIndex) {
            1 -> {
                setFormFields("Name", "Address", "Painting")
            }
            2 -> {
                setFormFields("Name", "Address", "Cleaning")
            }
            3 -> {
                setFormFields("Name", "Address", "Repair")
            }
            4 -> {
                setFormFields("Name", "Address", "Plumbing")
            }
            5 -> {
                setFormFields("Name", "Address", "Carpentry")
            }
            6 -> {
                setFormFields("Name", "Address", "Electrician")
            }
            // Add more cases for other CardViews as needed
        }
        sharedPreferences = getSharedPreferences("FormData", Context.MODE_PRIVATE)
        submit.setOnClickListener {
            val name = editTextName.text.toString()
            val address = editTextAddress.text.toString()
            val selectedDate = textViewSelectedDate.text.toString()
            val selectedTime = textViewSelectedTime.text.toString()
            val cardViewTitle=textViewCardViewTitle.text.toString()
            Log.d("FormActivity", "Name: $name")
            Log.d("FormActivity", "Address: $address")
            Log.d("FormActivity", "Selected Date: $selectedDate")
            Log.d("FormActivity", "Selected Time: $selectedTime")
            Log.d("FormActivity", "Card View Title: $cardViewTitle")
            // Save the collected data in SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("name", name)
            editor.putString("address", address)
            editor.putString("selectedDate", selectedDate)
            editor.putString("selectedTime", selectedTime)
            editor.putString("cardViewTitle", cardViewTitle) // Save card view title
            editor.apply()
            showReceiptDialog(name, address, selectedDate, selectedTime, cardViewTitle)


            // Return to MainActivity or perform other actions
//                finish()
        }
        calendar = Calendar.getInstance()
        textViewSelectedDate = findViewById(R.id.textViewSelectedDate)
        textViewSelectedTime = findViewById(R.id.textViewSelectedTime)

        val buttonPickDate = findViewById<Button>(R.id.buttonPickDate)
        val buttonPickTime = findViewById<Button>(R.id.buttonPickTime)

        buttonPickDate.setOnClickListener {
            showDatePickerDialog()
        }

        buttonPickTime.setOnClickListener {
            showTimePickerDialog()
        }
    }
    private fun setFormFields(nameHint: String, addressHint: String, cardViewTitle: String) {
        editTextName.hint = nameHint

        editTextAddress.hint = addressHint

        // Set the card view title dynamically
        textViewCardViewTitle.text = cardViewTitle
    }
    private fun showDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
                calendar.set(Calendar.YEAR, selectedYear)
                calendar.set(Calendar.MONTH, selectedMonth)
                calendar.set(Calendar.DAY_OF_MONTH, selectedDay)

                // Display the selected date in textViewSelectedDate
                val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                val formattedDate = dateFormat.format(calendar.time)
                textViewSelectedDate.text = "$formattedDate"
            },
            year, month, day
        )

        datePickerDialog.show()
    }
    private fun showTimePickerDialog() {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { view, selectedHour, selectedMinute ->
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
                calendar.set(Calendar.MINUTE, selectedMinute)

                // Display the selected time in textViewSelectedTime
                val timeFormat = SimpleDateFormat("HH:mm")
                val formattedTime = timeFormat.format(calendar.time)
                textViewSelectedTime.text = "$formattedTime"
            },
            hour, minute, true
        )

        timePickerDialog.show()
    }
    //    private fun showReceiptDialog(
//        name: String,
//        address: String,
//        selectedDate: String,
//        selectedTime: String,
//        cardViewTitle: String
//    ) {
//        // Generate a unique transaction ID (you can use your own logic)
//        val transactionId = generateUniqueTransactionId()
//
//        // Define a predefined price
//        val predefinedPrice = 50.0 // Replace with your predefined price
//
//        val receiptText = "Transaction ID: $transactionId\n" +
//                "Name: $name\nAddress: $address\n" +
//                "Selected Date: $selectedDate\nSelected Time: $selectedTime\n" +
//                "Service: $cardViewTitle\n" +
//                "Predefined Price: $$predefinedPrice"
//
//        val dialog = AlertDialog.Builder(this)
//            .setTitle("Receipt")
//            .setMessage(receiptText)
//            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
//            .create()
//
//        dialog.show()
//    }
    private fun showReceiptDialog(
        name: String,
        address: String,
        selectedDate: String,
        selectedTime: String,
        cardViewTitle: String
    ) {
        // Generate a unique transaction ID (you can use your own logic)
        val transactionId = generateUniqueTransactionId()

        // Define a predefined price
        val predefinedPrice = 50.0 // Replace with your predefined price

        // Generate the PDF receipt
        val pdfFile = generatePdfReceipt(name, address, selectedDate, selectedTime, cardViewTitle, transactionId, predefinedPrice)

        // Create a dialog to confirm the download
        val dialog = AlertDialog.Builder(this)
            .setTitle("Receipt")
            .setMessage("Transaction ID: $transactionId\nName: $name\nAddress: $address\n" +
                    "Selected Date: $selectedDate\nSelected Time: $selectedTime\n" +
                    "Service: $cardViewTitle\nPredefined Price: $$predefinedPrice")
            .setPositiveButton("Download PDF") { dialog, _ ->
                initiateDownload(pdfFile)
                dialog.dismiss()
            }
            .setNegativeButton("OK") { dialog, _ -> dialog.dismiss() }
            .create()

        dialog.show()
    }
    private fun generatePdfReceipt(name: String, address: String, selectedDate: String, selectedTime: String, cardViewTitle: String, transactionId: String, predefinedPrice: Double): File {
        val fileName = "Receipt_${System.currentTimeMillis()}.pdf"
        val filePath = File(filesDir, fileName)

        val pdfDocument = PdfDocument(PdfWriter(filePath))
        val document = Document(pdfDocument)

        document.add(Paragraph("Receipt").setFontSize(18f).setBold())
        document.add(Paragraph("Transaction ID: $transactionId"))
        document.add(Paragraph("Name: $name"))
        document.add(Paragraph("Address: $address"))
        document.add(Paragraph("Selected Date: $selectedDate"))
        document.add(Paragraph("Selected Time: $selectedTime"))
        document.add(Paragraph("Service: $cardViewTitle"))
        document.add(Paragraph("Predefined Price: $$predefinedPrice"))

        document.close()

        return filePath
    }

    private fun initiateDownload(pdfFile: File) {
        val downloadDirectory = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)

        if (downloadDirectory != null) {
            // Create a directory if it doesn't exist
            if (!downloadDirectory.exists()) {
                downloadDirectory.mkdirs()
            }

            val destinationFile = File(downloadDirectory, pdfFile.name)

            try {
                Files.copy(pdfFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
                Toast.makeText(this,"PDF downloaded to ${destinationFile.absolutePath}",Toast.LENGTH_LONG).show()
                Log.d("pdf", "Path: ${destinationFile.absolutePath}")
            } catch (e: Exception) {
                Toast.makeText(this,"Error while saving the PDF: ${e.message}",Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this,"Download directory not available",Toast.LENGTH_SHORT).show()
        }
    }


    private fun generateUniqueTransactionId(): String {
        // Implement your logic to generate a unique transaction ID (e.g., using UUID)
        return java.util.UUID.randomUUID().toString()
    }


}
