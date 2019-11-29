package com.example.drinkaway


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.random.Random


class PlayActivity : AppCompatActivity() {

    var text = mutableListOf<String>()
    var amount = mutableListOf<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        supportActionBar?.hide()

        val reloaded = savedInstanceState?.get("reloaded")
        Log.d("reloadTest", reloaded.toString())

        if (reloaded != true) {
            val TAG = "Firestore"
            val db = FirebaseFirestore.getInstance()

            db.collection("dares")
                .get()
                .addOnSuccessListener { result ->
                    for ((index, document) in result.withIndex()) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        text.add(document.data["text"].toString())
                        amount.add((document.data["amount"].toString()).toInt())
                    }

                    randomDare(text, amount)
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                    Toast.makeText(this, "Error getting documents", Toast.LENGTH_SHORT).show()
                }
        }

    }


    fun randomDare(text: MutableList<String>, amount: MutableList<Int>) {
        val randomValues = Random.nextInt(0, text.size)
        val dareTextView = findViewById<TextView>(R.id.dareTextRandom)
        val nextBtn = findViewById<Button>(R.id.nextBtn)

        dareTextView.text = text[randomValues]

        amountCheck(randomValues)

        nextBtn.setOnClickListener {
            if (text.size != 0) {
                val randomValues = Random.nextInt(0, text.size)

                dareTextView.text = text[randomValues]
                amountCheck(randomValues)
            } else {


                val refreshBtn = findViewById<ImageButton>(R.id.refresh)
                refreshBtn.visibility = View.VISIBLE
                val endString = getString(R.string.end)
                dareTextView.text = endString

            }
        }
    }


    fun amountCheck(randomValues: Int) {

        if (amount[randomValues] > 1) {
            amount[randomValues] = amount[randomValues] - 1

        } else {

            amount.removeAt(randomValues)
            text.removeAt(randomValues)
        }
    }


    fun back(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("reloaded", true)
    }

    fun refresh(view: View) {
        finish()
        startActivity(Intent(this, PlayActivity::class.java))
        overridePendingTransition(0, 0)
    }
}


