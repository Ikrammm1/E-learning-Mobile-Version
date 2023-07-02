package com.skadubai.elearning.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import com.skadubai.elearning.R
import com.skadubai.elearning.helper.SharedPrefManager
import com.skadubai.elearning.login.Login
import com.skadubai.elearning.model.ModelProfile
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {
    lateinit var s: SharedPrefManager
    lateinit var btnLogout:Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)

        val txtnama : TextView = view.findViewById(R.id.txtNama)
        val txtnisn : TextView = view.findViewById(R.id.txtNisn)
        val txtjurusan : TextView = view.findViewById(R.id.txtJurusan)
        val txtemail : TextView = view.findViewById(R.id.txtEmail)
        val txtnohp: TextView = view.findViewById(R.id.txtNohp)
        val txttempat : TextView = view.findViewById(R.id.txtTmptlahit)
        val txttgllahir : TextView = view.findViewById(R.id.txtTgllahir)
        val txtagama : TextView = view.findViewById(R.id.txtAgama)
        val txtalamat : TextView = view.findViewById(R.id.txtAlamat)
        val fotoprof : ImageView = view.findViewById(R.id.img_profile)


        val args = this.arguments
        val nama = args?.get("nama_siswa")
        val nisn = args?.get("NISN")
        val jurusan = args?.get("jurusan")
        val email = args?.get("email")
        val nohp = args?.get("nohp")
        val tempat = args?.get("tempat_lahir")
        val tgllahir= args?.get("tanggal_lahir")
        val agama = args?.get("agama")
        val alamat = args?.get("alamat")
        val image = args?.get("gambar")
        val urlFoto = "https://elearning.smkn2barabai.sch.id/$image"
//        val urlFoto = "http://192.168.100.151/$image"
        Picasso.get().load(urlFoto).into(fotoprof)

        Log.d("Image", image.toString())

        txtnama.text = nama.toString()
        txtnisn.text = nisn.toString()
        txtjurusan.text = jurusan.toString()
        txtemail.text = email.toString()
        txtnohp.text = nohp.toString()
        txttempat.text = tempat.toString()
        txttgllahir.text = tgllahir.toString()
        txtagama.text = agama.toString()
        txtalamat.text = alamat.toString()



        s = SharedPrefManager.getIntstance(requireActivity())

        btnLogout = view.findViewById(R.id.btn_Logout)
        btnLogout.setOnClickListener(){
            s.clear()
            val intent = Intent(activity, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return view
    }



}
