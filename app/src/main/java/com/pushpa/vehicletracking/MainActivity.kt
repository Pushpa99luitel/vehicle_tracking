package com.pushpa.vehicletracking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    private lateinit var etPickup:EditText
    private lateinit var map:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etPickup=findViewById(R.id.etPickup)
        map=findViewById(R.id.map)

        map.setOnClickListener {
            startActivity(Intent(this,MapsActivity::class.java))
        }
    }
}