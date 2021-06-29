package com.example.come

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class OrderPlacedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_placed)
        findViewById<Button>(R.id.continueBtn).setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext,HomeActivity::class.java)
            finish()
            startActivity(intent)
        })

    }
}