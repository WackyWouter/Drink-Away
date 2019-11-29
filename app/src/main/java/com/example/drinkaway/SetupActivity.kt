package com.example.drinkaway

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore


class SetupActivity : AppCompatActivity() {

    var text = mutableListOf<String>()
    var amount = mutableListOf<String>()
    var key = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)
        supportActionBar?.hide()

        val TAG = "Firestore"
        val db = FirebaseFirestore.getInstance()


        db.collection("dares")
            .get()
            .addOnSuccessListener { result ->
                for ((index, document) in result.withIndex()) {
                    Log.d(TAG, "${document.id} => ${document.data}")

                    key.add(document.id)

                    text.add(document.data["text"].toString())

                    amount.add(document.data["amount"].toString())

                }

                createList()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
                Toast.makeText(this, "Error getting documents", Toast.LENGTH_SHORT).show()
            }

    }

    fun createList() {

        val listView = findViewById<ListView>(R.id.dareList)
        listView.adapter = MyListAdapter(this, text, amount)

        listView.setOnItemClickListener { parent, view, position, id ->
            Log.d("test", "setupActivity 48")
            val element = hashMapOf(
                "text" to text[position],
                "amount" to amount[position],
                "key" to key[position]
            )
            val intent = Intent(this, DareDetails::class.java)
            intent.putExtra("details", element)
            startActivity(intent)
        }
    }

    fun back(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}
