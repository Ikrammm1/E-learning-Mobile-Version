package com.skadubai.elearning.fragments


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.skadubai.elearning.API.RetrofitClient
import com.skadubai.elearning.Adapter.AdapterTugas
import com.skadubai.elearning.R
import com.skadubai.elearning.detailkelas.Detailkelas
import com.skadubai.elearning.helper.SharedPrefManager
import com.skadubai.elearning.model.ModelKlsSiswa
import com.skadubai.elearning.model.ModelTgs
import com.skadubai.elearning.tugas.DetailTugas
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TugasFragment : Fragment() {
    lateinit var s: SharedPrefManager

    lateinit var rvTgs : RecyclerView
    lateinit var tgsAdapter : AdapterTugas
    lateinit var notugas : LinearLayout
    private var gridlayoutManager : GridLayoutManager? = null

    private lateinit var RefreshTugas : SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_tugas, container, false)

        RefreshTugas = view.findViewById(R.id.refreshtugas)
        rvTgs = view.findViewById(R.id.listView)
        notugas = view.findViewById(R.id.notugas)
        gridlayoutManager = GridLayoutManager(requireContext(),1,
            LinearLayoutManager.VERTICAL, false)


        rvTgs?.layoutManager = gridlayoutManager
        rvTgs?.setHasFixedSize(true)

        tgsAdapter = activity?.let {
            AdapterTugas(arrayListOf(), object  : AdapterTugas.OnAdapterlistener{
                override fun onClick(detail: ModelTgs.Daftar_tgs) {
                    val kedetailtugas = Intent(activity, DetailTugas::class.java)
                        .putExtra("id_tugas", detail.id_tugas)
                        .putExtra("nama_mapel", detail.nama_mapel)
                        .putExtra("kelas", detail.kelas)
                        .putExtra("judul_tugas", detail.judul_tugas)
                        .putExtra("deskripsi", detail.deskripsi)
                        .putExtra("mulai", detail.mulai)
                        .putExtra("waktu_mulai", detail.waktu_mulai)
                        .putExtra("hari_akhir", detail.hari_akhir)
                        .putExtra("waktu_akhir", detail.waktu_akhir)
                        .putExtra("nama_file", detail.namafile)
                        .putExtra("file_tugas", detail.file_tugas)
                        .putExtra("id_jawaban", detail.id_jawaban)
                        .putExtra("nama_pengirim", detail.nama_pengirim)
                        .putExtra("jwb_text", detail.jwb_text)
                        .putExtra("file", detail.file)
                        .putExtra("submited", detail.submited)
                        .putExtra("update", detail.update)
                        .putExtra("filejwb", detail.filejwb)
                        .putExtra("NISN", detail.NISN)
                        .putExtra("sisawaktu", detail.sisawaktu)
                        .putExtra("countwaktu", detail.countwaktu)
                    startActivity(kedetailtugas)
                }

            }, it)
        }!!
        rvTgs.adapter = tgsAdapter


        val args = this.arguments

        val username = args?.get("username")
        val NISN = username.toString()



        s = SharedPrefManager.getIntstance(requireActivity())

        RetrofitClient.instance.DaftarTugas(NISN).enqueue(object : Callback<ModelTgs>{
            override fun onResponse(call: Call<ModelTgs>, response: Response<ModelTgs>) {
                if(response.isSuccessful){
                    val listData = response.body()!!.daftar_tugas
                    tgsAdapter.setdData(listData)
                    if (listData.size == 0){
                        notugas.visibility = View.VISIBLE
                        rvTgs.visibility = View.GONE
                    }else{
                        notugas.visibility = View.GONE
                        rvTgs.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<ModelTgs>, t: Throwable) {
                Log.e(t.message, t.toString())
            }

        })

        RefreshTugas.setOnRefreshListener {
            Toast.makeText(requireContext().applicationContext,"Memperbahrui Data", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                RefreshTugas.isRefreshing = false

                tgsAdapter = activity?.let {
                    AdapterTugas(arrayListOf(), object  : AdapterTugas.OnAdapterlistener{
                        override fun onClick(detail: ModelTgs.Daftar_tgs) {
                            val kedetailtugas = Intent(activity, DetailTugas::class.java)
                                .putExtra("id_tugas", detail.id_tugas)
                                .putExtra("nama_mapel", detail.nama_mapel)
                                .putExtra("kelas", detail.kelas)
                                .putExtra("judul_tugas", detail.judul_tugas)
                                .putExtra("deskripsi", detail.deskripsi)
                                .putExtra("mulai", detail.mulai)
                                .putExtra("waktu_mulai", detail.waktu_mulai)
                                .putExtra("hari_akhir", detail.hari_akhir)
                                .putExtra("waktu_akhir", detail.waktu_akhir)
                                .putExtra("nama_file", detail.namafile)
                                .putExtra("file_tugas", detail.file_tugas)
                                .putExtra("id_jawaban", detail.id_jawaban)
                                .putExtra("nama_pengirim", detail.nama_pengirim)
                                .putExtra("jwb_text", detail.jwb_text)
                                .putExtra("file", detail.file)
                                .putExtra("submited", detail.submited)
                                .putExtra("update", detail.update)
                                .putExtra("filejwb", detail.filejwb)
                                .putExtra("NISN", detail.NISN)
                                .putExtra("sisawaktu", detail.sisawaktu)
                                .putExtra("countwaktu", detail.countwaktu)
                            startActivity(kedetailtugas)
                        }

                    }, it)
                }!!
                rvTgs.adapter = tgsAdapter


                val args = this.arguments

                val username = args?.get("username")
                val NISN = username.toString()



                s = SharedPrefManager.getIntstance(requireActivity())

                RetrofitClient.instance.DaftarTugas(NISN).enqueue(object : Callback<ModelTgs>{
                    override fun onResponse(call: Call<ModelTgs>, response: Response<ModelTgs>) {
                        if(response.isSuccessful){
                            val listData = response.body()!!.daftar_tugas
                            tgsAdapter.setdData(listData)
                            if (listData.size == 0){
                                notugas.visibility = View.VISIBLE
                                rvTgs.visibility = View.GONE
                            }else{
                                notugas.visibility = View.GONE
                                rvTgs.visibility = View.VISIBLE
                            }
                        }
                    }

                    override fun onFailure(call: Call<ModelTgs>, t: Throwable) {
                        Log.e(t.message, t.toString())
                    }

                })


                Toast.makeText(
                    requireContext().applicationContext,
                    "Memperbahrui Data Selesai",
                    Toast.LENGTH_SHORT
                ).show()


            },3000L)
        }


        return view
    }



}
