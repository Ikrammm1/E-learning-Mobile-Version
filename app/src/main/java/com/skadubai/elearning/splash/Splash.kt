package com.skadubai.elearning.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.skadubai.elearning.R
import com.skadubai.elearning.landing.Landing

class Splash : AppCompatActivity() {

    //timer
    private val SPLASH_TIME_OUT:Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //jalankan landing setelah loding

        Handler().postDelayed({
            startActivity(Intent(this, Landing::class.java))
            finish()
        }, SPLASH_TIME_OUT)

    }
}
