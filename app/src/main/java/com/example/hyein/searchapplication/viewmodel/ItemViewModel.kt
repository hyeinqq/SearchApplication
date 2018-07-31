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

    fun saveLocalKeyword(keywords: String){
        val preferences = PreferenceManager.getDefaultSharedPreferences(getApplication())
        //preference 객체를 바로 넘겨서 추가하면 앱 재실행 시 저장이 안되어있다. 새롭게 객체를 생성해서 하면, 저장됨
        val newSet = HashSet<String>(preferences.getStringSet("keywords", HashSet<String>()))
        for(keyword in keywords.split(' ')){
            newSet.add(keyword)
        }
        val edit = preferences.edit()
        edit.putStringSet("keywords", newSet).apply() //{ commit() }
        edit.commit()
    }

    fun getLocalKeyword(): ArrayList<String> {
        val preferences = PreferenceManager.getDefaultSharedPreferences(getApplication())
        val set: MutableSet<String> = preferences.getStringSet("keywords", HashSet<String>())
        val returnValue = ArrayList<String>()
        set.forEach { returnValue.add(it) }
        return returnValue
    }

    override fun onCleared() {
        super.onCleared()
    }
}