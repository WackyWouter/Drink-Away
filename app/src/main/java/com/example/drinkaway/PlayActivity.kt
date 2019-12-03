package com.example.drinkaway


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.drinkaway.database.DaresDBOpenHelper
import com.example.drinkaway.database.model.dare
import kotlin.random.Random

class PlayActivity : AppCompatActivity() {

    lateinit var dares: ArrayList<dare>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        supportActionBar?.hide()

        val reloaded = savedInstanceState?.get("reloaded")
        val dbHandler = DaresDBOpenHelper(this, null)
        dares = dbHandler.getAllDares()
        randomDare()
    }


    fun randomDare() {
        var randomValues = Random.nextInt(0, dares.size)
        val dareTextView = findViewById<TextView>(R.id.dareTextRandom)
        val nextBtn = findViewById<Button>(R.id.nextBtn)
        if (dares[randomValues].drinkBool == 1) {
            dareTextView.text =
                (dares[randomValues].dareText)!!.plus(" Sips: ${dares[randomValues].drinkAmount}")
        } else {
            dareTextView.text = (dares[randomValues].dareText)
        }
        amountCheck(randomValues)

        nextBtn.setOnClickListener {
            if (dares.size != 0) {
                randomValues = Random.nextInt(0, dares.size)
                if (dares[randomValues].drinkBool == 1) {
                    dareTextView.text =
                        (dares[randomValues].dareText)!!.plus(" Sips: ${dares[randomValues].drinkAmount}")
                } else {
                    dareTextView.text = (dares[randomValues].dareText)
                }
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
        if (dares[randomValues].amount > 1) {
            dares[randomValues].amount = dares[randomValues].amount - 1
        } else {
            dares.removeAt(randomValues)
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


