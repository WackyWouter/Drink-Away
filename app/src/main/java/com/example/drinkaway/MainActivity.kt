package com.example.drinkaway

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        drinkResponsibly()
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

    private fun drinkResponsibly() {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Warning")
        builder.setMessage("Please drink responsibly. By continuing, you agree that you are responsible for any consequences that may result from the use of Drink Away")
        builder.setPositiveButton("Okey") { dialog, which -> }
        builder.setNegativeButton("Close") { dialog, which ->
            finishAffinity()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
