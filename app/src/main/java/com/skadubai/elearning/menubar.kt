package com.skadubai.elearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.skadubai.elearning.databinding.ActivityBerandaBinding
import com.skadubai.elearning.databinding.ActivityMenubarBinding

class menubar : AppCompatActivity() {

    private lateinit var binding: ActivityMenubarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenubarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setUpTabBar()
    }

    private fun setUpTabBar() {
        binding.bottomNav.setOnItemSelectedListener {
            when(it) {
                R.id.nav_home -> binding.txtTest.text = "Home"
                R.id.nav_home -> binding
            }
        }
    }
}
