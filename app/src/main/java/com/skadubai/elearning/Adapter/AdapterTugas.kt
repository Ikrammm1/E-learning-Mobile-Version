package com.skadubai.elearning.Adapter

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.skadubai.elearning.R
import com.skadubai.elearning.model.ModelTgs
import com.squareup.picasso.Picasso


class AdapterTugas(
    val Daftarkls: ArrayList<ModelTgs.Daftar_tgs>,
    val listener: OnAdapterlistener,
    val context: Context
) : RecyclerView.Adapter<AdapterTugas.ViewHolder>(){


    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        val txtnmkls = view.findViewById<TextView>(R.id.txtnmkls)
        val txtdeskrip = view.findViewById<TextView>(R.id.txtdesk)
        val txtwaktu = view.findViewById<TextView>(R.id.txtwaktu)
        val txttgl = view.findViewById<TextView>(R.id.txttgl)
        val gambar = view.findViewById<ImageView>(R.id.gambar_tgs)
        val verify = view.findViewById<ImageView>(R.id.verify)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View =  LayoutInflater.from(parent.context)
            .inflate(R.layout.daftar_tugas, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = Daftarkls[position]

        if (data.sisawaktu <= 0){
            holder.txtwaktu.setTextColor(Color.RED)
            holder.txttgl.setTextColor(Color.RED)
        }else{
            holder.txtwaktu.setTextColor(Color.parseColor("#EB047810"))
            holder.txttgl.setTextColor(Color.parseColor("#EB047810"))
        }

        holder.txtnmkls.text = data.nama_mapel
        holder.txtdeskrip.text = Html.fromHtml(data.deskripsi)
        holder.txtwaktu.text = data.waktu_akhir
        holder.txttgl.text = data.hari_akhir
        val image = data.gambar
//        val urlFoto = "http://192.168.100.151/$image"
        val urlFoto = "https://elearning.smkn2barabai.sch.id/$image"
        Picasso.get().load(urlFoto).into(holder.gambar)

        Log.d("Image", image.toString())

        if (data.id_jawaban == null){
            holder.verify.visibility = View.GONE
        }else{
            holder.verify.visibility = View.VISIBLE
        }


        holder.itemView.setOnClickListener {
            listener.onClick(data)
        }
    }



    override fun getItemCount() = Daftarkls.size

    public fun setdData(data : List<ModelTgs.Daftar_tgs>){
        Daftarkls.clear()
        Daftarkls.addAll(data)
        notifyDataSetChanged()
    }
    interface  OnAdapterlistener {
        fun onClick(detail: ModelTgs.Daftar_tgs)

    }
}