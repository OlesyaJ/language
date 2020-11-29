package com.example.language.content.network

import com.example.language.content.data.TextMessageResponse
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @GET("chat/start_phrase/")
    fun getStartPhrase(): Single<TextMessageResponse>

    @POST("chat/answer/")
    fun getAnswer(@Body body: TextMessageResponse): Single<Response<ResponseBody>>
}
