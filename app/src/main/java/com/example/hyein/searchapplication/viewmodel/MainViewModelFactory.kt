package com.example.hyein.searchapplication.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.hyein.searchapplication.repository.ItemRepository
import com.example.hyein.searchapplication.repository.KeywordRepository

class MainViewModelFactory(private val itemRepository: ItemRepository, private val keywordRepository: KeywordRepository): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(itemRepository, keywordRepository) as T
    }
}