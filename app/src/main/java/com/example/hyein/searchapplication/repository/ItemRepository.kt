package com.example.hyein.searchapplication.repository

import android.arch.lifecycle.LiveData
import com.example.hyein.searchapplication.WebService
import com.example.hyein.searchapplication.database.SearchLocalCache
import com.example.hyein.searchapplication.model.Item
import com.example.hyein.searchapplication.model.Keyword
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ItemRepository(private val webService: WebService, private val cache: SearchLocalCache) {
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


    fun addKeyword(keyword: Keyword){
        cache.insert(keyword = keyword, insertFinished = {})
    }

    fun getKeywords() = cache.getStrings()

}