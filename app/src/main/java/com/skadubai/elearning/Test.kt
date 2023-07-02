package com.skadubai.elearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.skadubai.elearning.databinding.ActivityTestBinding
import com.skadubai.elearning.helper.Constant
import com.skadubai.elearning.helper.SharedPrefManager
import kotlinx.android.synthetic.main.activity_test.*

class Test : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding

    lateinit var sharedpref : SharedPrefManager

    private lateinit var nama:TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}
