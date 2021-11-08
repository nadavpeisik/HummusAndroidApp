package com.hachitovhummus.androidapp.model

data class OrderCollection(
    var orders: List<Order>,
    var takeOut: Boolean,
    var userName: String,
    var timeOfArrival: String,
)