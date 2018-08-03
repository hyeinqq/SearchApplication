package com.example.hyein.searchapplication.repository

import com.example.hyein.searchapplication.WebService
import com.example.hyein.searchapplication.database.KeywordDao
import com.example.hyein.searchapplication.model.Item
import com.example.hyein.searchapplication.model.Keyword
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors


class ItemRepository(private val webService: WebService, private val keywordDao: KeywordDao) {
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
        Executors.newSingleThreadExecutor().execute {
            keywordDao.insert(keyword)
        }
    }

    fun getKeywords() = keywordDao.getKeywordStrings()



}