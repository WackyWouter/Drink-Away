package com.example.drinkaway

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.drinkaway.database.DaresDBOpenHelper
import com.example.drinkaway.database.model.dare

class DareDetails : AppCompatActivity() {

    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dare_details)
        supportActionBar?.hide()

        val intent = intent
        id = intent.getSerializableExtra("details") as Int
        val dareTextView = findViewById<TextView>(R.id.dareText)
        val amountTextView = findViewById<TextView>(R.id.amount)
        val dbHandler = DaresDBOpenHelper(this, null)
        val dare = dbHandler.getSpecificDareById(id)
        dareTextView.text = dare.dareText
        amountTextView.text = dare.amount.toString()
    }

    fun back(view: View) {
        val intent = Intent(this, SetupActivity::class.java)
        startActivity(intent)
    }

    fun firestore(view: View) {
        val dbHandler = DaresDBOpenHelper(this, null)
        val oldDare = dbHandler.getSpecificDareById(id)
        val dareTextView = findViewById<TextView>(R.id.dareText)
        val amountTextView = findViewById<TextView>(R.id.amount)
        val dareText = dareTextView.text.toString()
        val amount = (amountTextView.text).toString()
        val drinkAmount = 1

        if (dareText.isNotEmpty() && dareText.isNotEmpty() && !(dareText == oldDare.dareText && amount == oldDare.amount.toString())) {
            val intent = Intent(this, SetupActivity::class.java)
            val newDare = dare(id, dareText, amount.toInt(), drinkAmount)

            dbHandler.updateDare(newDare)
            startActivity(intent)
        } else if (dareText.isEmpty()) {
            Toast.makeText(applicationContext, "No dare was entered! ", Toast.LENGTH_SHORT).show()
        } else if (amount.isEmpty()) {
            Toast.makeText(applicationContext, "No amount was entered! ", Toast.LENGTH_SHORT).show()
        } else if (dareText == oldDare.dareText && amount == oldDare.amount.toString()) {
            val intent = Intent(this, SetupActivity::class.java)

            startActivity(intent)
        }
    }

    fun confirmDelete(view: View) {
        val dbHandler = DaresDBOpenHelper(this, null)
        val dare = dbHandler.getSpecificDareById(id)

        val builder = AlertDialog.Builder(this@DareDetails)
        builder.setTitle("Confirm Delete")
        builder.setMessage("Are you sure you want to delete: ".plus(dare.dareText))
        builder.setPositiveButton("YES") { dialog, which ->
            delete()
        }
        builder.setNegativeButton("No") { dialog, which -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun delete() {
        val dbHandler = DaresDBOpenHelper(this, null)
        val intent = Intent(this, SetupActivity::class.java)

        dbHandler.DeleteDare(id)
        startActivity(intent)
    }
}



