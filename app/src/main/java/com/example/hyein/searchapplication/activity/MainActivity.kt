package com.example.hyein.searchapplication.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.hyein.searchapplication.adapter.ItemAdapter
import com.example.hyein.searchapplication.R
import com.example.hyein.searchapplication.model.Item

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        recyclerView.adapter = ItemAdapter(getItem())
    }

    private fun getItem() : ArrayList<Item>{
        val item = Item("test","description")
        val list : ArrayList<Item> = ArrayList()
        list.add(item)
        list.add(Item("test","kkkkk"))
        list.add(item)
        list.add(item)

        return list
    }
}
