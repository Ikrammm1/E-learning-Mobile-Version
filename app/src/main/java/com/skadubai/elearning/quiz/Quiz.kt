package com.skadubai.elearning.quiz

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skadubai.elearning.API.Jawaban
import com.skadubai.elearning.API.QuizRequest
import com.skadubai.elearning.API.RetrofitClient
import com.skadubai.elearning.R
import com.skadubai.elearning.model.ModelSoal
import com.skadubai.elearning.model.ResponseLogin
import kotlinx.android.synthetic.main.activity_quiz.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Quiz : AppCompatActivity() {
    private lateinit var SoalArrayList : ArrayList<ModelSoal.Daftar_soal>
    lateinit var rvSoal : RecyclerView
    lateinit var SoalAdapter : AdapterSoal
    private var gridlayoutManager : GridLayoutManager? = null
    private val id_quiz by lazy { intent.getStringExtra("id_quiz") }
    private val NISN by lazy { intent.getStringExtra("NISN") }
    private val id_kelas_mapel by lazy { intent.getStringExtra("id_kelas_mapel") }
    private val id_act_kelas by lazy { intent.getStringExtra("id_act_kelas") }
    private val namaquiz by lazy { intent.getStringExtra("nama_quiz") }
    private val nama_mapel by lazy { intent.getStringExtra("mapel") }
    private val kelas by lazy { intent.getStringExtra("kelas") }
    private val durasiquiz by lazy { intent.getIntExtra("durasi_quiz",0) }
    private val KKM by lazy { intent.getIntExtra("KKM",0) }
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
        setContentView(R.layout.activity_quiz)

        rvSoal = findViewById(R.id.listSoal)
        val btnSubmit = findViewById<LinearLayout>(R.id.btnsubmit)
        val judulquiz = findViewById<TextView>(R.id.judul_quiz)
        val namaMapel = findViewById<TextView>(R.id.mapelkelas)
        val jmlSoal = findViewById<TextView>(R.id.jmlsoal)


        judulquiz.text = namaquiz
        namaMapel.text = "${nama_mapel} - ${kelas}"


        gridlayoutManager = GridLayoutManager(this,1,
            LinearLayoutManager.VERTICAL, false)

        rvSoal?.layoutManager = gridlayoutManager
        rvSoal?.setHasFixedSize(true)

        var listJawaban = mutableListOf<Jawaban>()

        SoalAdapter = AdapterSoal(arrayListOf(), object : AdapterSoal.OnAdapterlistener{
            override fun onClick(detail: ModelSoal.Daftar_soal) {
                TODO("Not yet implemented")
            }

            override fun onSelected(idSoal: String, jawaban: String) {
                super.onSelected(idSoal, jawaban)
                val index = listJawaban.withIndex().first {
                    it.value.idSoal === idSoal
                }.index

                listJawaban.set(
                    index,
                    Jawaban(
                        idSoal,
                        jawaban
                    )
                )
            }
        })
        rvSoal.adapter = SoalAdapter

        btnSubmit.setOnClickListener {

            var alertDialog = AlertDialog.Builder(this@Quiz)
                .setTitle("Apakah Anda ingin Mengirim Jawaban ?")
                .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
                    val request = QuizRequest(
                        id_quiz,
                        NISN,
                        listJawaban
                    )

                    RetrofitClient.instance.jawabQuiz(request).enqueue(object  : Callback<ResponseLogin>{
                        override fun onResponse(
                            call: Call<ResponseLogin>,
                            response: Response<ResponseLogin>
                        ) {
                            Log.d("berhasil", "Berhasil menjawab quiz")
                            val keHasil = Intent(this@Quiz, SimpanHasilQuiz::class.java)
                                .putExtra("id_quiz", id_quiz)
                                .putExtra("NISN", NISN)
                                .putExtra("id_kelas_mapel", id_kelas_mapel)
                                .putExtra("id_act_kelas",id_act_kelas)
                                .putExtra("nama_quiz", namaquiz)
                                .putExtra("mapel", nama_mapel)
                                .putExtra("kelas", kelas)
                                .putExtra("durasi_quiz", durasiquiz)
                                .putExtra("KKM", KKM)
                                .putExtra("mulai", mulai)
                                .putExtra("waktu_mulai", waktumulai)
                                .putExtra("selesai", selesai)
                                .putExtra("waktu_selesai", waktuselesai)
                                .putExtra("tgl_modify",tglmodify)
                                .putExtra("waktu_modify", waktumodify)
                                .putExtra("sisawaktuquiz", sisawaktu)
                                .putExtra("countwaktuquiz", countwaktuquiz)
                                .putExtra("status_quiz", StatusQuiz)

                                .putExtra("gambar_kls", Gambar)
                                .putExtra("guru", nama_guru)
                                .putExtra("keterangan", keterangan)
                                .putExtra("id_kelas_siswa", id_kelas_siswa)
                            startActivity(keHasil)
                        }

                        override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                            Log.e("Kesalahan Api Jawaban", t.toString())
                        }

                    })
                })
                .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, which ->  })
                .setIcon(R.drawable.warning_icon)
                .show()


        }



        RetrofitClient.instance.SoalQuiz(id_quiz).enqueue(object  : Callback<ModelSoal>{
            override fun onResponse(call: Call<ModelSoal>, response: Response<ModelSoal>) {
                if (response.isSuccessful){
                    val listSoal = response.body()!!.Soal
                    jmlSoal.text = "Jumlah Soal : ${listSoal.size.toString()}"
                        SoalAdapter.setdData(listSoal)

                    listJawaban.addAll(listSoal.map {
                        Jawaban(
                            it.id_soal,
                            "pilihan_a"
                        )
                    })
                }
            }

            override fun onFailure(call: Call<ModelSoal>, t: Throwable) {
                Log.e("Kesalahan Api Soal", id_quiz.toString())
            }

        })
        val durasi = durasiquiz *(1000*60).toLong()

        val countdown = object : CountDownTimer(durasi, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Dilakukan setiap detik saat countdown berjalan
                val jam = millisUntilFinished / (1000*60*60)
                val sisamilli = millisUntilFinished % (1000*60*60)
                val sisamenit = sisamilli % (60*1000)
                val seconds = sisamenit / 1000
                val minute = sisamilli/(1000 * 60)
                // Lakukan sesuatu, misalnya update UI dengan menampilkan detik yang tersisa
                countdown.text = "$jam : $minute : $seconds"
            }

            override fun onFinish() {
                // Dilakukan saat countdown selesai
                countdown.text = "Waktu Habis"
                // Lakukan tindakan tertentu setelah countdown selesai
                var alertDialog = AlertDialog.Builder(this@Quiz)
                    .setTitle("Waktu Habis")
                    .setPositiveButton("Oke", DialogInterface.OnClickListener { dialog, which ->

                    })
                    .show()

                val request = QuizRequest(
                    id_quiz,
                    NISN,
                    listJawaban
                )

                RetrofitClient.instance.jawabQuiz(request).enqueue(object  : Callback<ResponseLogin>{
                    override fun onResponse(
                        call: Call<ResponseLogin>,
                        response: Response<ResponseLogin>
                    ) {
                        Log.d("berhasil", "Berhasil menjawab quiz")
                        val keHasil = Intent(this@Quiz, SimpanHasilQuiz::class.java)
                            .putExtra("id_quiz", id_quiz)
                            .putExtra("NISN", NISN)
                            .putExtra("id_kelas_mapel", id_kelas_mapel)
                            .putExtra("id_act_kelas",id_act_kelas)
                            .putExtra("nama_quiz", namaquiz)
                            .putExtra("mapel", nama_mapel)
                            .putExtra("kelas", kelas)
                            .putExtra("durasi_quiz", durasiquiz)
                            .putExtra("KKM", KKM)
                            .putExtra("mulai", mulai)
                            .putExtra("waktu_mulai", waktumulai)
                            .putExtra("selesai", selesai)
                            .putExtra("waktu_selesai", waktuselesai)
                            .putExtra("tgl_modify",tglmodify)
                            .putExtra("waktu_modify", waktumodify)
                            .putExtra("sisawaktuquiz", sisawaktu)
                            .putExtra("countwaktuquiz", countwaktuquiz)
                            .putExtra("status_quiz", StatusQuiz)

                            .putExtra("gambar_kls", Gambar)
                            .putExtra("guru", nama_guru)
                            .putExtra("keterangan", keterangan)
                            .putExtra("id_kelas_siswa", id_kelas_siswa)

                        startActivity(keHasil)
                    }

                    override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                        Log.e("Kesalahan Api", t.toString())
                    }

                })

            }
        }

// Mulai countdown
        countdown.start()



    }
}
