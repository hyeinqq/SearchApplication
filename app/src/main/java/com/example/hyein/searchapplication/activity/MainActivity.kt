package com.example.hyein.searchapplication.activity

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.hyein.searchapplication.adapter.ItemAdapter
import com.example.hyein.searchapplication.R
import com.example.hyein.searchapplication.databinding.ActivityMainBinding
import com.example.hyein.searchapplication.model.Item
import com.example.hyein.searchapplication.viewmodel.ItemViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TextWatcher, View.OnKeyListener {
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
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        }

        mainBinding.editText.addTextChangedListener(this)
        mainBinding.editText.setOnKeyListener(this)

        subscribe()
    }

    fun searchKeyword(view: View){
        Log.i(TAG, "search keyword "+  mainBinding.editText.text)
        val keyword : String = mainBinding.editText.text.toString()

        itemViewModel.search(keyword)
        //TODO 검색기록에 저장하기
    }

    private fun subscribe(){
        val searchObserver: Observer<ArrayList<Item>> = Observer {
            mainBinding.recyclerView.adapter = ItemAdapter(it!!)
        }
        itemViewModel.getResultItems().observe(this@MainActivity, searchObserver)
    }

    private fun getItem() : ArrayList<Item>{
        return itemViewModel.getResultItems().value!!
    }

    //implements OnKeyListener
    override fun onKey(view: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if(event?.action == KeyEvent.ACTION_DOWN &&
                keyCode == KeyEvent.KEYCODE_ENTER){
            val imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(mainBinding.editText.windowToken, 0)

            //TODO 검색기록에 저장하기
            return true
        }
        return false
    }

    //implements textWatcher
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
    override fun afterTextChanged(p0: Editable?) {
        Log.i(TAG, "editable ?? "+p0.toString())
        itemViewModel.search(p0.toString())
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

}
