package com.example.hyein.searchapplication.adapter


import android.databinding.DataBindingUtil
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.hyein.searchapplication.R
import com.example.hyein.searchapplication.databinding.ItemContentBinding
import com.example.hyein.searchapplication.model.Item

class ItemAdapter(private val itemList : ArrayList<Item>, private val keywordList: ArrayList<String>): RecyclerView.Adapter<ItemAdapter.ViewHolder>(){

    class ViewHolder(private val binding: ItemContentBinding, private val keywordList: ArrayList<String>): RecyclerView.ViewHolder(binding.root){
        fun bindTo(item: Item){
            binding.item = item

            val spannable: Spannable = SpannableString(item.description)
            for(keyword in keywordList){
                val startIndex = item.description.indexOf(keyword)
                if(startIndex > -1){
                    spannable.setSpan(ForegroundColorSpan(Color.RED), startIndex, startIndex+keyword.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    binding.itemTextView.setText(spannable, TextView.BufferType.SPANNABLE)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_content, parent, false), keywordList)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(itemList[position])
    }
}