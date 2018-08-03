package com.example.hyein.searchapplication.repository

import com.example.hyein.searchapplication.database.KeywordDao
import com.example.hyein.searchapplication.model.Keyword
import java.util.concurrent.Executors

class KeywordRepository(private val keywordDao: KeywordDao){

    fun addKeyword(keyword: Keyword){
        Executors.newSingleThreadExecutor().execute {
            keywordDao.insert(keyword)
        }
    }

    fun getKeywords() = keywordDao.getKeywordStrings()

}