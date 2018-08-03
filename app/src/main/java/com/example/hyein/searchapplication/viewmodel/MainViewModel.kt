package com.example.hyein.searchapplication.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.hyein.searchapplication.model.Item
import com.example.hyein.searchapplication.model.Keyword
import com.example.hyein.searchapplication.repository.ItemRepository
import com.example.hyein.searchapplication.repository.KeywordRepository

class MainViewModel(private val itemRepository: ItemRepository, private val keywordRepository: KeywordRepository) : ViewModel(){
    private var items = ArrayList<Item>()
    private var resultItems = MutableLiveData<ArrayList<Item>>()

    init {
        initItems()
    }

    fun search(keywords: String){
        resultItems.postValue(filterList(keywords))
    }

    private fun filterList(keywords: String): ArrayList<Item>{
        return ArrayList<Item>().apply {
            for(keyword in keywords.split(' ')){
                this.addAll(items.filter{
                    this.indexOf(it) == -1  &&
                    it.description.toLowerCase().indexOf(keyword.toLowerCase()) > -1
                })
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun getKeywords() = keywordRepository.getKeywords() as LiveData<ArrayList<String>>


    fun insertKeyword(keywords: String){
        for(keyword in keywords.split(' ')){
            if(keyword != "") {
                keywordRepository.addKeyword(Keyword(keyword))
            }
        }
    }


    fun getResultItems() = resultItems


    private fun initItems(){
        resultItems.postValue(items)
        itemRepository.getItemServer(onSuccess = {
            items = it
            items.add(0,Item("테스트","테스트테스트테스트테스트테스트"))

            resultItems.postValue(items)
        }, onError = {
            Log.i("TEST!", "onError???  $it")
        })
    }
}