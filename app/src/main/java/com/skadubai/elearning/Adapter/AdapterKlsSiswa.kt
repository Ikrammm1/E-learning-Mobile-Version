package com.skadubai.elearning.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skadubai.elearning.R
import com.skadubai.elearning.model.ModelKlsSiswa
import com.squareup.picasso.Picasso

class AdapterKlsSiswa(
    val kelasSiswa : ArrayList<ModelKlsSiswa.KelasSiswa>,
    val listener : OnAdapterlistener
) : RecyclerView.Adapter<AdapterKlsSiswa.ViewHolder>(){

    class ViewHolder (view : View) : RecyclerView.ViewHolder(view){
        val txtnamakelas = view.findViewById<TextView>(R.id.txtnamakls)
        val txtnamaguru = view.findViewById<TextView>(R.id.txtnamaguru)
        val  image = view.findViewById<ImageView>(R.id.imgkls)
    }

    interface  OnAdapterlistener {
        fun onClick(detail: ModelKlsSiswa.KelasSiswa)
        fun onDelete(detail: ModelKlsSiswa.KelasSiswa)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View =  LayoutInflater.from(parent.context)
            .inflate(R.layout.kls_siswa, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = kelasSiswa[position]

        holder.txtnamakelas.text = data.nama_mapel
        holder.txtnamaguru.text = data.nama_guru
        val image = data.gambar_kls
        val urlFoto = "https://elearning.smkn2barabai.sch.id/$image"
//        val urlFoto = "http://192.168.100.67/$image"
        Picasso
            .get()
            .load(urlFoto)
            .into(holder.image)

        Log.d("Image", image.toString())

        holder.itemView.setOnClickListener {
            listener.onClick(data)
        }
    }

    override fun getItemCount() = kelasSiswa.size



    public fun setdData(data : List<ModelKlsSiswa.KelasSiswa>){
        kelasSiswa.clear()
        kelasSiswa.addAll(data)
        notifyDataSetChanged()
    }
}