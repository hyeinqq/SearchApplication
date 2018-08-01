package com.example.hyein.searchapplication.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.hyein.searchapplication.repository.ItemRepository

class ItemViewModelFactory(private val repository: ItemRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ItemViewModel(repository) as T
    }
}