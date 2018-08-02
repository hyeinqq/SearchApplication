package com.example.hyein.searchapplication

import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.hyein.searchapplication.model.Item
import com.example.hyein.searchapplication.repository.ItemRepository
import com.example.hyein.searchapplication.viewmodel.ItemViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
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
//        val repository = mock(ItemRepository::class.java)
//        val viewModel = ItemViewModel(repository)
//        viewModel.filterList("desc", items)
//
//        val filterItems = ArrayList(listOf(Item("name", "description"),
//        Item("name", "descrip"), Item("name", "desc")))
//
//        assert(viewModel.filterList("desc", items).size == filterItems.size)

//        val viewModel = mock(ItemViewModel::class.java)
//        `when`(viewModel.items).thenReturn(items)
//
//        val viewModelItems = items

        val viewModel = ItemViewModel(null)
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