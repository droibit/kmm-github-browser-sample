package com.example.android.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shared.Greeting
import android.widget.TextView
import com.example.android.app.R

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = greet()
    }
}
