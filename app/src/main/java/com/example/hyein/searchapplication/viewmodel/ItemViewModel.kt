package com.example.hyein.searchapplication.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.hyein.searchapplication.model.Item

class ItemViewModel:ViewModel(){
    private var items = ArrayList<Item>() //MutableLiveData<ArrayList<Item>>()
    private var resultItems : MutableLiveData<ArrayList<Item>>? = null

    init {
        initItems()
    }

    fun search(keyword: String){
         resultItems?.postValue(ArrayList(items.filter{
             it.description.indexOf(keyword) > -1
         }))
    }

    fun getResultItems(): MutableLiveData<ArrayList<Item>> {
        if(resultItems == null){
            resultItems = MutableLiveData()
            resultItems?.value = items
        }
        return resultItems!!
    }

    fun initItems(){
        val item = Item("test","description")
        items.add(item)
        items.add(Item("test","kkkkk"))
        items.add(item)
        items.add(item)
    }

    override fun onCleared() {
        super.onCleared()
    }
}