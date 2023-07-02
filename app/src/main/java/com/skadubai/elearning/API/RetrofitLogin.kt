package com.skadubai.elearning.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitLogin {
    private fun getRetrofitLogin(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://elearning.smkn2barabai.sch.id/elearningskadubai/API/")
//            .baseUrl("http://192.168.100.151/elearningskadubai/API/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getInstance() : ApiService{
        return getRetrofitLogin().create(ApiService::class.java)
    }
}