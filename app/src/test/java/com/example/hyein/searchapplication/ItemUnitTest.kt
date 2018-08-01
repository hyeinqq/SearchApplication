package com.example.hyein.searchapplication

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

//    @Mock
//    lateinit var itemViewModel: ItemViewModel

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
        val itemRepository = mock(ItemRepository::class.java)
        `when`(itemRepository.items).thenReturn(items)

        val viewModel = ItemViewModel(itemRepository)
        viewModel.items = itemRepository.items

        val filterItems = ArrayList(listOf(Item("name", "description"),
                Item("name", "descrip"), Item("name", "desc")))

        assert(viewModel.filterList("desc") == filterItems)
    }

    @Test
    fun getItem(){
        val item = Item("test", "description")
        items.add(item)

        assert(item.description.equals("description"))
    }
}