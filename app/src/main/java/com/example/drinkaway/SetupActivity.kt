package com.example.drinkaway

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.drinkaway.database.DaresDBOpenHelper
import com.example.drinkaway.database.model.dare


class SetupActivity : AppCompatActivity() {

    var dares = arrayListOf<dare>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)
        supportActionBar?.hide()

        val dbHandler = DaresDBOpenHelper(this, null)
        dares = dbHandler.getAllDares()

        createList()

//        val TAG = "Firestore"
//        val db = FirebaseFirestore.getInstance()
//        db.collection("dares")
//            .get()
//            .addOnSuccessListener { result ->
//                for ((index, document) in result.withIndex()) {
//                    Log.d(TAG, "${document.id} => ${document.data}")
//
//                    key.add(document.id)
//
//                    text.add(document.data["text"].toString())
//
//                    amount.add(document.data["amount"].toString())
//
//                }
//
//                createList()
//            }
//            .addOnFailureListener { exception ->
//                Log.w(TAG, "Error getting documents.", exception)
//                Toast.makeText(this, "Error getting documents", Toast.LENGTH_SHORT).show()
//            }

    }

    fun createList() {
        Log.d("test", "setupActivity 59")
        val listView = findViewById<ListView>(R.id.dareList)
        dares.forEach {
            Log.d("sqlLite test dares", it.dareText.toString())
        }
        listView.adapter = MyListAdapter(this, dares)
        Log.d("test", "setupActivity 62")

        listView.setOnItemClickListener { parent, view, position, id ->
            Log.d("test", "setupActivity 64")
            val specificDare = arrayOf(dares[position])
            val intent = Intent(this, DareDetails::class.java)
            intent.putExtra("details", dares[position].id)
            startActivity(intent)
        }
    }

    fun back(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}
