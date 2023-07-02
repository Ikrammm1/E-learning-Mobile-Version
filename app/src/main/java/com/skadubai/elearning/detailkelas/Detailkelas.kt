package com.skadubai.elearning.detailkelas

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.skadubai.elearning.API.ApiService
import com.skadubai.elearning.API.RetrofitClient
import com.skadubai.elearning.API.RetrofitLogin
import com.skadubai.elearning.Jwbtugas
import com.skadubai.elearning.MainActivity
import com.skadubai.elearning.PostJwb
import com.skadubai.elearning.R
import com.skadubai.elearning.databinding.ActivityDetailkelasBinding
import com.skadubai.elearning.databinding.ActivityMainBinding
import com.skadubai.elearning.fragments.BerandaFragment
import com.skadubai.elearning.model.ModelAct
import com.skadubai.elearning.model.ModelKlsSiswa
import com.skadubai.elearning.model.ModelTgs
import com.skadubai.elearning.quiz.DetailQuiz
import com.skadubai.elearning.quiz.Quiz
import com.skadubai.elearning.tugas.DetailTugas
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Detailkelas : AppCompatActivity() {
    private lateinit var binding: ActivityDetailkelasBinding

    private val api by lazy { RetrofitClient.instance}
    private val Gambar by lazy { intent.getStringExtra("gambar_kls") }
    private val nama_guru by lazy { intent.getStringExtra("guru") }
    private val kelas by lazy { intent.getStringExtra("kelas") }
    private val keterangan by lazy { intent.getStringExtra("keterangan") }
    private val nama_mapel by lazy { intent.getStringExtra("mapel") }
    private val NISN by lazy { intent.getStringExtra("NISN") }
    private val id_kelas_siswa by lazy { intent.getStringExtra("id_kelas_siswa") }
    private val id_kelas_mapel by lazy { intent.getStringExtra("id_kelas_mapel") }

    private lateinit var fotoclass : ImageView
    private lateinit var mapel: TextView
    private lateinit var kelas_mapel: TextView
    private lateinit var guru: TextView
    private lateinit var kt: TextView

    private lateinit var AdapterAct : AdapterDetailkls
    private lateinit var listAct : RecyclerView
    private lateinit var Refresh : SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDetailkelasBinding.inflate(layoutInflater)
        setContentView(binding.root)


        fotoclass = findViewById(R.id.ftclass)
        mapel = findViewById(R.id.nm_mapel)
        kelas_mapel = findViewById(R.id.kls)
        guru = findViewById(R.id.nm_guru)
        kt = findViewById(R.id.keterangan)
        Refresh = findViewById(R.id.Refresh)




        mapel.setText(nama_mapel)
        kelas_mapel.setText(kelas)
        guru.setText(nama_guru)
        kt.setText(keterangan)

        val id_mapel = id_kelas_mapel
        val image = Gambar
        val urlFoto = "https://elearning.smkn2barabai.sch.id/$image"
        Picasso.get().load(urlFoto).into(fotoclass)

        Log.d("Image", image.toString())

        listAct = findViewById(R.id.list_act)

        AdapterAct = AdapterDetailkls(arrayListOf(),object : AdapterDetailkls.OnAdapterlistener{
            override fun onClick(aktifitas: ModelAct.Daftar_act) {
                val detailquiz = Intent(this@Detailkelas, DetailQuiz::class.java)
                    .putExtra("id_kelas_mapel", aktifitas.id_kelas_mapel)
                    .putExtra("id_act_kelas", aktifitas.id_act_kelas)
                    .putExtra("topik", aktifitas.topik)
                    .putExtra("deskripsi", aktifitas.deskripsi)
                    .putExtra("id_quiz", aktifitas.id_quiz)
                    .putExtra("nama_quiz", aktifitas.nama_quiz)
                    .putExtra("durasi_quiz", aktifitas.durasi_quiz)
                    .putExtra("tgl_mulai", aktifitas.tgl_mulai)
                    .putExtra("tgl_akhir", aktifitas.tgl_akhir)
                    .putExtra("KKM", aktifitas.KKM)
                    .putExtra("status_quiz", aktifitas.status_quiz)
                    .putExtra("tgl_buat_quiz", aktifitas.tgl_buat_quiz)
                    .putExtra("tgl_modify_quiz", aktifitas.tgl_buat_quiz)
                    .putExtra("mulai", aktifitas.mulai)
                    .putExtra("waktu_mulai", aktifitas.waktu_mulai)
                    .putExtra("selesai", aktifitas.selesai)
                    .putExtra("waktu_selesai", aktifitas.waktu_selesai)
                    .putExtra("tgl_modify", aktifitas.tgl_modify)
                    .putExtra("waktu_modify", aktifitas.waktu_modify)
                    .putExtra("sisawaktuquiz", aktifitas.sisawaktuquiz)
                    .putExtra("countwaktuquiz", aktifitas.countwaktuquiz)
                    .putExtra("NISN", NISN)
                    .putExtra("mapel", nama_mapel)
                    .putExtra("kelas", kelas)

                    .putExtra("gambar_kls", Gambar)
                    .putExtra("guru", nama_guru)
                    .putExtra("keterangan", keterangan)
                    .putExtra("id_kelas_siswa", id_kelas_siswa)



                startActivity(detailquiz)
            }

        },this)
        listAct.adapter = AdapterAct
        api.AktifitasKls(id_kelas_mapel).enqueue(object : Callback<ModelAct>{
            override fun onResponse(call: Call<ModelAct>, response: Response<ModelAct>) {
                if (response.isSuccessful){
                    val listAktifitas =  response.body()!!.Aktifitas
                    AdapterAct.setData(listAktifitas)
                }

            }

            override fun onFailure(call: Call<ModelAct>, t: Throwable) {
                Toast.makeText(this@Detailkelas, "Ini salah", Toast.LENGTH_SHORT).show()
            }

        })

//        binding.btnback.setOnClickListener {
//            val berandafragment = BerandaFragment(onClickTugas = {})
//            val fragment : Fragment? =
//                supportFragmentManager.findFragmentByTag(BerandaFragment::class.java.simpleName)
//            if (fragment !is  BerandaFragment){
//                supportFragmentManager.beginTransaction()
//                    .add(R.id.contenview, berandafragment, BerandaFragment::class.java.simpleName)
//                    .commit()
//            }
//        }

        binding.btnback.setOnClickListener {
            val kembali = Intent(this@Detailkelas, MainActivity::class.java)
            startActivity(kembali)
        }

        mapel.setOnClickListener {
            val kequiz = Intent(this@Detailkelas, Quiz::class.java)
            startActivity(kequiz)
        }

        Refresh.setOnRefreshListener {
            Toast.makeText(this.applicationContext,"Memperbahrui Data", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                Refresh.isRefreshing = false

                mapel.setText(nama_mapel)
                kelas_mapel.setText(kelas)
                guru.setText(nama_guru)
                kt.setText(keterangan)

                val id_mapel = id_kelas_mapel
                val image = Gambar
                val urlFoto = "https://elearning.smkn2barabai.sch.id/$image"
                Picasso.get().load(urlFoto).into(fotoclass)

                Log.d("Image", image.toString())

                listAct = findViewById(R.id.list_act)

                AdapterAct = AdapterDetailkls(arrayListOf(),object : AdapterDetailkls.OnAdapterlistener{
                    override fun onClick(aktifitas: ModelAct.Daftar_act) {
                        val detailquiz = Intent(this@Detailkelas, DetailQuiz::class.java)
                            .putExtra("id_kelas_mapel", aktifitas.id_kelas_mapel)
                            .putExtra("id_act_kelas", aktifitas.id_act_kelas)
                            .putExtra("topik", aktifitas.topik)
                            .putExtra("deskripsi", aktifitas.deskripsi)
                            .putExtra("id_quiz", aktifitas.id_quiz)
                            .putExtra("nama_quiz", aktifitas.nama_quiz)
                            .putExtra("durasi_quiz", aktifitas.durasi_quiz)
                            .putExtra("tgl_mulai", aktifitas.tgl_mulai)
                            .putExtra("tgl_akhir", aktifitas.tgl_akhir)
                            .putExtra("KKM", aktifitas.KKM)
                            .putExtra("status_quiz", aktifitas.status_quiz)
                            .putExtra("tgl_buat_quiz", aktifitas.tgl_buat_quiz)
                            .putExtra("tgl_modify_quiz", aktifitas.tgl_buat_quiz)
                            .putExtra("mulai", aktifitas.mulai)
                            .putExtra("waktu_mulai", aktifitas.waktu_mulai)
                            .putExtra("selesai", aktifitas.selesai)
                            .putExtra("waktu_selesai", aktifitas.waktu_selesai)
                            .putExtra("tgl_modify", aktifitas.tgl_modify)
                            .putExtra("waktu_modify", aktifitas.waktu_modify)
                            .putExtra("sisawaktuquiz", aktifitas.sisawaktuquiz)
                            .putExtra("countwaktuquiz", aktifitas.countwaktuquiz)
                            .putExtra("NISN", NISN)
                            .putExtra("mapel", nama_mapel)
                            .putExtra("kelas", kelas)

                            .putExtra("gambar_kls", Gambar)
                            .putExtra("guru", nama_guru)
                            .putExtra("keterangan", keterangan)
                            .putExtra("id_kelas_siswa", id_kelas_siswa)



                        startActivity(detailquiz)
                    }

                }, this)
                listAct.adapter = AdapterAct
                api.AktifitasKls(id_kelas_mapel).enqueue(object : Callback<ModelAct>{
                    override fun onResponse(call: Call<ModelAct>, response: Response<ModelAct>) {
                        if (response.isSuccessful){
                            val listAktifitas =  response.body()!!.Aktifitas
                            AdapterAct.setData(listAktifitas)
                        }

                    }

                    override fun onFailure(call: Call<ModelAct>, t: Throwable) {
                        Toast.makeText(this@Detailkelas, "Ini salah", Toast.LENGTH_SHORT).show()
                    }

                })


                Toast.makeText(
                    this.applicationContext,
                    "Memperbahrui Data Selesai",
                    Toast.LENGTH_SHORT
                ).show()


            },3000L)
        }

        binding.btnkeluar.setOnClickListener {

            var alertDialog = AlertDialog.Builder(this@Detailkelas)
                .setTitle("Apakah anda yakin ingin meninggalkan Kelas?")
                .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
                    api.KeluarKelas(NISN!!, id_kelas_mapel!!)
                        .enqueue(object :Callback<ModelAct>{
                            override fun onResponse(
                                call: Call<ModelAct>,
                                response: Response<ModelAct>
                            ) {
                                if (response.isSuccessful){
                                    Toast.makeText(
                                        this@Detailkelas,
                                        "Berhasil Keluar Kelas!",
                                        Toast.LENGTH_SHORT).show()
                                    val kembali = Intent(this@Detailkelas, MainActivity::class.java)
                                    startActivity(kembali)
                                }
                            }

                            override fun onFailure(call: Call<ModelAct>, t: Throwable) {
                                Log.d("Kesalahan : " ,t.toString())
                            }

                        })
                })
                .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, which ->  })
                .setIcon(R.drawable.warning_icon)
                .show()





        }
    }
}
