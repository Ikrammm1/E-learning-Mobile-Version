package com.skadubai.elearning.tugas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skadubai.elearning.R

class Adapatertugas(private val newList: ArrayList<NewList>) : RecyclerView.Adapter<Adapatertugas.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newList[position]

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {

    }


}
