package com.example.hyein.searchapplication.database

import android.arch.lifecycle.LiveData
import com.example.hyein.searchapplication.model.Keyword
import java.util.concurrent.Executor
import javax.security.auth.callback.Callback

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

//    fun getStrings(callback: (List<String>) -> Unit){
//        ioExecutor.execute{
//            callback(keywordDao.getKeywordStrings())
//        }
//    }
    fun getStrings(): LiveData<List<String>> = keywordDao.getKeywordStrings()
}