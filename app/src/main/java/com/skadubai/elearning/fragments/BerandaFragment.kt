package com.skadubai.elearning.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.skadubai.elearning.API.RetrofitClient
import com.skadubai.elearning.Adapter.AdapterKlsSiswa
import com.skadubai.elearning.Adapter.AdapterTgsLimit
import com.skadubai.elearning.Jwbtugas
import com.skadubai.elearning.R
import com.skadubai.elearning.cari.Resultcari

import com.skadubai.elearning.detailkelas.Detailkelas
import com.skadubai.elearning.helper.Constant
import com.skadubai.elearning.helper.SharedPrefManager
import com.skadubai.elearning.model.ModelJmlkls
import com.skadubai.elearning.model.ModelKlsSiswa
import com.skadubai.elearning.model.ModelProfile
import com.skadubai.elearning.model.ModelTgs
import com.skadubai.elearning.tugas.DetailTugas
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_beranda.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BerandaFragment(
    var onClickTugas: (() -> Unit)
) : Fragment() {
    lateinit var s:SharedPrefManager

    lateinit var rvKelas : RecyclerView
    lateinit var rvTgs : RecyclerView
    lateinit var klsAdapter : AdapterKlsSiswa
    lateinit var daftartgs : AdapterTgsLimit
    private var gridlayoutManager : GridLayoutManager? = null
    private var gridlayouttgs : GridLayoutManager? = null
    private lateinit var fotoprof : ImageView
    private lateinit var Jmlkls : TextView

    private lateinit var swipeRefresh : SwipeRefreshLayout
    lateinit var EdCari : SearchView




    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment

        var view = inflater.inflate(R.layout.fragment_beranda, container, false)

        EdCari = view.findViewById(R.id.edCari)
        rvKelas = view.findViewById(R.id.listView)
        gridlayoutManager = GridLayoutManager(requireContext(),2,LinearLayoutManager.VERTICAL, false)
        rvTgs = view.findViewById(R.id.listlimit)
        fotoprof = view.findViewById(R.id.ftprofil)
        Jmlkls = view.findViewById(R.id.txtJmlkls)
        var semuaTugas = view.findViewById<CardView>(R.id.semuatugas)
        swipeRefresh = view.findViewById(R.id.swipeRefresh)


        semuaTugas.setOnClickListener {
            onClickTugas()
        }

        rvTgs.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        rvTgs?.setHasFixedSize(true)

        rvKelas?.layoutManager = gridlayoutManager
        rvKelas?.setHasFixedSize(true)



        klsAdapter = AdapterKlsSiswa(arrayListOf(), object  : AdapterKlsSiswa.OnAdapterlistener{
            override fun onClick(detail: ModelKlsSiswa.KelasSiswa) {
                val kedetailkls = Intent(activity, Detailkelas::class.java)
                    .putExtra("gambar_kls",detail.gambar_kls)
                    .putExtra("keterangan", detail.keterangan)
                    .putExtra("guru", detail.nama_guru)
                    .putExtra("kelas", detail.kelas)
                    .putExtra("mapel", detail.nama_mapel)
                    .putExtra("id_kelas_mapel", detail.id_kelas_mapel)
                    .putExtra("id_kelas_siswa", detail.id_kelas_siswa)
                    .putExtra("NISN", detail.NISN)
                startActivity(kedetailkls)

            }

            override fun onDelete(detail: ModelKlsSiswa.KelasSiswa) {
                Toast.makeText(requireActivity().applicationContext,"Maaf, Masih Tahap Pengembangan!",
                    Toast.LENGTH_LONG).show()
            }

        })
        rvKelas.adapter = klsAdapter

        daftartgs = AdapterTgsLimit(arrayListOf(), object :AdapterTgsLimit.OnAdapterlistener{
            override fun onClick(detail: ModelTgs.Daftar_tgs) {
                val kedetailkls = Intent(activity, DetailTugas::class.java)
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
                startActivity(kedetailkls)
            }

            override fun onDelete(detail: ModelTgs.Daftar_tgs) {
                Toast.makeText(requireActivity().applicationContext,"Maaf, Masih Tahap Pengembangan!",
                    Toast.LENGTH_LONG).show()
            }

        })
        rvTgs.adapter = daftartgs


        val txtnama : TextView = view.findViewById(R.id.txtNama)
        val txtJmlkls : TextView = view.findViewById(R.id.txtJmlkls)
        val txtJmlTgs : TextView = view.findViewById(R.id.txtJmlTgs)
        val notugas : TextView = view.findViewById(R.id.tdkTugas)

        val args = this.arguments
        val nama = args?.get("nama_siswa")
        val username = args?.get("username")
        val jmlkls = args?.get("jml_kls_siswa")
        val NISN = username.toString()
        val image = args?.get("gambar")
//        val urlFoto = "http://192.168.100.151/$image"
        val urlFoto = "https://elearning.smkn2barabai.sch.id/$image"
        Picasso.get().load(urlFoto).into(fotoprof)

        Log.d("Image", image.toString())

        txtnama.text = nama.toString()


        s = SharedPrefManager.getIntstance(requireActivity())

        RetrofitClient.instance.KelasSiswa(NISN).enqueue(object  : Callback<ModelKlsSiswa>{
            override fun onResponse(call: Call<ModelKlsSiswa>, response: Response<ModelKlsSiswa>) {
                if(response.isSuccessful){
                    val listData = response.body()!!.Kelas_Siswa
                    klsAdapter.setdData(listData)
                    txtJmlkls.text = listData.size.toString()

                }
            }

            override fun onFailure(call: Call<ModelKlsSiswa>, t: Throwable) {
                Log.e(t.message, t.toString())
            }

        })
        RetrofitClient.instance.TugasLimit(NISN).enqueue(object :Callback<ModelTgs>{
            override fun onResponse(call: Call<ModelTgs>, response: Response<ModelTgs>) {
                if(response.isSuccessful){
                    val listData = response.body()!!.daftar_tugas
                    daftartgs.setdData(listData)
                    txtJmlTgs.text = listData.size.toString()
                    if (listData.size == 0){
                        notugas.visibility = View.VISIBLE
                        rvTgs.visibility = View.GONE
                        semuaTugas.visibility = View.GONE
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

        fotoprof.setOnClickListener {
            val Intent = Intent(activity, Jwbtugas::class.java)
            startActivity(Intent)
        }

        swipeRefresh.setOnRefreshListener {
            Toast.makeText(requireContext().applicationContext,"Memperbahrui Data", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                swipeRefresh.isRefreshing = false

                RetrofitClient.instance.KelasSiswa(NISN).enqueue(object  : Callback<ModelKlsSiswa>{
                    override fun onResponse(call: Call<ModelKlsSiswa>, response: Response<ModelKlsSiswa>) {
                        if(response.isSuccessful){
                            val listData = response.body()!!.Kelas_Siswa
                            klsAdapter.setdData(listData)
                            txtJmlkls.text = listData.size.toString()
                        }
                    }

                    override fun onFailure(call: Call<ModelKlsSiswa>, t: Throwable) {
                        Log.e(t.message, t.toString())
                    }

                })
                RetrofitClient.instance.TugasLimit(NISN).enqueue(object :Callback<ModelTgs>{
                    override fun onResponse(call: Call<ModelTgs>, response: Response<ModelTgs>) {
                        if(response.isSuccessful){
                            val listData = response.body()!!.daftar_tugas
                            daftartgs.setdData(listData)
                            txtJmlTgs.text = listData.size.toString()
                            if (listData.size == 0){
                                notugas.visibility = View.VISIBLE
                                rvTgs.visibility = View.GONE
                                semuaTugas.visibility = View.GONE
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

        EdCari.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val Word: String = query.toString()
                if (Word.isEmpty()) {
                    Log.d("Text cari ", Word)
                } else {
                    Log.d("Text cari ", Word)
                    val cari = Intent(requireContext(), Resultcari::class.java)
                        .putExtra("word", Word)
                        .putExtra("NISN", NISN)
                    startActivity(cari)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }

        })


        return view
    }



}
