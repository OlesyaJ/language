package com.example.language.content.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiImpl {

    val api: Api

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://tanya-16914764.localhost.run/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

        api = retrofit.create(Api::class.java)
    }
}
