package com.example.hyein.searchapplication

import android.util.Log
import com.example.hyein.searchapplication.model.Item
import com.example.hyein.searchapplication.repository.KeywordRepository
import com.example.hyein.searchapplication.viewmodel.MainViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ItemUnitTest{
    lateinit var items:ArrayList<Item>

    @Before
    fun setUp(){
        items = ArrayList()
        items.add(Item("name", "description"))
        items.add(Item("name", "descrip"))
        items.add(Item("name", "desc"))
        items.add(Item("name", "test"))
        items.add(Item("name", "elevator"))
        items.add(Item("name", "suran"))
        items.add(Item("name", "fan"))

        Log.i("TEST!", "여기 먼저 오나???")
    }


    @Test
    fun getIncludeKeywordItem(){
        val keywordRepository = mock(KeywordRepository::class.java)
        val viewModel = MainViewModel(null, keywordRepository)
        viewModel.items = items
        val filterItems = ArrayList(listOf(Item("name", "description"),
                Item("name", "descrip"), Item("name", "desc")))

        assert(viewModel.filterList("desc").size == filterItems.size)
    }

    @Test
    fun addItem(){
        val item = Item("test", "description")
        items.add(item)
        assert(item.description.equals("description"))
    }


}