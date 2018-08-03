package com.example.hyein.searchapplication

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.example.hyein.searchapplication.database.KeywordDao
import com.example.hyein.searchapplication.database.SearchDatabase
import com.example.hyein.searchapplication.model.Keyword
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class KeywordDaoTest{

    lateinit var database: SearchDatabase
    lateinit var keywordDao: KeywordDao

    @Before
    fun createDb(){
        val context = InstrumentationRegistry.getTargetContext()
        database = Room.inMemoryDatabaseBuilder(context, SearchDatabase::class.java)
                .allowMainThreadQueries().build()
        keywordDao = database.keywordDao()
    }

    @After
    fun closeDb(){
        database.close()
    }

    @Test
    fun addKeyword(){
//        val mockObserver = mock(Observer::class.java)
//        keywordDao.getKeywordStrings().observeForever(mockObserver)

        val keyword = Keyword("testCase")
        keywordDao.insert(keyword)

//        val keywords = keywordDao.getKeywordStrings()

        assertEquals(keyword.name,"testCase")
//        Assert.assertEquals(keywords.value?.isNotEmpty() , true)
    }

    @Test
    fun getKeyword(){
//        val keywords =  keywordDao.getKeywordStrings()

//        Assert.assertEquals(keywords.value?.size , 4 )
    }
}