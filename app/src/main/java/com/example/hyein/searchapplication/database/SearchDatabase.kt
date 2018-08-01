package com.example.hyein.searchapplication.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.hyein.searchapplication.model.Keyword

@Database(entities = [Keyword::class], version = 1, exportSchema = false)
abstract class SearchDatabase: RoomDatabase(){
    abstract fun keywordDao(): KeywordDao

    companion object {
        private var INSTANCE : SearchDatabase? = null

        fun getDatabase(context: Context): SearchDatabase{
            if (INSTANCE == null){
                synchronized(SearchDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context, SearchDatabase::class.java, "Search.db").build()
                }
            }
            return INSTANCE!!
        }
    }
}

