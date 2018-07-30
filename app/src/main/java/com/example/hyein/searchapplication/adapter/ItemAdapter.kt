package com.example.hyein.searchapplication.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hyein.searchapplication.R
import com.example.hyein.searchapplication.databinding.ItemContentBinding
import com.example.hyein.searchapplication.model.Item

class ItemAdapter(private val itemList : ArrayList<Item>): RecyclerView.Adapter<ItemAdapter.ViewHolder>(){

    class ViewHolder(private val binding: ItemContentBinding): RecyclerView.ViewHolder(binding.root){
        fun bindTo(item: Item){
            Log.i("ITEM ", ""+item)
            binding.item = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_content, parent, false))
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(itemList[position])
    }
}