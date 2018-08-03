package com.example.hyein.searchapplication.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.hyein.searchapplication.model.Item
import com.example.hyein.searchapplication.model.Keyword
import com.example.hyein.searchapplication.repository.ItemRepository
import com.example.hyein.searchapplication.repository.KeywordRepository

class MainViewModel(private val itemRepository: ItemRepository?, private val keywordRepository: KeywordRepository) : ViewModel(){
    var items = ArrayList<Item>()
    private var resultItems : MutableLiveData<ArrayList<Item>>? = null

    init {
        itemRepository?.let { initItems()}
    }

    fun search(keywords: String){
        resultItems?.postValue(filterList(keywords))
    }

    fun filterList(keywords: String): ArrayList<Item>{
        return ArrayList<Item>().apply {
            for(keyword in keywords.split(' ')){
                this.addAll(items.filter{
                    it.description.toLowerCase().indexOf(keyword.toLowerCase()) > -1
                })
            }
        }
    }

    fun getKeywords() = keywordRepository.getKeywords()

    fun insertKeyword(keywords: String){
        for(keyword in keywords.split(' ')){
            if(keyword != "") {
                keywordRepository.addKeyword(Keyword(keyword))
            }
        }
    }


    fun getResultItems(): MutableLiveData<ArrayList<Item>> {
        if(resultItems == null){
            resultItems = MutableLiveData()
            resultItems?.value = items
        }
        return resultItems!!
    }

    private fun initItems(){
        resultItems?.postValue(items)
        itemRepository?.getItemServer(onSuccess = {
            items = it
            items.add(0,Item("테스트","테스트테스트테스트테스트테스트"))

            resultItems?.postValue(items)
        }, onError = {
            Log.i("TEST!", "onError???  $it")
        })
    }
}