package com.skadubai.elearning.landing

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.skadubai.elearning.MainActivity
import com.skadubai.elearning.R
import com.skadubai.elearning.helper.SharedPrefManager
import com.skadubai.elearning.login.Login
import com.skadubai.elearning.register.Register
import kotlinx.android.synthetic.main.activity_landing.*

class Landing : AppCompatActivity() {
    private lateinit var profile : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)



        btn_Log.setOnClickListener {
            val keLogin = Intent (this@Landing, Login::class.java)
            startActivity(keLogin)
        }

//        btn_reg.setOnClickListener {
//            val keRegis = Intent (this@Landing, Register::class.java)
//            startActivity(keRegis)
//        }
        skip.setOnClickListener {
            val keLogin2 = Intent (this@Landing, Login::class.java)
            startActivity(keLogin2)
        }


    }
    override fun onStart(){
        super.onStart()
        if (SharedPrefManager.getIntstance(this).isLoggedIn){
            val intent = Intent(this@Landing, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
