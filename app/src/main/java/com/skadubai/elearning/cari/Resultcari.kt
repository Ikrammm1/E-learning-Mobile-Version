package com.skadubai.elearning.cari

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skadubai.elearning.API.RetrofitClient
import com.skadubai.elearning.R
import com.skadubai.elearning.detailkelas.AdapterDetailkls
import com.skadubai.elearning.model.ModelCari
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Resultcari : AppCompatActivity() {

    private val api by lazy { RetrofitClient.instance}
    private val Jurusan by lazy { intent.getStringExtra("cari") }
    private val Word by lazy { intent.getStringExtra("word") }
    private val NISN by lazy { intent.getStringExtra("NISN") }
    lateinit var adapterResult : Adapterresult
    private lateinit var listKelas : RecyclerView
    lateinit var EdCari : SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultcari)

        val noresult = findViewById<LinearLayout>(R.id.noresult)
        val title = findViewById<TextView>(R.id.titleresult)
        EdCari = findViewById(R.id.edCari)

        EdCari.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val Word: String = query.toString()
                if (Word.isEmpty()) {
                    Log.d("Text cari ", Word)
                } else {
                    Log.d("Text cari ", Word)
                    api.CariWord(Word).enqueue(object : Callback<ModelCari> {
                        override fun onResponse(call: Call<ModelCari>, response: Response<ModelCari>) {
                            if (response.isSuccessful) {
                                val listData = response.body()!!.KelasMapel
                                adapterResult.setData(listData)
                                if (listData.size == 0) {
                                    noresult.visibility = View.VISIBLE
                                } else {
                                    noresult.visibility = View.GONE
                                }
                            }
                        }

                        override fun onFailure(call: Call<ModelCari>, t: Throwable) {
                            Log.d("Kesalahan API", t.toString())
                        }

                    })
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }

        })



//        title.text = Word
        listKelas = findViewById(R.id.list_hasilcari)
        adapterResult = Adapterresult(arrayListOf(), object : Adapterresult.OnAdapterlistener{
            override fun onClick(detail: ModelCari.DaftarKelas) {
                Log.d("Ini kelas mapel", detail.code.toString())
                val MasukKelas = Intent(this@Resultcari, MasukKelas::class.java)
                    .putExtra("id_kelas_mapel", detail.id_kelas_mapel)
                    .putExtra("nama_mapel", detail.nama_mapel)
                    .putExtra("kelas", detail.kelas)
                    .putExtra("nama_guru", detail.nama_guru)
                    .putExtra("token", detail.token)
                    .putExtra("NISN", NISN)
                startActivity(MasukKelas)
            }

        })
        listKelas.adapter = adapterResult

        if (Jurusan != null) {
            api.CariKelas(Jurusan).enqueue(object : Callback<ModelCari> {
                override fun onResponse(call: Call<ModelCari>, response: Response<ModelCari>) {
                    if (response.isSuccessful) {
                        val listData = response.body()!!.KelasMapel
                        adapterResult.setData(listData)
                        if (listData.size == 0) {
                            noresult.visibility = View.VISIBLE
                        } else {
                            noresult.visibility = View.GONE
                        }
                    }
                }

                override fun onFailure(call: Call<ModelCari>, t: Throwable) {
                    Log.d("Kesalahan API", t.toString())
                }

            })
        }else{
            api.CariWord(Word).enqueue(object : Callback<ModelCari> {
                override fun onResponse(call: Call<ModelCari>, response: Response<ModelCari>) {
                    if (response.isSuccessful) {
                        val listData = response.body()!!.KelasMapel
                        adapterResult.setData(listData)
                        if (listData.size == 0) {
                            noresult.visibility = View.VISIBLE
                        } else {
                            noresult.visibility = View.GONE
                        }
                    }
                }

                override fun onFailure(call: Call<ModelCari>, t: Throwable) {
                    Log.d("Kesalahan API", t.toString())
                }

            })
        }


    }
}
