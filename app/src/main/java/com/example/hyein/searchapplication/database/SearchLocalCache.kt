package com.example.hyein.searchapplication.database

import android.arch.lifecycle.LiveData
import com.example.hyein.searchapplication.model.Keyword
import java.util.concurrent.Executor

class SearchLocalCache(
        private val keywordDao: KeywordDao,
        private val ioExecutor: Executor
){
    fun insert(keyword: Keyword, insertFinished: () -> Unit){
        ioExecutor.execute{
            keywordDao.insert(keyword)
            insertFinished()
        }
    }

    fun getStrings(): LiveData<List<String>> = keywordDao.getKeywordStrings()
}