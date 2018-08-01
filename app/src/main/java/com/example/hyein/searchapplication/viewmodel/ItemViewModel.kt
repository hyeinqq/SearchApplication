package com.example.hyein.searchapplication.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.hyein.searchapplication.model.Item
import com.example.hyein.searchapplication.model.Keyword
import com.example.hyein.searchapplication.repository.ItemRepository

class ItemViewModel(private val repository: ItemRepository?) : ViewModel(){ //ViewModel(){
    var items = ArrayList<Item>()
    private var resultItems : MutableLiveData<ArrayList<Item>>? = null

    init {
        repository?.let { initItems()}
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

    fun getKeywords(): LiveData<ArrayList<String>> = repository?.getKeywords()!!

    fun insertKeyword(keywords: String){
        for(keyword in keywords.split(' ')){
            if(keyword != "") {
                repository?.addKeyword(Keyword(keyword))
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
        repository?.getItem(onSuccess = {
            items = it
            items.add(0,Item("테스트","테스트테스트테스트테스트테스트"))

            resultItems?.postValue(items)
        }, onError = {
            Log.i("TEST!", "onError???  $it")
        })
    }

    override fun onCleared() {
        super.onCleared()
    }
}