package com.example.drinkaway

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }


    fun addActivity(view: View) {
        val intent = Intent(this, AddActivity::class.java)
        startActivity(intent)
    }

    fun playActivity(view: View) {
        val intent = Intent(this, PlayActivity::class.java)
        startActivity(intent)
    }

    fun creditActivity(view: View) {
        val intent = Intent(this, CreditActivity::class.java)
        startActivity(intent)
    }

    fun setupActivity(view: View) {
        val intent = Intent(this, SetupActivity::class.java)
        startActivity(intent)
    }
}
