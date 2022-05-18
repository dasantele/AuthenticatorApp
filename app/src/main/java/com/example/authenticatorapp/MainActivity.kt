package com.example.authenticatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.authenticatorapp.data.repository.BackendRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val boton= findViewById<Button>(R.id.button)
        boton.setOnClickListener{
            val repository= BackendRepository()
            repository.requestLogin("Daniel", "Daniel")
        }
    }
}