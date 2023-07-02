package com.skadubai.elearning.login

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.skadubai.elearning.API.RetrofitClient
import com.skadubai.elearning.model.ResponseLogin
import com.skadubai.elearning.API.RetrofitLogin
import com.skadubai.elearning.MainActivity
import com.skadubai.elearning.R
import com.skadubai.elearning.helper.Constant
import com.skadubai.elearning.helper.SharedPrefManager
import com.skadubai.elearning.model.ModelProfile
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tv_username = findViewById<TextView>(R.id.edt_username)
        val tv_password = findViewById<TextView>(R.id.edt_pass)
        val btn_login = findViewById<TextView>(R.id.btn_signin)



        btn_login.setOnClickListener {
            val username = tv_username.text.toString().trim()
            val  password = tv_password.text.toString().trim()
            when{
                username == "" ->{
                    tv_username.error = "Email Tidak Boleh Kosong!"
                }
                password == "" -> {
                    tv_password.error = "Password Tidak Boleh Kosong!"
                }
                else -> {
                    RetrofitClient.instance.login(username, password).enqueue(object  : Callback<ResponseLogin>{
                        override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                            if (response.body() != null && response.isSuccessful() && response.body()?.response == true) {
                                SharedPrefManager.getIntstance(applicationContext)
                                    .saveUser(response.body()?.payload!!)
                                Toast.makeText(applicationContext, response.body()?.status, Toast.LENGTH_LONG)
                                    .show()
                                val intent = Intent(this@Login, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                finish()

                            }
                        }

                        override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        }

                    })
                }
            }
        }


}






//    private fun getUser() {
//        val api = RetrofitLogin().getInstance()
//        api.login(username, password).enqueue(object :Callback<ResponseLogin>{
//            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
//                if(response.isSuccessful){
//
//                    //fungsi session
//                    getSharedPreferences("Login_Session", MODE_PRIVATE)
//                        .edit()
//                        .putString("id_user", response.body()?.payload?.id_user)
//                        .putString("username", response.body()?.payload?.username)
//                        .putString("password", response.body()?.payload?.password)
//                        .putString("role_id", response.body()?.payload?.role_id)
//                        .apply()
//
//                    if(response.body()?.response == true){
//                        startActivity(Intent(this@Login, MainActivity::class.java)
//                        )
//                        finish()
////                        val kedashboard = Intent (this@LoginActivity, Dashboard::class.java)
////                          startActivity(kedashboard)
//                    }else{
//                        gagallog.visibility = View.VISIBLE
//                        Toast.makeText(this@Login, "GAGAL", Toast.LENGTH_SHORT)
//                    }
//                } else {
//                    Toast.makeText(this@Login, "Kesalahan", Toast.LENGTH_SHORT)
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })
//
//
//    }
}
