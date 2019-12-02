package com.example.drinkaway


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.drinkaway.database.model.dare


class MyListAdapter(context: Context, dares: List<dare>) :
    BaseAdapter() {

    private val mContext: Context
    private val mDares: List<dare>



    init {
        mContext = context
        mDares = dares
    }

    //responsible for rendering out each row
    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val rowMain = layoutInflater.inflate(R.layout.dare_list, viewGroup, false)
        val dareTextView = rowMain.findViewById<TextView>(R.id.dare)
        dareTextView.text = mDares[position].dareText
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
        return mDares.size
    }
}

