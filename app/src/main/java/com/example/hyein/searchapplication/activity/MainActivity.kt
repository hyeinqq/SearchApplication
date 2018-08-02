package com.example.hyein.searchapplication.activity

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import com.example.hyein.searchapplication.R
import com.example.hyein.searchapplication.WebService
import com.example.hyein.searchapplication.adapter.ItemAdapter
import com.example.hyein.searchapplication.database.SearchDatabase
import com.example.hyein.searchapplication.database.SearchLocalCache
import com.example.hyein.searchapplication.databinding.ActivityMainBinding
import com.example.hyein.searchapplication.repository.ItemRepository
import com.example.hyein.searchapplication.viewmodel.ItemViewModel
import com.example.hyein.searchapplication.viewmodel.ItemViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity(), TextWatcher, View.OnKeyListener {
    val tag = "TEST!"

    private lateinit var itemViewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val itemRepository = ItemRepository(WebService.create(),
                SearchLocalCache(SearchDatabase.getDatabase(this).keywordDao(), Executors.newSingleThreadExecutor()))

        DataBindingUtil.setContentView<ActivityMainBinding>(this@MainActivity, R.layout.activity_main)
        itemViewModel = ViewModelProviders.of(this@MainActivity, ItemViewModelFactory(itemRepository)).get(ItemViewModel::class.java)

        initComponents()
        subscribe()
    }

    fun searchKeyword(view: View){
        val keywords : String = searchTextView.text.toString()

        itemViewModel.search(keywords)
        itemViewModel.insertKeyword(searchTextView.text.toString())
    }

    private fun initComponents(){
        recyclerView.apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ItemAdapter(getItem(), ArrayList<String>())
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        }

        searchTextView.let {
            it.addTextChangedListener(this)
            it.setOnKeyListener(this)
            it.setAdapter(ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, itemViewModel.getKeywords().value))
        }
    }

    private fun getCurrentKeywordList(): ArrayList<String>{
        return  ArrayList<String>(searchTextView.text.toString().split(' '))
    }

    private fun subscribe(){
        itemViewModel.getResultItems().observe(this@MainActivity, Observer {
            it?.let {
                recyclerView.adapter = ItemAdapter(it, getCurrentKeywordList())
            }
        })

        itemViewModel.getKeywords().observe(this@MainActivity, Observer {
            searchTextView.setAdapter(ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, it))
        })
    }

    private fun getItem() = itemViewModel.getResultItems().value!!

    //implements OnKeyListener
    override fun onKey(view: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if(event?.action == KeyEvent.ACTION_DOWN &&
                keyCode == KeyEvent.KEYCODE_ENTER){
            val imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(searchTextView.windowToken, 0)

            itemViewModel.insertKeyword(searchTextView.text.toString())
            return true
        }
        return false
    }

    //implements textWatcher
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        searchTextView.showDropDown()
    }
    override fun afterTextChanged(p0: Editable?) {
        itemViewModel.search(p0.toString())
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

}
