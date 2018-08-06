package com.example.hyein.searchapplication

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.example.hyein.searchapplication.model.Item
import com.example.hyein.searchapplication.repository.ItemRepository
import com.example.hyein.searchapplication.repository.KeywordRepository
import com.example.hyein.searchapplication.viewmodel.MainViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import kotlin.collections.ArrayList

@RunWith(MockitoJUnitRunner::class)
class ItemUnitTest{
    lateinit var items:ArrayList<Item>

    inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

    fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> uninitialized(): T = null as T

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val itemRepository = mock(ItemRepository::class.java)
    val keywordRepository = mock(KeywordRepository::class.java)

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

        @Suppress("UNCHECKED_CAST")
        doAnswer { invocation ->
            val callback = invocation.arguments[0] as (ArrayList<Item>) -> Unit
            callback(items)
        }.`when`(itemRepository).getItemServer(any(), any())
    }


    @Test
    fun search(){
        val viewModel = MainViewModel(itemRepository, keywordRepository)
        val mockObserver = mock<Observer<ArrayList<Item>>>()
        viewModel.getResultItems().observeForever(mockObserver)

        val filterItems = ArrayList(listOf(Item("name", "description"),
                Item("name", "descrip"), Item("name", "desc")))

        viewModel.search("desc")

        Mockito.verify(mockObserver).onChanged(filterItems)
    }

    @Test
    fun getKeywords(){
        val viewModel = MainViewModel(itemRepository, keywordRepository)

        val stringList = ArrayList(listOf("testCase", "test", "cheese"))
        val keywordStrings = MutableLiveData<List<String>>().apply {
            value = stringList
        }

        `when`(keywordRepository.getKeywords()).thenReturn(keywordStrings)
        assert(viewModel.getKeywords().value?.size == 3)

    }


    @Test
    fun insertKeyword(){
        val viewModel = MainViewModel(itemRepository, keywordRepository)
        viewModel.insertKeyword("keyword keyword")

    }


}