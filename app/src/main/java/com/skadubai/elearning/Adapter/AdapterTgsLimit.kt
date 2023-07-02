package com.skadubai.elearning.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skadubai.elearning.R
import com.skadubai.elearning.model.ModelKlsSiswa
import com.skadubai.elearning.model.ModelTgs

class AdapterTgsLimit(
    val daftarTugas : ArrayList<ModelTgs.Daftar_tgs>,
    val listener : OnAdapterlistener
) : RecyclerView.Adapter<AdapterTgsLimit.ViewHolder>() {

    class ViewHolder (view : View) : RecyclerView.ViewHolder(view){
        val txtjudul = view.findViewById<TextView>(R.id.jdltgs)
        val txtbatashari = view.findViewById<TextView>(R.id.hari)
        val  txtwaktu = view.findViewById<TextView>(R.id.waktu)
        val hubawal = view.findViewById<ImageView>(R.id.hubawal)
        val hubakhir = view.findViewById<ImageView>(R.id.hubakhir)
    }

    interface  OnAdapterlistener {
        fun onClick(detail: ModelTgs.Daftar_tgs)
        fun onDelete(detail: ModelTgs.Daftar_tgs)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View =  LayoutInflater.from(parent.context)
            .inflate(R.layout.tgs_limit, parent, false)
        return AdapterTgsLimit.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = daftarTugas[position]

        holder.txtjudul.text = data.judul_tugas
        holder.txtbatashari.text = data.hari_akhir
        holder.txtwaktu.text = data.waktu_akhir
        val jmltgs = daftarTugas.size
        if (jmltgs > 1){
            holder.hubawal.visibility = View.GONE
        }
        holder.itemView.setOnClickListener {
            listener.onClick(data)
        }

    }

    override fun getItemCount() = daftarTugas.size

    public fun setdData(data : List<ModelTgs.Daftar_tgs>){
            daftarTugas.clear()
            daftarTugas.addAll(data)
            notifyDataSetChanged()
    }
}