package com.example.hyein.searchapplication

import android.content.ClipData
import com.example.hyein.searchapplication.model.Item
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface WebService{
    @GET("cheeses")
    fun getItems(): Call<ArrayList<Item>>

    companion object Factory {
        private const val BASE_URL: String = "http://192.168.4.132:5000/"

        fun create(): WebService{
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(WebService::class.java)
        }
    }
}