package com.skadubai.elearning.cari

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.skadubai.elearning.API.RetrofitClient
import com.skadubai.elearning.MainActivity
import com.skadubai.elearning.R
import com.skadubai.elearning.model.ModelCekkls
import com.skadubai.elearning.model.PostKelas
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MasukKelas : AppCompatActivity() {
    private val api by lazy { RetrofitClient.instance}
    private val id_kelas_mapel by lazy { intent.getStringExtra("id_kelas_mapel") }
    private val Token by lazy { intent.getStringExtra("token") }
    private val Namamapel by lazy { intent.getStringExtra("nama_mapel") }
    private val Kelas by lazy { intent.getStringExtra("kelas") }
    private val Namaguru by lazy { intent.getStringExtra("nama_guru") }
    private val NISN by lazy { intent.getStringExtra("NISN") }

    lateinit var ptToken : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_masuk_kelas)

        val NamaMapel = findViewById<TextView>(R.id.namamapel)
        val NamaGuru = findViewById<TextView>(R.id.namaguru)
        ptToken = findViewById(R.id.txttokeninput) as EditText
        val btnMasuk = findViewById<LinearLayout>(R.id.btnmasuk)
        val btnBack = findViewById<ImageView>(R.id.btnback)

        NamaMapel.text = "${Namamapel} ${Kelas}"
        NamaGuru.text = Namaguru
        ptToken.text.toString()




        btnMasuk.setOnClickListener {

            if (ptToken.text.toString() == Token) {
                api.CekklsSiswa(NISN, id_kelas_mapel).enqueue(object : Callback<ModelCekkls>{
                    override fun onResponse(call: Call<ModelCekkls>, response: Response<ModelCekkls>) {
                        if (response.isSuccessful){
                            if (response.body()!!.response == true){
                                Toast.makeText(
                                    this@MasukKelas,
                                    "Anda telah bergabung dalam kelas",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }else{

                                api.MasukKelas(
                                    NISN,
                                    id_kelas_mapel,
                                    Token,
                                    ptToken.text.toString()
                                ).enqueue(object : Callback<PostKelas> {
                                    override fun onResponse(call: Call<PostKelas>, response: Response<PostKelas>) {
                                        if (response.isSuccessful) {
                                            Toast.makeText(
                                                this@MasukKelas,
                                                "Berhasil. Selamat Belajar",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            Log.d("Berhasi Masuk ", response.body()!!.token.toString())
                                            Log.d("NISN ", NISN.toString())
                                            Log.d("id_kelas_mapel ", id_kelas_mapel.toString())
                                            Log.d("Token ", Token.toString())
                                            Log.d("Token Input", ptToken.text.toString())
                                            startActivity(Intent(this@MasukKelas, MainActivity::class.java))
                                        }
                                    }

                                    override fun onFailure(call: Call<PostKelas>, t: Throwable) {
                                        Toast.makeText(
                                            this@MasukKelas,
                                            "Kesalahan API Masuk${t.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                })


                            }
                        }
                    }

                    override fun onFailure(call: Call<ModelCekkls>, t: Throwable) {
                        Log.d("Kesalahan Api ", t.toString())
                    }

                })

            }else{
                Toast.makeText(
                    this@MasukKelas,
                    "Token Salah",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btnBack.setOnClickListener {
            this.finish()
        }


    }
}
