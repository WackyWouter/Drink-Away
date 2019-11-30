package com.example.drinkaway

import android.content.Intent
import android.os.Bundle
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
    }

    fun createList() {
        //TODO fix sorting array alphabeticly
        val daresSorted = dares.sortedBy { it.dareText } as ArrayList<dare>
        val listView = findViewById<ListView>(R.id.dareList)
        listView.adapter = MyListAdapter(this, daresSorted)

        listView.setOnItemClickListener { parent, view, position, id ->
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
