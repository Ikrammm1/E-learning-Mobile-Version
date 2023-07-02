package com.skadubai.elearning.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.skadubai.elearning.R
import com.skadubai.elearning.cari.Resultcari
import com.skadubai.elearning.helper.SharedPrefManager


class CariFragment : Fragment() {
    lateinit var s:SharedPrefManager

    lateinit var btnTKR : LinearLayout
    lateinit var btnMM : LinearLayout
    lateinit var btnATP : LinearLayout
    lateinit var btnATPH : LinearLayout
    lateinit var Bindo : TextView
    lateinit var MTK : TextView
    lateinit var Kimia : TextView
    lateinit var EdCari : SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_cari, container, false)

        btnTKR = view.findViewById(R.id.btntkr)
        btnMM = view.findViewById(R.id.btnmm)
        btnATP = view.findViewById(R.id.btnatp)
        btnATPH = view.findViewById(R.id.btnatph)
        Bindo =view.findViewById(R.id.bindo)
        MTK = view.findViewById(R.id.mtk)
        Kimia = view.findViewById(R.id.kimia)
        EdCari = view.findViewById(R.id.edCari)

        val args = this.arguments
        val username = args?.get("username")
        val NISN = username.toString()

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


        s = SharedPrefManager.getIntstance(requireActivity())


        btnTKR.setOnClickListener {
            val caribyjurusan = Intent(requireContext(), Resultcari::class.java)
                .putExtra("cari", "TKR")
                .putExtra("NISN", NISN)
            startActivity(caribyjurusan)
        }

        btnMM.setOnClickListener {
            val caribyjurusan = Intent(requireContext(), Resultcari::class.java)
                .putExtra("cari", "MM")
                .putExtra("NISN", NISN)
            startActivity(caribyjurusan)
        }
        btnATP.setOnClickListener {
            val caribyjurusan = Intent(requireContext(), Resultcari::class.java)
                .putExtra("cari", "ATP")
                .putExtra("NISN", NISN)
            startActivity(caribyjurusan)
        }
        btnATPH.setOnClickListener {
            val caribyjurusan = Intent(requireContext(), Resultcari::class.java)
                .putExtra("cari", "ATPH")
                .putExtra("NISN", NISN)
            startActivity(caribyjurusan)
        }
        Bindo.setOnClickListener {
            val caribyword = Intent(requireContext(), Resultcari::class.java)
                .putExtra("word", "Bahasa Indonesia")
                .putExtra("NISN", NISN)
            startActivity(caribyword)
        }
        MTK.setOnClickListener {
            val caribyword = Intent(requireContext(), Resultcari::class.java)
                .putExtra("word", "Matematika")
                .putExtra("NISN", NISN)
            startActivity(caribyword)
        }
        Kimia.setOnClickListener {
            val caribyword = Intent(requireContext(), Resultcari::class.java)
                .putExtra("word", "Kimia")
                .putExtra("NISN", NISN)
            startActivity(caribyword)
        }




        return view;
    }



}
