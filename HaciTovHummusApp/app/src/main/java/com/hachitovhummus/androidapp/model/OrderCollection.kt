package com.hachitovhummus.androidapp.model

import androidx.compose.runtime.mutableStateListOf

data class OrderCollection(var takeOut: Boolean, var timeOfArrival: String = ""){
    var orders = mutableStateListOf<Order>()
    lateinit var userName: String
    //lateinit var timeOfArrival: String

    fun checkUserNameInit(): Boolean{
        if (this::userName.isInitialized){
            if(userName != ""){
                return true
            }
        }

        return false
    }
}