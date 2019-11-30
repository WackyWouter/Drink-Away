package com.example.drinkaway.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.drinkaway.database.model.dare

class DaresDBOpenHelper(
    context: Context,
    factory: SQLiteDatabase.CursorFactory?
) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_DARES_TABLE = ("CREATE TABLE " +
                TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DARE_TEXT + " TEXT, " +
                COLUMN_AMOUNT + " INT, " +
                COLUMN_DRINK_AMOUNT + " INT" + ")")
        db.execSQL(CREATE_DARES_TABLE)

        initialDares(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addDare(dare: dare) {
        val values = ContentValues()
        values.put(COLUMN_DARE_TEXT, dare.dareText)
        values.put(COLUMN_AMOUNT, dare.amount)
        values.put(COLUMN_DRINK_AMOUNT, dare.drinkAmount)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllDares(): ArrayList<dare> {
        val db = this.readableDatabase
        val dares = ArrayList<dare>()
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        } catch (e: SQLiteException) {
            Log.d("SQLiteExpection", "SQLite error: ".plus(e.toString()))
            return ArrayList()
        }
        var dareText: String
        var amount: Int
        var drinkAmount: Int
        var id: Int
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                dareText = cursor.getString(cursor.getColumnIndex(COLUMN_DARE_TEXT))
                amount = cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT))
                drinkAmount = cursor.getInt(cursor.getColumnIndex(COLUMN_DRINK_AMOUNT))
                dares.add(dare(id, dareText, amount, drinkAmount))
                cursor.moveToNext()
            }
        }
        cursor.close()
        db.close()
        return dares
    }

    fun getSpecificDareById(id: Int): dare {
        val db = this.readableDatabase
        var dare = dare(0, "", 0, 0)
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID =  $id", null)
        } catch (e: SQLiteException) {
            Log.d("SQLiteExpection", "SQLite error: ".plus(e.toString()))
            return dare("Database Error", 0, 0)
        }
        if (cursor!!.moveToFirst()) {
            dare = dare(
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DARE_TEXT)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_DRINK_AMOUNT))
            )
        }
        cursor.close()
        db.close()
        return dare
    }

    fun updateDare(dare: dare) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(COLUMN_DARE_TEXT, dare.dareText)
        values.put(COLUMN_AMOUNT, dare.amount)
        values.put(COLUMN_DRINK_AMOUNT, dare.drinkAmount)
        db.update(TABLE_NAME, values, "$COLUMN_ID = ${dare.id}", null)
        db.close()

    }

    fun DeleteDare(id: Int) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = $id", null)
        db.close()
    }

    private fun initialDares(db: SQLiteDatabase) {
//        val db = this.writableDatabase
        val values = ContentValues()
        val amount = 1
        val drinkAmount = 1
        val dareTexts: Array<String> = arrayOf(
            "Put your shoe on your head for 1 round",
            "The person across from you gets to send a text from your phone",
            "Give your most sassy mean girl reaction",
            "Do a shot with someone you don't know",
            "Ask for a random drink",
            "Cut of a piece of your hair",
            "Run in a circle around the terraces",
            "Choose a person to be your drinking buddy",
            "You are not allowed to smoke for a hour",
            "Exchange shoes with the person across from you",
            "Write you name with your ass",
            "Category: objects that fit in your anus",
            "Give your glasses to someone you don't know",
            "Give away three sips to a person of your choosing",
            "Put your t-shirt or hoodie on inside out",
            "Chug your glass",
            "Time for the bus",
            "Do a dance on your chair for us",
            "The person on your left gets to draw on your face with a pen",
            "Let the person on your left do your hair",
            "Convince someone you don't know to take of their shoes",
            "Take a shot with a stranger",
            "Do a freestyle rap",
            "Smoke three cigarettes at the same time",
            "Cut of a piece of your eyelash",
            "You have to pick two people to be pee buddies. When one of the pee buddies has to go, the other one goes with him/her",
            "The person on your right orders a drink for you and you have to drink it",
            "Walk up to someone and act like he/she is your long lost childhood friend",
            "Lick the person to your right",
            "For 1 round everyone has to say the last word of their sentence twice",
            "Quiz master. If anybody answers your questions they have to drink",
            "Forbidden word. You can choose one word. When anybody says that word they have to drink",
            "Snake eyes. When anybody looks you in the eyes. They have to drink"
        )


        values.put(COLUMN_AMOUNT, amount)
        values.put(COLUMN_DRINK_AMOUNT, drinkAmount)

        dareTexts.forEach {
            values.put(COLUMN_DARE_TEXT, it)
            db.insert(TABLE_NAME, null, values)
        }
    }

    companion object {
        private const val DATABASE_VERSION = 3
        private const val DATABASE_NAME = "dares.db"
        const val TABLE_NAME = "dares"
        const val COLUMN_ID = "_id"
        const val COLUMN_DARE_TEXT = "dareText"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_DRINK_AMOUNT = "drinkAmount"
    }


}