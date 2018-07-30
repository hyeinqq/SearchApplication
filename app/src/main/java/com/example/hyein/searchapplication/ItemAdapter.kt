package com.example.hyein.searchapplication

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.hyein.searchapplication.model.Item

class ItemAdapter(private val itemList : ArrayList<Item>): RecyclerView.Adapter<ItemAdapter.ViewHolder>(){

//    class ViewHolder(private val binding: ItemContentBinding): RecyclerView.ViewHolder(binding.root){
//        fun bindTo(item: Item){
//            Log.i("ITEM ", ""+item)
////            binding.item = item
//        }
//    }

    class ViewHolder(view :View): RecyclerView.ViewHolder(view){
        private val itemTextView = view.findViewById<TextView>(R.id.itemTextView)
        fun bindTo(item: Item){
            Log.i("ITEM ", ""+item)
            itemTextView.text = item.description
//            binding.item = item
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_content, parent, false)
        return ViewHolder(view)
//        return ViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_content, parent, false))
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        holder.bindTo(itemList[position])
    }
}