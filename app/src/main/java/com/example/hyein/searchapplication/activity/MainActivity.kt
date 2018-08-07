package com.example.hyein.searchapplication.activity

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import com.example.hyein.searchapplication.R
import com.example.hyein.searchapplication.WebService
import com.example.hyein.searchapplication.adapter.ItemAdapter
import com.example.hyein.searchapplication.database.SearchDatabase
import com.example.hyein.searchapplication.databinding.ActivityMainBinding
import com.example.hyein.searchapplication.repository.ItemRepository
import com.example.hyein.searchapplication.repository.KeywordRepository
import com.example.hyein.searchapplication.viewmodel.MainViewModel
import com.example.hyein.searchapplication.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), TextWatcher, View.OnKeyListener {
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val itemRepository = ItemRepository(WebService.create())
        val keywordRepository = KeywordRepository(SearchDatabase.getDatabase(this).keywordDao())

        DataBindingUtil.setContentView<ActivityMainBinding>(this@MainActivity, R.layout.activity_main)
        mainViewModel = ViewModelProviders.of(this@MainActivity, MainViewModelFactory(itemRepository,keywordRepository)).get(MainViewModel::class.java)

    }

    override fun onResume() {
        super.onResume()
        initComponents()
        subscribe()
    }

    fun searchKeyword(view: View){
        val keywords : String = searchTextView.text.toString()
// 이미 TextChanged가 불린 후이기 때문에 중복으로 부를 필요가 없다.
//        mainViewModel.search(keywords)
        mainViewModel.insertKeyword(keywords)
    }

    private fun initComponents(){
        recyclerView.apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ItemAdapter(getItem() ?: ArrayList(), ArrayList())
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        }

        searchTextView.run {
            addTextChangedListener(this@MainActivity)
            setOnKeyListener(this@MainActivity)
            //바로 value를 꺼내는건 위험!(빈 리스트를 넘기거나 해야함) adapter가 늦게 설정되도 된다면, 옵저버에서 그냥 처리
            setAdapter(ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_dropdown_item_1line,
                    mainViewModel.getKeywords().value ?: ArrayList()))
        }
    }

    private fun getCurrentKeywordList(): ArrayList<String>{
        return  ArrayList(searchTextView.text.toString().split(' '))
    }

    private fun subscribe(){
        mainViewModel.getResultItems().observe(this@MainActivity, Observer {
            it?.let {
                recyclerView.adapter = ItemAdapter(it, getCurrentKeywordList())
            }
        })

        mainViewModel.getKeywords().observe(this@MainActivity, Observer {
            searchTextView.setAdapter(ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, it))
        })
    }

    private fun getItem() = mainViewModel.getResultItems().value

    private fun hideKeyboard(){
        val imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchTextView.windowToken, 0)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboard()
        return super.dispatchTouchEvent(ev)
    }

    //implements OnKeyListener
    override fun onKey(view: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if(event?.action == KeyEvent.ACTION_DOWN &&
                keyCode == KeyEvent.KEYCODE_ENTER){
            hideKeyboard()

            mainViewModel.insertKeyword(searchTextView.text.toString())
            return true
        }
        return false
    }

    //implements textWatcher
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        searchTextView.showDropDown()
    }
    override fun afterTextChanged(p0: Editable?) {
        mainViewModel.search(p0.toString())
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

}
