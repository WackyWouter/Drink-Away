package com.example.drinkaway

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.drinkaway.database.DaresDBOpenHelper
import com.example.drinkaway.database.model.dare
import com.google.firebase.firestore.FirebaseFirestore

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        supportActionBar?.hide()
    }


    fun firestore(view: View) {

        val TAG = "Firestore"
        val db = FirebaseFirestore.getInstance()

        var editText = findViewById<EditText>(R.id.dareText)

        var dareText = editText.text.toString()

        //check if the EditText have values or not
        if (dareText.isNotEmpty()) {

            val dbHandler = DaresDBOpenHelper(this, null)
            val newDare = dare(dareText, 1, 1)
            dbHandler.addDare(newDare)
            Toast.makeText(this, dareText + " Added to database", Toast.LENGTH_LONG).show()


            //old code for the firebase cloud storage
//            val dare = hashMapOf(
//                "text" to dareText,
//                "amount" to 1
//            )
            // Add a new document with a generated ID
//            db.collection("dares")
//                .add(dare)
//                .addOnSuccessListener { documentReference ->
//                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//                    Toast.makeText(
//                        applicationContext,
//                        "The dare was succesfully added.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                .addOnFailureListener { e ->
//                    Log.w(TAG, "Error adding document", e)
//                    Toast.makeText(
//                        applicationContext,
//                        "Error: ".plus(e.toString()),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }

            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)

        } else {
            Toast.makeText(applicationContext, "No dare was entered! ", Toast.LENGTH_SHORT).show()
        }


    }

    fun back(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}

