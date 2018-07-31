package com.example.hyein.searchapplication.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.preference.PreferenceManager

class KeywordViewModel(application: Application): AndroidViewModel(application){
    private var keywordsLiveData = MutableLiveData<ArrayList<String>>()
    private var currentKeyword : String = ""

    init {
        keywordsLiveData.postValue(getLocalKeyword())
    }

    fun getCurrentKeywordList(): ArrayList<String>{
        return ArrayList(currentKeyword.split(' '))
    }

    fun setCurrentKeyword(keywords: String){
        currentKeyword = keywords
    }

    fun getKeywords(): MutableLiveData<ArrayList<String>> = keywordsLiveData

    fun saveLocalKeyword(keywords: String){
        val preferences = PreferenceManager.getDefaultSharedPreferences(getApplication())
        //preference 객체를 바로 넘겨서 추가하면 앱 재실행 시 저장이 안되어있다. 새롭게 객체를 생성해서 하면, 저장됨
        val newSet = HashSet<String>(preferences.getStringSet("keywords", HashSet<String>()))
        for(keyword in keywords.split(' ')){
            newSet.add(keyword)
            keywordsLiveData.value?.add(keyword)
            keywordsLiveData.postValue(keywordsLiveData.value)
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
}