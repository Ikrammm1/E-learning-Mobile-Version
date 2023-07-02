package com.skadubai.elearning

import android.app.DownloadManager
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.Html
import android.util.Log
import android.webkit.CookieManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.skadubai.elearning.API.RetrofitClient
import com.skadubai.elearning.databinding.ActivityJwbtugasBinding
import com.skadubai.elearning.databinding.ActivityMainBinding
import com.skadubai.elearning.fragments.BerandaFragment
import com.skadubai.elearning.fragments.TugasFragment
import com.skadubai.elearning.tugas.DetailTugas
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class Jwbtugas : AppCompatActivity() {

    private lateinit var binding: ActivityJwbtugasBinding


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

    lateinit var ptNama : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJwbtugasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ptNama = findViewById(R.id.txtnamapengirim) as EditText
        val Nama = ptNama.text.toString()

        binding.btnback.setOnClickListener {
            this.finish()
        }

        binding.pilihfile.setOnClickListener {
            if (Build.VERSION.SDK_INT> Build.VERSION_CODES.M){
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    val permission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permission, 1001)
                }else{
                    filePicker()
                }
            }else{
                filePicker()
            }
        }

        binding.namamapel.setText(nama_mapel)
        binding.kelas.setText(kelas)
        binding.jdltugas.setText(Judul)
        binding.filetugas.setText(Namafile)
        binding.destugas.setText(Html.fromHtml(Deskripsi))

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


    }
    private fun filePicker(){
        val intent = Intent()
            .setType("*/*")
            .setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        ptNama = findViewById(R.id.txtnamapengirim) as EditText
        val Nama = ptNama.text.toString()

        if (requestCode == 111 && resultCode == RESULT_OK) {
            if ( resultCode.equals(-1)){
                val Uri = data?.data!!

                val path = RealPathUtil.getRealPath(this, Uri!!)
                val file = File(path)
                Log.d("file",path.toString())
                val getName = path?.substringAfterLast("/")
                Log.d("file",getName.toString())
                binding.titleFile.text = getName

                binding.btnsubmit.setOnClickListener {

                    var alertDialog = AlertDialog.Builder(this@Jwbtugas)
                        .setTitle("Apakah Anda ingin Mengirim Jawaban?")
                        .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
                            RetrofitClient.instance.UploadJwb(
                                Nama,
                                getName.toString(),
                                NISN,
                                Id_tugas
                            ).enqueue(object  : Callback<PostJwb>{
                                override fun onResponse(
                                    call: Call<PostJwb>,
                                    response: Response<PostJwb>
                                ) {
                                    Toast.makeText(this@Jwbtugas, getName.toString(), Toast.LENGTH_SHORT).show()
                                }

                                override fun onFailure(call: Call<PostJwb>, t: Throwable) {
                                    Log.d("ini kesalahan API jwb", Id_tugas.toString())
                                }

                            })

                            postImage(file, getName.toString())
                            val submit = Intent(this@Jwbtugas, DetailTugas::class.java)
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
                                .putExtra("nama_pengirim", Nama)
                                .putExtra("jwb_text", Jwbtext)
                                .putExtra("file", getName)
                                .putExtra("submited", Submited)
                                .putExtra("update", Update)
                                .putExtra("filejwb", FileJwb)
                                .putExtra("NISN", NISN)
                                .putExtra("sisawaktu", Sisawaktu)
                                .putExtra("countwaktu", Countwaktu)
                            startActivity(submit)

                        })
                        .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, which ->  })
                        .setIcon(R.drawable.warning_icon)
                        .show()

                }


            }



        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1001 ->{
                if (grantResults.size >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    filePicker()
                }else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun postImage(file : File, name : String){

        ptNama = findViewById(R.id.txtnamapengirim) as EditText
        val Nama = ptNama.text.toString()

        //get url
        AndroidNetworking.upload("https://elearning.smkn2barabai.sch.id/elearningskadubai/API/upload_filejwb.php")
            .addHeaders("Content-Type", "application/json")
            .addMultipartFile("file",file)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    try {
                        val getRespon = response.getString("status")
                        if (getRespon.equals("Ok")){
//
//                            Toast.makeText(this@Jwbtugas, "Berhasil", Toast.LENGTH_SHORT).show()

                        }else{
                            Toast.makeText(this@Jwbtugas, "salah", Toast.LENGTH_SHORT).show()
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(this@Jwbtugas, "kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onError(error: ANError) {
                    // handle error
                    Log.d("ini eror", error.toString())
                }
            })
    }

}
