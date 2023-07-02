package com.skadubai.elearning.beranda

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.skadubai.elearning.API.RetrofitLogin
import com.skadubai.elearning.R
import com.skadubai.elearning.kelas.Caripage
import kotlinx.android.synthetic.main.activity_beranda.*
import kotlinx.android.synthetic.main.activity_login.*

class Beranda : AppCompatActivity() {
    private lateinit var profile : SharedPreferences
    private lateinit var username : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beranda)

        profile = getSharedPreferences("Login_Session", MODE_PRIVATE)


        username = txtNama
        username.text = profile.getString("username", null)

//        getSiswa()

        card_cari.setOnClickListener {
            val keCari = Intent(this@Beranda, Caripage::class.java)
            startActivity(keCari)
        }
    }

//    private fun getSiswa() {
//        val api = RetrofitLogin().getInstance()
//        api.datasiswa()
//    }
}
