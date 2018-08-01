package com.example.hyein.searchapplication.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "keywords")
data class Keyword(
        @PrimaryKey @field:SerializedName("name") var name:String) {
}