package com.example.hyein.searchapplication.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.preference.PreferenceManager
import com.example.hyein.searchapplication.model.Item
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ItemViewModel(application: Application):AndroidViewModel(application){ //ViewModel(){
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


    fun getResultItems(): MutableLiveData<ArrayList<Item>> {
        if(resultItems == null){
            resultItems = MutableLiveData()
            resultItems?.value = items
        }
        return resultItems!!
    }

    fun initItems(){
        val jsonString: String = getApplication<Application>().assets.open("item.json").bufferedReader().use { it.readText() }

        items = Gson().fromJson(jsonString, object : TypeToken<ArrayList<Item>>(){}.type)
        items.add(Item("테스트","테스트테스트테스트테스트테스트"))
    }

    override fun onCleared() {
        super.onCleared()
    }
}