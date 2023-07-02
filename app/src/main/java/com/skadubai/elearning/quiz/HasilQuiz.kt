package com.skadubai.elearning.quiz

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.skadubai.elearning.API.RetrofitClient
import com.skadubai.elearning.R
import com.skadubai.elearning.model.ModelHasil
import kotlinx.android.synthetic.main.activity_hasil_quiz.*
import kotlinx.android.synthetic.main.activity_quiz.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HasilQuiz : AppCompatActivity() {

    private val id_quiz by lazy { intent.getStringExtra("id_quiz") }
    private val NISN by lazy { intent.getStringExtra("NISN") }
    private val id_kelas_mapel by lazy { intent.getStringExtra("id_kelas_mapel") }
    private val id_act_kelas by lazy { intent.getStringExtra("id_act_kelas") }
    private val namaquiz by lazy { intent.getStringExtra("nama_quiz") }
    private val nama_mapel by lazy { intent.getStringExtra("mapel") }
    private val kelas by lazy { intent.getStringExtra("kelas") }
    private val durasiquiz by lazy { intent.getIntExtra("durasi_quiz",0) }
    private val KKM by lazy { intent.getIntExtra("KKM", 0) }
    private val mulai by lazy { intent.getStringExtra("mulai") }
    private val waktumulai by lazy { intent.getStringExtra("waktu_mulai") }
    private val selesai by lazy { intent.getStringExtra("selesai") }
    private val waktuselesai by lazy { intent.getStringExtra("waktu_selesai") }
    private val tglmodify by lazy { intent.getStringExtra("tgl_modify") }
    private val waktumodify by lazy { intent.getStringExtra("waktu_modify") }
    private val sisawaktu by lazy { intent.getIntExtra("sisawaktuquiz", 0) }
    private val countwaktuquiz by lazy { intent.getStringExtra("countwaktuquiz") }
    private val StatusQuiz by lazy { intent.getStringExtra("status_quiz") }

    private val Gambar by lazy { intent.getStringExtra("gambar_kls") }
    private val nama_guru by lazy { intent.getStringExtra("guru") }
    private val keterangan by lazy { intent.getStringExtra("keterangan") }
    private val id_kelas_siswa by lazy { intent.getStringExtra("id_kelas_siswa") }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hasil_quiz)

        val namamapel = findViewById<TextView>(R.id.namamapel)
        val Judulquiz = findViewById<TextView>(R.id.namaquiz)
        val JmlSoal = findViewById<TextView>(R.id.jmlsoal)
        val JwbBenar = findViewById<TextView>(R.id.benar)
        val JwbSalah = findViewById<TextView>(R.id.salah)
        val Nilai = findViewById<TextView>(R.id.nilai)
        val kkm = findViewById<TextView>(R.id.kkm)
        val Keterangan = findViewById<TextView>(R.id.keterangan)
        val btnback = findViewById<ImageView>(R.id.btnback)

        RetrofitClient.instance.HasilQuiz(id_quiz, NISN).enqueue(object : Callback<ModelHasil>{
            override fun onResponse(call: Call<ModelHasil>, response: Response<ModelHasil>) {
                if (response.isSuccessful){
                    val nilai = response.body()!!.nilai
                    val benar = response.body()!!.total_benar
                    val salah = response.body()!!.total_salah
                    JwbBenar.text = benar.toString()
                    JwbSalah.text =  salah.toString()
                    Nilai.text = nilai.toString()

                    if (nilai.toInt() < KKM.toInt()){
                        Nilai.setTextColor(Color.RED)
                        Keterangan.text = "Yaahh.. sedikit lagi.. Belajar lagi ya"
                        Keterangan.setTextColor(Color.RED)

                    }else{
                        Nilai.setTextColor(Color.parseColor("#EB047810"))
                        Keterangan.text = "Yee... Selamat Anda Lulus"
                        Keterangan.setTextColor(Color.parseColor("#EB047810"))
                    }


                }
            }

            override fun onFailure(call: Call<ModelHasil>, t: Throwable) {
                Log.d("Kesalahan Api ", t.toString())
            }

        })

        namamapel.text = "${nama_mapel} - ${kelas}"
        Judulquiz.text = namaquiz
        JmlSoal.text = "${mulai} ${waktumulai}"

        kkm.text = KKM.toString()

        btnback.setOnClickListener {
            val kembali = Intent(this@HasilQuiz, DetailQuiz::class.java)
                .putExtra("NISN", NISN)
                .putExtra("id_act_kelas", id_act_kelas)
                .putExtra("mapel", nama_mapel)
                .putExtra("kelas", kelas)
                .putExtra("mulai", mulai)
                .putExtra("waktu_mulai", waktumulai)
                .putExtra("tgl_modify", tglmodify)
                .putExtra("selesai", selesai)
                .putExtra("waktu_selesai", waktuselesai)
                .putExtra("durasi_quiz", durasiquiz)
                .putExtra("sisawaktuquiz", sisawaktu)
                .putExtra("countwaktuquiz", countwaktuquiz)
                .putExtra("id_kelas_mapel", id_kelas_mapel)
                .putExtra("id_quiz", id_quiz)
                .putExtra("nama_quiz", namaquiz)
                .putExtra("KKM", KKM)
                .putExtra("status_quiz", StatusQuiz)

                .putExtra("gambar_kls", Gambar)
                .putExtra("guru", nama_guru)
                .putExtra("keterangan", keterangan)
                .putExtra("id_kelas_siswa", id_kelas_siswa)
            startActivity(kembali)
        }








    }
}
