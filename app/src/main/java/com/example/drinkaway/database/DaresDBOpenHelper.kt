package com.example.drinkaway.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
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
            return ArrayList()
        }
        var dareText: String
        var amount: Int
        var drinkAmount: Int
        var id: Int
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                dareText = cursor.getString(cursor.getColumnIndex(COLUMN_DARE_TEXT))
                amount = cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT))
                drinkAmount = cursor.getInt(cursor.getColumnIndex(COLUMN_DRINK_AMOUNT))
                id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))

                dares.add(dare(id, dareText, amount, drinkAmount))
                cursor.moveToNext()
            }
        }
        return dares
    }


    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "dares.db"
        const val TABLE_NAME = "dares"
        const val COLUMN_ID = "_id"
        const val COLUMN_DARE_TEXT = "dareText"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_DRINK_AMOUNT = "drinkAmount"
    }

}