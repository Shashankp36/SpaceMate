package com.example.spacemate

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText

class EmiCalc : AppCompatActivity() {
    lateinit var calculateButton:Button
    private lateinit var loanAmountEditText: TextInputEditText
    private lateinit var interestRateEditText: TextInputEditText
    private lateinit var loanTermEditText: TextInputEditText
    private lateinit var downPaymentEditText: TextInputEditText
    private lateinit var resultTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emi_calc)

        loanAmountEditText = findViewById(R.id.loanAmountEditText)
        interestRateEditText = findViewById(R.id.interestRateEditText)
        loanTermEditText = findViewById(R.id.loanTermEditText)
        downPaymentEditText = findViewById(R.id.downPaymentEditText)
        resultTextView=findViewById(R.id.resultTextView)
        calculateButton=findViewById(R.id.calculateButton)
        calculateButton.setOnClickListener {
            calculateEMI()
        }
    }
    @SuppressLint("SetTextI18n")
    private fun calculateEMI() {
        val loanAmount = loanAmountEditText.text.toString().toDouble()
        val interestRate = interestRateEditText.text.toString().toDouble()
        val loanTerm = loanTermEditText.text.toString().toDouble()
        val downPayment = downPaymentEditText.text.toString().toDouble()

        val principal = loanAmount - downPayment
        val monthlyInterestRate = interestRate / 12 / 100
        val totalMonths = loanTerm * 12

        if (totalMonths.toInt() == 0 || monthlyInterestRate == 0.0) {
            resultTextView.text = "Invalid input. Please check the loan term and interest rate."
            return
        }

        val emi = principal * monthlyInterestRate / (1 - (1 + monthlyInterestRate).let { rate ->
            var denominator = 1.0
            repeat(totalMonths.toInt()) {
                denominator *= (1 + rate)
            }
            denominator
        })
        resultTextView.text = "EMI: ${String.format("%.2f", emi)}"
    }
}