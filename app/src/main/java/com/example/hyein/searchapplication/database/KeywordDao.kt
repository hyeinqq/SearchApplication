package com.example.hyein.searchapplication.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.example.hyein.searchapplication.model.Item
import com.example.hyein.searchapplication.model.Keyword

@Dao
interface KeywordDao{
    @Insert(onConflict = REPLACE)
    fun insert(keyword: Keyword)

    @Query("SELECT * FROM keywords WHERE name = :name")
    fun get(name: String):Keyword

    @Query("SELECT * FROM keywords")
    fun getAll(): List<Keyword>

    @Query("SELECT name FROM keywords")
    fun getKeywordStrings(): LiveData<List<String>>

}