package com.example.hyein.searchapplication.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.preference.PreferenceManager
import android.util.Log
import com.example.hyein.searchapplication.model.Item
import com.example.hyein.searchapplication.model.Keyword
import com.example.hyein.searchapplication.repository.ItemRepository

class ItemViewModel(private val repository: ItemRepository) : ViewModel(){ //ViewModel(){
    private var items = ArrayList<Item>()
    private var resultItems : MutableLiveData<ArrayList<Item>>? = null

    init {
        initItems()
    }

    fun search(keywords: String){
        val tmpItems = ArrayList<Item>()
        for(keyword in keywords.split(' ')){
            tmpItems.addAll(items.filter{
                it.description.toLowerCase().indexOf(keyword.toLowerCase()) > -1
            })
        }
        resultItems?.postValue(tmpItems)
    }


    fun getKeywords(): LiveData<ArrayList<String>> = repository.getKeywords()

    fun insertKeyword(keywords: String){
        for(keyword in keywords.split(' ')){
            repository.addKeyword(Keyword(keyword))
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
        repository.getItem(onSuccess = {
            items = it
            items.add(Item("테스트","테스트테스트테스트테스트테스트"))

            resultItems?.postValue(items)
        }, onError = {
            Log.i("TEST!", "onError???  $it")
        })
    }

    override fun onCleared() {
        super.onCleared()
    }
}