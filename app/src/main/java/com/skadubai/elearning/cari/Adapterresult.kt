package com.skadubai.elearning.cari

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skadubai.elearning.R
import com.skadubai.elearning.detailkelas.AdapterDetailkls
import com.skadubai.elearning.model.ModelAct
import com.skadubai.elearning.model.ModelCari
import com.squareup.picasso.Picasso

class Adapterresult(
    val KelasMapel : ArrayList<ModelCari.DaftarKelas>,
    val listener : Adapterresult.OnAdapterlistener
) : RecyclerView.Adapter<Adapterresult.ViewHolder>() {


    class ViewHolder(view : View): RecyclerView.ViewHolder(view) {
        val gambar_kls = view.findViewById<ImageView>(R.id.gambar_kls)
        val Namamapel = view.findViewById<TextView>(R.id.namamapel)
        val Deskripsi = view.findViewById<TextView>(R.id.deskripsi)
        val Namaguru = view.findViewById<TextView>(R.id.nm_guru)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder (
        LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_adapterresult, parent, false)
    )

    override fun onBindViewHolder(holder: Adapterresult.ViewHolder, position: Int) {
        val kelasmapel = KelasMapel[position]

        holder.Namamapel.text = "${kelasmapel.nama_mapel} - ${kelasmapel.kelas}"
        holder.Deskripsi.text = Html.fromHtml(kelasmapel.keterangan)
        holder.Namaguru.text = kelasmapel.nama_guru

        val urlFoto = "https://elearning.smkn2barabai.sch.id/${kelasmapel.dwgambar}"
        Picasso.get().load(urlFoto).into(holder.gambar_kls)

        Log.d("Image", kelasmapel.dwgambar.toString())

        holder.itemView.setOnClickListener {
            listener.onClick(kelasmapel)
        }
    }

    override fun getItemCount()= KelasMapel.size

    public fun setData(data : List<ModelCari.DaftarKelas>){
        KelasMapel.clear()
        KelasMapel.addAll(data)
        notifyDataSetChanged()
    }
    interface OnAdapterlistener{
        fun onClick(detail : ModelCari.DaftarKelas)
    }


}
