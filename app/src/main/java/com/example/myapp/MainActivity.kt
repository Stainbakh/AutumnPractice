package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val registerButton = findViewById<Button>(R.id.main_activity_register)
        registerButton.setOnClickListener{
            val intentToRegScreen = Intent(this, RegistrationActivity::class.java)
            startActivity(intentToRegScreen)
        }
    }
}