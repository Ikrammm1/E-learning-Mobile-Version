package com.skadubai.elearning

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.skadubai.elearning.databinding.ActivityMainBinding
import com.skadubai.elearning.fragments.BerandaFragment
import com.skadubai.elearning.fragments.CariFragment
import com.skadubai.elearning.fragments.ProfileFragment
import com.skadubai.elearning.fragments.TugasFragment
import com.skadubai.elearning.helper.SharedPrefManager
import com.skadubai.elearning.login.Login
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var fragmentHome : Fragment
    val fragmentCari: Fragment = CariFragment()
    val fragmentTugas : Fragment = TugasFragment()
    val fragmentProf : Fragment = ProfileFragment()
    val fm: FragmentManager = supportFragmentManager
    lateinit var active : Fragment

    private lateinit var binding : ActivityMainBinding
    private lateinit var menu : Menu
    private lateinit var menuItem: MenuItem
    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpBottomNav()
    }

    private fun setUpBottomNav() {
        val data = SharedPrefManager.getIntstance(this).user
        val bundle = Bundle()
        bundle.putString("id_user", data.id_user.toString())
        bundle.putString("username", data.username.toString())
        bundle.putString("password", data.password.toString())
        bundle.putString("role_id", data.role_id.toString())
        bundle.putString("NISN", data.NISN.toString())
        bundle.putString("nama_siswa", data.nama_siswa.toString())
        bundle.putString("jk", data.jk.toString())
        bundle.putString("tempat_lahir", data.tempat_lahir.toString())
        bundle.putString("tanggal_lahir", data.tanggal_lahir.toString())
        bundle.putString("agama", data.agama.toString())
        bundle.putString("jurusan", data.jurusan.toString())
        bundle.putString("alamat", data.alamat.toString())
        bundle.putString("nohp", data.nohp.toString())
        bundle.putString("email", data.email.toString())
        bundle.putString("gambar", data.gambar.toString())
        bundle.putString("jml_kls_siswa", data.jml_kls_siswa.toString())

        bottomNavigationView = findViewById(R.id.bottom_nav)
        menu = bottomNavigationView.menu

        fragmentHome =  BerandaFragment(
            onClickTugas = {
                callFragment(2, fragmentTugas)
            }
        )
        active = fragmentHome

        fragmentHome.arguments = bundle
        fragmentCari.arguments = bundle
        fragmentTugas.arguments = bundle
        fragmentProf.arguments = bundle

        fm.beginTransaction().add(R.id.contenview, fragmentHome).show(fragmentHome).commit()
        fm.beginTransaction().add(R.id.contenview, fragmentCari).hide(fragmentCari).commit()
        fm.beginTransaction().add(R.id.contenview, fragmentTugas).hide(fragmentTugas).commit()
        fm.beginTransaction().add(R.id.contenview, fragmentProf).hide(fragmentProf).commit()

        menuItem = menu.getItem(0)
        menuItem.isChecked = true

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    callFragment(0, fragmentHome)
                }
                R.id.nav_cari -> {
                    callFragment(1, fragmentCari)
                }
                R.id.nav_tugas -> {
                    callFragment(2, fragmentTugas)
                }
                R.id.nav_profil -> {
                    callFragment(3, fragmentProf)
                }
            }
            false
        }

    }
    fun callFragment(int: Int , fragment: Fragment){

        menuItem = menu.getItem(int)
        menuItem.isChecked = true
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
    }
    override fun onStart() {
        super.onStart()
        if(!SharedPrefManager.getIntstance(this).isLoggedIn){
            val intent = Intent(this@MainActivity, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }


}
