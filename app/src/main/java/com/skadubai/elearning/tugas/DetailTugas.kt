package com.skadubai.elearning.tugas

import android.app.DownloadManager
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.skadubai.elearning.API.RetrofitClient
import com.skadubai.elearning.Adapter.AdapterTugas
import com.skadubai.elearning.Editjwb
import com.skadubai.elearning.Jwbtugas
import com.skadubai.elearning.R
import com.skadubai.elearning.databinding.ActivityDetailTugasBinding
import com.skadubai.elearning.databinding.ActivityMainBinding
import com.skadubai.elearning.fragments.BerandaFragment
import com.skadubai.elearning.fragments.TugasFragment
import com.skadubai.elearning.helper.SharedPrefManager
import com.skadubai.elearning.model.ModelJwb
import com.skadubai.elearning.model.ModelTgs
import kotlinx.android.synthetic.main.activity_quiz.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailTugas : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTugasBinding
    private val api by lazy { RetrofitClient.instance}
    private val Id_tugas by lazy { intent.getStringExtra("id_tugas") }
    private val File by lazy { intent.getStringExtra("file_tugas") }
    private val Namafile by lazy { intent.getStringExtra("nama_file") }
    private val Judul by lazy { intent.getStringExtra("judul_tugas") }
    private val kelas by lazy { intent.getStringExtra("kelas") }
    private val Deskripsi by lazy { intent.getStringExtra("deskripsi") }
    private val nama_mapel by lazy { intent.getStringExtra("nama_mapel") }
    private val NISN by lazy { intent.getStringExtra("NISN") }
    private val Mulai by lazy { intent.getStringExtra("mulai") }
    private val Waktu_Mulai by lazy { intent.getStringExtra("waktu_mulai") }
    private val Hari_akhir by lazy { intent.getStringExtra("hari_akhir") }
    private val WaktuAkhir by lazy { intent.getStringExtra("waktu_akhir") }
    private val id_jawaban by lazy { intent.getStringExtra("id_jawaban") }
    private val NamaPengirim by lazy { intent.getStringExtra("nama_pengirim") }
    private val Jwbtext by lazy { intent.getStringExtra("jwb_text") }
    private val Namajwb by lazy { intent.getStringExtra("file") }
    private val Submited by lazy { intent.getStringExtra("submited") }
    private val Update by lazy { intent.getStringExtra("update") }
    private val FileJwb by lazy { intent.getStringExtra("filejwb") }
    private val Sisawaktu by lazy { intent.getIntExtra("sisawaktu",0) }
    private val Countwaktu by lazy { intent.getStringExtra("countwaktu") }

    private lateinit var RefreshJwb : SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTugasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        RefreshJwb = binding.refreshjwb

        binding.btnback.setOnClickListener {
            this.finish()
        }

        api.DaftarJawban(NISN, Id_tugas).enqueue(object : Callback<ModelJwb>{
            override fun onResponse(call: Call<ModelJwb>, response: Response<ModelJwb>) {
                if (response.isSuccessful){
                    if (Namafile == ""){
                        binding.lineFile.visibility = View.GONE
                    }
                    if (Sisawaktu <= 0){
                        binding.btnjwb.visibility = View.GONE
                        binding.btnEdit.visibility = View.GONE
                    }else {

                        if (response.body()!!.id_jawaban == null) {

                            binding.btnjwb.visibility = View.VISIBLE
                            binding.btnEdit.visibility = View.GONE

                        } else {
                            binding.btnjwb.visibility = View.GONE
                            binding.btnEdit.visibility = View.VISIBLE


                        }

                    }
                    binding.namapengirim.setText(response.body()!!.nama_pengirim)
                    binding.filejwbtgs.setText(response.body()!!.file)

                    if (response.body()!!.update == null) {
                        binding.datejwbtgs.setText(response.body()!!.submited)
                    } else {
                        binding.datejwbtgs.setText(response.body()!!.update)
                    }

                    binding.filejwbtgs.setOnClickListener {
                        val url = "https://elearning.smkn2barabai.sch.id/${response.body()!!.filejwb}"
                        Log.d("url", url)
                        val request = DownloadManager.Request(Uri.parse(url))
                        val title = url.substringAfterLast("/")
                        Log.d("title", title)
                        request.setTitle(title)
                        request.setDescription("Downloading File please wait...")
                        val cookie = CookieManager.getInstance().getCookie(url)
                        request.addRequestHeader("cookie", cookie)
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        request.setDestinationInExternalPublicDir(
                            Environment.DIRECTORY_DOWNLOADS,
                            title
                        )

                        val download = this@DetailTugas.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                        download.enqueue(request)
                        Toast.makeText( this@DetailTugas,"Downloadin Started", Toast.LENGTH_SHORT).show()
                    }

                }
            }

            override fun onFailure(call: Call<ModelJwb>, t: Throwable) {
                Log.d("Kesalahan : ", t.toString())
            }

        })

        binding.btnjwb.setOnClickListener {
            val keJwbtgs = Intent(this@DetailTugas, Jwbtugas::class.java)
                .putExtra("id_tugas", Id_tugas)
                .putExtra("nama_mapel", nama_mapel)
                .putExtra("kelas", kelas)
                .putExtra("judul_tugas", Judul)
                .putExtra("deskripsi", Deskripsi)
                .putExtra("mulai", Mulai)
                .putExtra("waktu_mulai", Waktu_Mulai)
                .putExtra("hari_akhir", Hari_akhir)
                .putExtra("waktu_akhir", WaktuAkhir)
                .putExtra("nama_file", Namafile)
                .putExtra("file_tugas", File)
                .putExtra("id_jawaban", id_jawaban)
                .putExtra("nama_pengirim", NamaPengirim)
                .putExtra("jwb_text", Jwbtext)
                .putExtra("file", Namajwb)
                .putExtra("submited", Submited)
                .putExtra("update", Update)
                .putExtra("filejwb", FileJwb)
                .putExtra("NISN", NISN)
                .putExtra("sisawaktu", Sisawaktu)
                .putExtra("countwaktu", Countwaktu)
            startActivity(keJwbtgs)
        }
        binding.btnEdit.setOnClickListener {
            val keEdit = Intent(this@DetailTugas, Editjwb::class.java)
                .putExtra("id_tugas", Id_tugas)
                .putExtra("nama_mapel", nama_mapel)
                .putExtra("kelas", kelas)
                .putExtra("judul_tugas", Judul)
                .putExtra("deskripsi", Deskripsi)
                .putExtra("mulai", Mulai)
                .putExtra("waktu_mulai", Waktu_Mulai)
                .putExtra("hari_akhir", Hari_akhir)
                .putExtra("waktu_akhir", WaktuAkhir)
                .putExtra("nama_file", Namafile)
                .putExtra("file_tugas", File)
                .putExtra("id_jawaban", id_jawaban)
                .putExtra("nama_pengirim", NamaPengirim)
                .putExtra("jwb_text", Jwbtext)
                .putExtra("file", Namajwb)
                .putExtra("submited", Submited)
                .putExtra("update", Update)
                .putExtra("filejwb", FileJwb)
                .putExtra("NISN", NISN)
                .putExtra("sisawaktu", Sisawaktu)
                .putExtra("countwaktu", Countwaktu)
            startActivity(keEdit)
        }

        binding.namamapel.setText(nama_mapel)
        binding.kelas.setText(kelas)
        binding.jdltugas.setText(Judul)
        binding.filetugas.setText(Namafile)
        binding.destugas.setText(Html.fromHtml(Deskripsi))
        binding.batashari.setText(Hari_akhir)
        binding.bataswaktu.setText(WaktuAkhir)
        binding.deadline.setText(Countwaktu)
        if (Sisawaktu <= 0){
            binding.deadline.setTextColor(Color.RED)
            binding.btnjwb.visibility = View.GONE
            binding.btnEdit.visibility = View.GONE
        }else{
            binding.deadline.setTextColor(Color.parseColor("#EB047810"))
        }

        binding.filetugas.setOnClickListener {
            val url ="https://elearning.smkn2barabai.sch.id/${File}"
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

            val download = this.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            download.enqueue(request)
            Toast.makeText(this, "Downloadin Started", Toast.LENGTH_SHORT).show()
        }


        RefreshJwb.setOnRefreshListener {
            Toast.makeText(this.applicationContext,"Memperbahrui Data", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                RefreshJwb.isRefreshing = false

                binding.namamapel.setText(nama_mapel)
                binding.kelas.setText(kelas)
                binding.jdltugas.setText(Judul)
                binding.filetugas.setText(Namafile)
                binding.destugas.setText(Html.fromHtml(Deskripsi))
                binding.batashari.setText(Hari_akhir)
                binding.bataswaktu.setText(WaktuAkhir)
                if (Sisawaktu <= 0){
                    binding.deadline.setTextColor(Color.RED)
                    binding.btnjwb.visibility = View.GONE
                    binding.btnEdit.visibility = View.GONE

                }else{
                    binding.deadline.setTextColor(Color.parseColor("#EB047810"))
                }

                binding.filetugas.setOnClickListener {
                    val url ="https://elearning.smkn2barabai.sch.id/${File}"
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

                    val download = this.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                    download.enqueue(request)
                    Toast.makeText(this, "Downloadin Started", Toast.LENGTH_SHORT).show()
                }

                api.DaftarJawban(NISN, Id_tugas).enqueue(object : Callback<ModelJwb>{
                    override fun onResponse(call: Call<ModelJwb>, response: Response<ModelJwb>) {
                        if (response.isSuccessful){
                            if (Namafile == ""){
                                binding.lineFile.visibility = View.GONE
                            }
                            if (Sisawaktu <= 0){
                                binding.btnjwb.visibility = View.GONE
                                binding.btnEdit.visibility = View.GONE
                            }else {

                                if (response.body()!!.id_jawaban == null) {

                                    binding.btnjwb.visibility = View.VISIBLE
                                    binding.btnEdit.visibility = View.GONE

                                } else {
                                    binding.btnjwb.visibility = View.GONE
                                    binding.btnEdit.visibility = View.VISIBLE


                                }

                            }
                            binding.namapengirim.setText(response.body()!!.nama_pengirim)
                            binding.filejwbtgs.setText(response.body()!!.file)

                            if (response.body()!!.update == null) {
                                binding.datejwbtgs.setText(response.body()!!.submited)
                            } else {
                                binding.datejwbtgs.setText(response.body()!!.update)
                            }

                            binding.filejwbtgs.setOnClickListener {
                                val url = "https://elearning.smkn2barabai.sch.id/${response.body()!!.filejwb}"
                                Log.d("url", url)
                                val request = DownloadManager.Request(Uri.parse(url))
                                val title = url.substringAfterLast("/")
                                Log.d("title", title)
                                request.setTitle(title)
                                request.setDescription("Downloading File please wait...")
                                val cookie = CookieManager.getInstance().getCookie(url)
                                request.addRequestHeader("cookie", cookie)
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                                request.setDestinationInExternalPublicDir(
                                    Environment.DIRECTORY_DOWNLOADS,
                                    title
                                )

                                val download = this@DetailTugas.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                                download.enqueue(request)
                                Toast.makeText( this@DetailTugas,"Downloadin Started", Toast.LENGTH_SHORT).show()
                            }

                        }
                    }

                    override fun onFailure(call: Call<ModelJwb>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })



                Toast.makeText(
                    this.applicationContext,
                    "Memperbahrui Data Selesai",
                    Toast.LENGTH_SHORT
                ).show()


            },3000L)
        }


    }
}
