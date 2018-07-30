package com.example.hyein.searchapplication.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.hyein.searchapplication.adapter.ItemAdapter
import com.example.hyein.searchapplication.R
import com.example.hyein.searchapplication.databinding.ActivityMainBinding
import com.example.hyein.searchapplication.model.Item
import com.example.hyein.searchapplication.viewmodel.ItemViewModel

class MainActivity : AppCompatActivity() {
    val TAG:String = "TEST!"
    lateinit var mainBinding: ActivityMainBinding
    lateinit var itemViewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        itemViewModel = ViewModelProviders.of(this@MainActivity).get(ItemViewModel::class.java)

        mainBinding.recyclerView.apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ItemAdapter(getItem())
        }



        subscribe()
    }

    fun subscribe(){
        val searchObserver: Observer<ArrayList<Item>> = Observer {
            mainBinding.recyclerView.adapter = ItemAdapter(it!!)
        }
        itemViewModel.getResultItems().observe(this@MainActivity, searchObserver)
    }

    private fun getItem() : ArrayList<Item>{
        return itemViewModel.getResultItems().value!!
    }

    fun searchKeyword(view: View){
        Log.i(TAG, "search keyword "+  mainBinding.editText.text)
        val keyword : String = mainBinding.editText.text.toString()
        itemViewModel.search(keyword)
    }
}
