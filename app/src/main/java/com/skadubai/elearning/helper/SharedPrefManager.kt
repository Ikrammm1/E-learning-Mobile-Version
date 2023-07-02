package com.skadubai.elearning.helper

import android.content.Context
import android.content.SharedPreferences
import com.skadubai.elearning.model.ModelProfile
import com.skadubai.elearning.model.PayloadLogin

class SharedPrefManager private constructor(private  val mCtx: Context){

    val isLoggedIn: Boolean
        get() {
            val  sharedPreferences =mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString("NISN", null)!= null
        }

    val user: PayloadLogin
        get() {
            val  sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return PayloadLogin(
                sharedPreferences.getString("id_user", null),
                sharedPreferences.getString("username", null),
                sharedPreferences.getString("password", null),
                sharedPreferences.getString("role_id", null),
                sharedPreferences.getString("NISN", null),
                sharedPreferences.getString("nama_siswa", null),
                sharedPreferences.getString("jk", null),
                sharedPreferences.getString("tempat_lahir", null),
                sharedPreferences.getString("tanggal_lahir", null),
                sharedPreferences.getString("agama", null),
                sharedPreferences.getString("jurusan", null),
                sharedPreferences.getString("alamat", null),
                sharedPreferences.getString("nohp", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("gambar", null),
                sharedPreferences.getString("jml_kls_siswa", null)


            )

        }

    fun saveUser(user: PayloadLogin){
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("id_user", user.id_user)
        editor.putString("username", user.username)
        editor.putString("password", user.password)
        editor.putString("role_id", user.role_id)
        editor.putString("NISN", user.NISN)
        editor.putString("nama_siswa", user.nama_siswa)
        editor.putString("jk", user.jk)
        editor.putString("tempat_lahir", user.tempat_lahir)
        editor.putString("tanggal_lahir", user.tanggal_lahir)
        editor.putString("agama", user.agama)
        editor.putString("jurusan", user.jurusan)
        editor.putString("alamat", user.alamat)
        editor.putString("nohp", user.nohp)
        editor.putString("email", user.email)
        editor.putString("gambar", user.gambar)
        editor.putString("jml_kls_siswa", user.jml_kls_siswa)

        editor.apply()
    }

    val datauser : ModelProfile
    get() {
        val  sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

        return ModelProfile(
            sharedPreferences.getString("NISN", null),
            sharedPreferences.getString("nama_siswa", null),
            sharedPreferences.getString("jk", null),
            sharedPreferences.getString("tempat_lahir", null),
            sharedPreferences.getString("tanggal_lahir", null),
            sharedPreferences.getString("agama", null),
            sharedPreferences.getString("jurusan", null),
            sharedPreferences.getString("alamat", null),
            sharedPreferences.getString("nohp", null),
            sharedPreferences.getString("email", null),
            sharedPreferences.getString("gambar", null)
        )

    }

    fun savedatauser(datauser : ModelProfile){
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("NISN", datauser.NISN)
        editor.putString("nama_siswa",datauser.nama_siswa)
        editor.putString("jk", datauser.jk)
        editor.putString("tempat_lahir", datauser.tempat_lahir)
        editor.putString("tanggal_lahir", datauser.tanggal_lahir)
        editor.putString("agama", datauser.agama)
        editor.putString("jurusan", datauser.jurusan)
        editor.putString("alamat", datauser.alamat)
        editor.putString("nohp", datauser.nohp)
        editor.putString("email", datauser.email)
        editor.putString("gambar", datauser.gambar)

        editor.apply()
    }


    fun clear(){
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private val SHARED_PREF_NAME = "my_shared_preff"
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getIntstance(mCtx: Context): SharedPrefManager{
            if (mInstance == null){
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }

}