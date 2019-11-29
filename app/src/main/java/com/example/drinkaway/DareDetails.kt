package com.example.drinkaway

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class DareDetails : AppCompatActivity() {

    var hashmapDare = HashMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dare_details)
        supportActionBar?.hide()

        val intent = intent
        hashmapDare = intent.getSerializableExtra("details") as HashMap<String, String>
        val dareTextView = findViewById<TextView>(R.id.dareText)
        val amountTextView = findViewById<TextView>(R.id.amount)
        dareTextView.text = hashmapDare["text"]
        amountTextView.text = hashmapDare["amount"]

    }


    fun back(view: View) {
        val intent = Intent(this, SetupActivity::class.java)
        startActivity(intent)
    }

    fun firestore(view: View) {
        val TAG = "update Firestore"
        val db = FirebaseFirestore.getInstance()
        val dareTextView = findViewById<TextView>(R.id.dareText)
        val amountTextView = findViewById<TextView>(R.id.amount)
        val text = dareTextView.text.toString()
        val amount = (amountTextView.text).toString()



        if (text.isNotEmpty() && amount.isNotEmpty() && !(text == hashmapDare["text"] && amount == hashmapDare["amount"])) {
            val dareDocument = db.collection("dares").document(hashmapDare["key"].toString())

            dareDocument
                .update(
                    "text", text.toString(),
                    "amount", amount.toInt()
                )
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully updated!")
                    Toast.makeText(
                        applicationContext,
                        "Added successfully!",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error updating document", e)
                    Toast.makeText(
                        applicationContext,
                        "Error: ".plus(e.toString()),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            val intent = Intent(this, SetupActivity::class.java)
            startActivity(intent)
        } else if (text.isEmpty()) {
            Toast.makeText(applicationContext, "No dare was entered! ", Toast.LENGTH_SHORT).show()
        } else if (amount.isEmpty()) {
            Toast.makeText(applicationContext, "No amount was entered! ", Toast.LENGTH_SHORT).show()
        } else if (text == hashmapDare["text"] && amount == hashmapDare["amount"]) {
            Toast.makeText(
                applicationContext,
                "You didn't change the amount or dare!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun confirmDelete(view: View) {

        val builder = AlertDialog.Builder(this@DareDetails)

        builder.setTitle("Confirm Delete")

        builder.setMessage("Are you sure you want to delete: ".plus(hashmapDare["text"]))

        builder.setPositiveButton("YES") { dialog, which ->
            delete()
        }
        builder.setNegativeButton("No") { dialog, which ->

        }


        val dialog: AlertDialog = builder.create()

        dialog.show()
    }

    fun delete() {
        val TAG = "Delete Firestore"
        val db = FirebaseFirestore.getInstance()
        db.collection("dares").document(hashmapDare["key"].toString())
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully deleted!")
                Toast.makeText(applicationContext, "Successfully deleted!.", Toast.LENGTH_SHORT)
                    .show()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting document", e)
                Toast.makeText(applicationContext, "Error: ".plus(e.toString()), Toast.LENGTH_SHORT)
                    .show()
            }

        val intent = Intent(this, SetupActivity::class.java)
        startActivity(intent)

    }
}



