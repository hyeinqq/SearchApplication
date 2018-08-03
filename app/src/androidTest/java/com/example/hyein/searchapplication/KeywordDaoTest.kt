package com.example.hyein.searchapplication

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.text.method.MetaKeyKeyListener
import com.example.hyein.searchapplication.database.KeywordDao
import com.example.hyein.searchapplication.database.SearchDatabase
import com.example.hyein.searchapplication.model.Keyword
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class KeywordDaoTest{

    inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

    lateinit var database: SearchDatabase
    lateinit var keywordDao: KeywordDao

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

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
        val keyword = Keyword("testCase")
        keywordDao.insert(keyword)

        val keywords =  keywordDao.getAll()
        assertEquals(keywords.size, 1)
    }

    @Test
    fun addDuplicatedKeyword(){
        val keyword = Keyword("testCase")
        keywordDao.insert(keyword)
        keywordDao.insert(keyword)

        val keyword2 = Keyword("testCase")
        keywordDao.insert(keyword2)

        val keywords =  keywordDao.getAll()

        assertEquals(keywords.size, 1)
    }


    @Test
    fun getKeywordStrings(){
        val mockObserver = mock<Observer<List<String>>>()
        keywordDao.getKeywordStrings().observeForever(mockObserver)

        val keywordStrings = ArrayList(listOf("testCase"))

        val keyword = Keyword("testCase")
        keywordDao.insert(keyword)

        Mockito.verify(mockObserver).onChanged(keywordStrings)
    }
}