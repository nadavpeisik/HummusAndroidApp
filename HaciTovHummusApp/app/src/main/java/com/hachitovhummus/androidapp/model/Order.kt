package com.hachitovhummus.androidapp.model

class Order(var dish: Dish = Dish(), var hasSmallSalad: Boolean = false, var drink: Drink = Drink.NONE,
            var price: Int = 25, var costumerComment: String = "") {
}


