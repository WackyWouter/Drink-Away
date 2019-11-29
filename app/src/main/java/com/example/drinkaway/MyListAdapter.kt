package com.example.drinkaway


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


class MyListAdapter(context: Context, text: MutableList<String>, amount: MutableList<String>) :
    BaseAdapter() {

    private val mContext: Context
    private val mText: MutableList<String>
    private val mAmount: MutableList<String>


    init {
        mContext = context
        mText = text
        mAmount = amount

    }

    //responsible for rendering out each row
    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val rowMain = layoutInflater.inflate(R.layout.dare_list, viewGroup, false)
        Log.d("test", "ListAdapter 31")

        val dareTextView = rowMain.findViewById<TextView>(R.id.dare)
        dareTextView.text = mText[position]

        return rowMain
    }

    //ignore this for now
    override fun getItem(position: Int): Any {

        return "Test string"
    }

    //ignore this for now
    override fun getItemId(position: Int): Long {

        return position.toLong()
    }

    //responsible for how many rows in my list
    override fun getCount(): Int {

        return mText.size
    }
}

