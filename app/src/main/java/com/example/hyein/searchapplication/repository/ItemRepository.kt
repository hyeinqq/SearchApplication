package com.example.hyein.searchapplication.repository

import com.example.hyein.searchapplication.WebService
import com.example.hyein.searchapplication.model.Item
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ItemRepository(private val webService: WebService) {
    fun getItemServer(onSuccess: (items: ArrayList<Item>)-> Unit,
                onError: (error: String) -> Unit){
        webService.getItems().enqueue(object :Callback<ArrayList<Item>>{
            override fun onFailure(call: Call<ArrayList<Item>>?, t: Throwable?) {
                onError("error ::: "+ t.toString())
            }

            override fun onResponse(call: Call<ArrayList<Item>>?, response: Response<ArrayList<Item>>?) {
                response?.body()?.let {
                    onSuccess(it) }
            }
        })
    }
}