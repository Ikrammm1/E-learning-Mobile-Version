package com.skadubai.elearning.detailkelas


import android.app.DownloadManager
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.skadubai.elearning.API.RetrofitClient
import com.skadubai.elearning.R
import com.skadubai.elearning.model.ModelAct

class AdapterDetailkls(
    val Aktifitas : ArrayList<ModelAct.Daftar_act>,
    val listener : OnAdapterlistener,
    val ctx : Context
) : RecyclerView.Adapter<AdapterDetailkls.ViewHolder>(){

    private val api by lazy { RetrofitClient.instance}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_adapter_detailkls, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val aktifitas = Aktifitas[position]

        when{
            aktifitas.deskripsi==""->{
                holder.desk.visibility = View.GONE
            }
        }

        holder.topik.text = aktifitas.topik
        holder.desk.text = Html.fromHtml(aktifitas.deskripsi)
        holder.namamateri.text = aktifitas.judul
        holder.judulquiz.text = aktifitas.nama_quiz
        holder.deadline.text = "${aktifitas.selesai}  ${aktifitas.waktu_selesai}"
        holder.sisawaktu.text = aktifitas.countwaktuquiz

        if (aktifitas.sisawaktuquiz <=0 ){
            holder.sisawaktu.setTextColor(Color.RED)

        }else{

            holder.sisawaktu.setTextColor(Color.parseColor("#EB047810"))
        }
        if (aktifitas.status_quiz == "nonaktif"){
            holder.contenQuiz.visibility = View.GONE
        }else{
            holder.contenQuiz.visibility = View.VISIBLE
        }

        when {
            aktifitas.id_materi == null ->{
                holder.contenMateri.visibility = View.GONE
            }
        }

        when {
            aktifitas.id_quiz == null ->{
                holder.contenQuiz.visibility = View.GONE
            }
        }

        holder.namamateri.setOnClickListener {
            val url ="https://elearning.smkn2barabai.sch.id/${aktifitas.dwmateri}"
            Log.d("url", url)
            val request = DownloadManager.Request(Uri.parse(url))
            val title = url.substringAfterLast("/")
            Log.d("title", title)
            request.setTitle(title)
            request.setDescription("Downloading File please wait...")
            val cookie = CookieManager.getInstance().getCookie(url)
            request.addRequestHeader("cookie",cookie)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title)

            val download = ctx.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            download.enqueue(request)
            Toast.makeText(ctx, "Downloadin Started", Toast.LENGTH_SHORT).show()
        }

//        holder.itemView.setOnClickListener {
//            listener.onClick(aktifitas)
//        }

        holder.btndetailquiz.setOnClickListener {
            listener.onClick(aktifitas)
        }


    }

    override fun getItemCount() = Aktifitas.size

    class ViewHolder(view : View): RecyclerView.ViewHolder(view) {
        val topik = view.findViewById<TextView>(R.id.topik)
        val desk = view.findViewById<TextView>(R.id.deskripsi)
        val namamateri = view.findViewById<TextView>(R.id.txtMateri)
        val contenMateri = view.findViewById<LinearLayout>(R.id.colMateri)
        val contenQuiz = view.findViewById<LinearLayout>(R.id.colquiz)
        val judulquiz = view.findViewById<TextView>(R.id.txtQuiz)
        val deadline = view.findViewById<TextView>(R.id.txtdeadline)
        val sisawaktu = view.findViewById<TextView>(R.id.txtsisawaktu)
        val btndetailquiz = view.findViewById<LinearLayout>(R.id.btndetailquiz)
//        val listMateri: RecyclerView = view.findViewById<RecyclerView>(R.id.listmateri)

    }

    public fun setData(data : List<ModelAct.Daftar_act>){
        Aktifitas.clear()
        Aktifitas.addAll(data)
        notifyDataSetChanged()
    }
    interface OnAdapterlistener{
        fun onClick(aktifitas : ModelAct.Daftar_act)
    }

}
