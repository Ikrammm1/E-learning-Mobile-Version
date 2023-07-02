package com.skadubai.elearning.quiz

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.skadubai.elearning.API.RetrofitClient
import com.skadubai.elearning.R
import com.skadubai.elearning.detailkelas.Detailkelas
import com.skadubai.elearning.model.ModelHasil
import com.skadubai.elearning.model.ModelSoal
import com.skadubai.elearning.tugas.DetailTugas
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailQuiz : AppCompatActivity() {

    private val api by lazy { RetrofitClient.instance}
    private val NISN by lazy { intent.getStringExtra("NISN") }
    private val id_act_kelas by lazy { intent.getStringExtra("id_act_kelas") }
    private val nama_mapel by lazy { intent.getStringExtra("mapel") }
    private val kelas by lazy { intent.getStringExtra("kelas") }
    private val mulai by lazy { intent.getStringExtra("mulai") }
    private val waktumulai by lazy { intent.getStringExtra("waktu_mulai") }
    private val tglmodify by lazy { intent.getStringExtra("tgl_modify") }
    private val waktumodify by lazy { intent.getStringExtra("waktu_modify") }
    private val selesai by lazy { intent.getStringExtra("selesai") }
    private val waktuselesai by lazy { intent.getStringExtra("waktu_selesai") }
    private val durasiquiz by lazy { intent.getIntExtra("durasi_quiz",0) }
    private val sisawaktu by lazy { intent.getIntExtra("sisawaktuquiz", 0) }
    private val countwaktuquiz by lazy { intent.getStringExtra("countwaktuquiz") }
    private val id_kelas_mapel by lazy { intent.getStringExtra("id_kelas_mapel") }
    private val id_quiz by lazy { intent.getStringExtra("id_quiz") }
    private val namaquiz by lazy { intent.getStringExtra("nama_quiz") }
    private val KKM by lazy { intent.getIntExtra("KKM", 0) }
    private val StatusQuiz by lazy { intent.getStringExtra("status_quiz") }

    private val Gambar by lazy { intent.getStringExtra("gambar_kls") }
    private val nama_guru by lazy { intent.getStringExtra("guru") }
    private val keterangan by lazy { intent.getStringExtra("keterangan") }
    private val id_kelas_siswa by lazy { intent.getStringExtra("id_kelas_siswa") }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_quiz)

        val namamapel = findViewById<TextView>(R.id.namamapel)
        val Judulquiz = findViewById<TextView>(R.id.namaquiz)
        val Tglmulai = findViewById<TextView>(R.id.tglmulai)
        val Deadline = findViewById<TextView>(R.id.deadline)
        val Durasi = findViewById<TextView>(R.id.durasi)
        val Jumlahsoal = findViewById<TextView>(R.id.jumlahsoal)
        val Sisawaktu = findViewById<TextView>(R.id.sisawaktu)
        val btnmulai = findViewById<LinearLayout>(R.id.btnmulai)
        val btnhasil = findViewById<LinearLayout>(R.id.btnhasil)
        val btnback = findViewById<ImageView>(R.id.btnback)

        namamapel.text = "${nama_mapel} - ${kelas}"
        Judulquiz.text = namaquiz
        Tglmulai.text = "${mulai} ${waktumulai}"
        Deadline.text = "${selesai} ${waktuselesai}"
        Durasi.text = "${durasiquiz.toString()} Menit"
        Sisawaktu.text = countwaktuquiz

        if (sisawaktu <=0 ){
            Sisawaktu.setTextColor(Color.RED)
        }else{
            Sisawaktu.setTextColor(Color.parseColor("#EB047810"))
        }

        RetrofitClient.instance.SoalQuiz(id_quiz).enqueue(object  : Callback<ModelSoal>{
            override fun onResponse(call: Call<ModelSoal>, response: Response<ModelSoal>) {
                if (response.isSuccessful){
                    val listSoal = response.body()!!.Soal
                    Jumlahsoal.text = listSoal.size.toString()
                }
            }

            override fun onFailure(call: Call<ModelSoal>, t: Throwable) {
                Log.e("Kesalahan Api Soal", t.toString())
            }

        })

        if (sisawaktu<=0){
            btnmulai.visibility= View.GONE
        }

        RetrofitClient.instance.HasilQuiz(id_quiz, NISN).enqueue(object : Callback<ModelHasil>{
            override fun onResponse(call: Call<ModelHasil>, response: Response<ModelHasil>) {
                if (response.isSuccessful){
                    val id_jawaban = response.body()!!.id_jawaban
                    if (id_jawaban != null){
                        btnmulai.visibility= View.GONE
                        btnhasil.visibility= View.VISIBLE
                    }else{
                        btnmulai.visibility = View.VISIBLE
                        btnhasil.visibility= View.GONE
                    }
                }
            }

            override fun onFailure(call: Call<ModelHasil>, t: Throwable) {
                Log.d("Kesalahan Api Hasil Quiz ", t.toString())
            }

        })

        btnmulai.setOnClickListener {
            var alertDialog = AlertDialog.Builder(this@DetailQuiz)
                .setTitle("Apakah Anda ingin Mulai Quiz ?")
                .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
                    val mulai = Intent(this@DetailQuiz, Quiz::class.java)
                        .putExtra("id_kelas_mapel", id_kelas_mapel)
                        .putExtra("id_act_kelas",id_act_kelas)
                        .putExtra("id_quiz", id_quiz)
                        .putExtra("nama_quiz", namaquiz)
                        .putExtra("mapel", nama_mapel)
                        .putExtra("kelas", kelas)
                        .putExtra("durasi_quiz", durasiquiz)
                        .putExtra("KKM", KKM)
                        .putExtra("status_quiz", StatusQuiz)
                        .putExtra("tgl_modify_quiz", tglmodify)
                        .putExtra("mulai", mulai)
                        .putExtra("waktu_mulai", waktumulai)
                        .putExtra("selesai", selesai)
                        .putExtra("waktu_selesai", waktuselesai)
                        .putExtra("tgl_modify",tglmodify)
                        .putExtra("waktu_modify", waktumodify)
                        .putExtra("sisawaktuquiz", sisawaktu)
                        .putExtra("countwaktuquiz", countwaktuquiz)
                        .putExtra("NISN", NISN)
                        .putExtra("status_quiz", StatusQuiz)

                        .putExtra("gambar_kls", Gambar)
                        .putExtra("guru", nama_guru)
                        .putExtra("keterangan", keterangan)
                        .putExtra("id_kelas_siswa", id_kelas_siswa)


                    startActivity(mulai)

                })
                .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, which ->  })
                .setIcon(R.drawable.warning_icon)
                .show()



        }

        btnhasil.setOnClickListener {
            val hasil = Intent(this@DetailQuiz, HasilQuiz::class.java)
                .putExtra("id_kelas_mapel", id_kelas_mapel)
                .putExtra("id_act_kelas",id_act_kelas)
                .putExtra("id_quiz", id_quiz)
                .putExtra("nama_quiz", namaquiz)
                .putExtra("mapel", nama_mapel)
                .putExtra("kelas", kelas)
                .putExtra("durasi_quiz", durasiquiz)
                .putExtra("KKM", KKM)
                .putExtra("status_quiz", StatusQuiz)
                .putExtra("tgl_modify_quiz", tglmodify)
                .putExtra("mulai", mulai)
                .putExtra("waktu_mulai", waktumulai)
                .putExtra("selesai", selesai)
                .putExtra("waktu_selesai", waktuselesai)
                .putExtra("tgl_modify",tglmodify)
                .putExtra("waktu_modify", waktumodify)
                .putExtra("sisawaktuquiz", sisawaktu)
                .putExtra("countwaktuquiz", countwaktuquiz)
                .putExtra("NISN", NISN)
                .putExtra("status_quiz", StatusQuiz)

                .putExtra("gambar_kls", Gambar)
                .putExtra("guru", nama_guru)
                .putExtra("keterangan", keterangan)
                .putExtra("id_kelas_siswa", id_kelas_siswa)

            startActivity(hasil)
        }

        btnback.setOnClickListener {
            val kembali = Intent(this@DetailQuiz, Detailkelas::class.java)
                .putExtra("gambar_kls", Gambar)
                .putExtra("guru", nama_guru)
                .putExtra("keterangan", keterangan)
                .putExtra("id_kelas_siswa", id_kelas_siswa)
                .putExtra("NISN", NISN)
                .putExtra("mapel", nama_mapel)
                .putExtra("kelas", kelas)
                .putExtra("id_kelas_mapel", id_kelas_mapel)
            startActivity(kembali)
        }

    }
}
