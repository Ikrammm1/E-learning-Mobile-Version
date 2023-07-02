package com.skadubai.elearning.quiz

import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skadubai.elearning.Adapter.AdapterTugas
import com.skadubai.elearning.R
import com.skadubai.elearning.model.ModelSoal
import com.skadubai.elearning.model.ModelTgs

class AdapterSoal(
    val DaftarSoal: ArrayList<ModelSoal.Daftar_soal>,
    val listener: OnAdapterlistener
) : RecyclerView.Adapter<AdapterSoal.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterSoal.ViewHolder {
        val view : View =  LayoutInflater.from(parent.context)
            .inflate(R.layout.soal_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterSoal.ViewHolder, position: Int) {
        val data = DaftarSoal[position]

        holder.Soal.text = Html.fromHtml(data.soal)
        holder.JwbA.text = "A. ${data.jwb_a}"
        holder.JwbB.text = "B. ${data.jwb_b}"
        holder.JwbC.text = "C. ${data.jwb_c}"
        holder.JwbD.text = "D. ${data.jwb_d}"
        holder.JwbE.text = "E. ${data.jwb_e}"
        holder.NmrSoal.text = "Soal No - ${(position+1)}"


        val radioGroup = listOf(holder.JwbA, holder.JwbB, holder.JwbC, holder.JwbD, holder.JwbE)
        val listJawaban = listOf("pilihan_a", "pilihan_b", "pilihan_c", "pilihan_d", "pilihan_e")

        var active = 0

        radioGroup.forEachIndexed { idx, radio ->
            radio.setOnClickListener {
                radioGroup.forEachIndexed { idxChild, radio ->
                    active = idx
                    listener.onSelected(data.id_soal, listJawaban.get(active))
                    if (active !== idxChild) {
                        radio.isChecked = false
                    }
                }
            }
        }

        Log.d("holder", holder.toString())

    }

    override fun getItemCount() = DaftarSoal.size


    class ViewHolder(view : View):RecyclerView.ViewHolder(view){
        val Soal = view.findViewById<TextView>(R.id.soal)
        val JwbA = view.findViewById<RadioButton>(R.id.jwbA)
        val JwbB = view.findViewById<RadioButton>(R.id.jwbB)
        val JwbC = view.findViewById<RadioButton>(R.id.jwbC)
        val JwbD = view.findViewById<RadioButton>(R.id.jwbD)
        val JwbE = view.findViewById<RadioButton>(R.id.jwbE)
        val RgJwb = view.findViewById<RadioGroup>(R.id.rgjwb)
        val NmrSoal = view.findViewById<TextView>(R.id.nosoal)

    }

    public fun setdData(data : List<ModelSoal.Daftar_soal>){
        DaftarSoal.clear()
        DaftarSoal.addAll(data)

        notifyDataSetChanged()
    }

    interface  OnAdapterlistener {
        fun onClick(detail: ModelSoal.Daftar_soal)
        fun onSelected(idSoal: String, jawaban: String) {}
    }
}