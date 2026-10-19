package com.felix.guotai.remote.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteRetrofit {
    companion object{
        fun loadDeveloper(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://openapi.twse.com.tw/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}