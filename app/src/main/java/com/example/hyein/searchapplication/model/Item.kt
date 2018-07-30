package com.example.hyein.searchapplication.model

data class Item(var name: String,
                var description: String){
    override fun toString(): String {
        return "name : $name   description : $description"
    }
}