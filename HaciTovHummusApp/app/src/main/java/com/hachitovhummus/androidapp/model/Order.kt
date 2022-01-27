package com.hachitovhummus.androidapp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Order(var dish: Dish = Dish()) {
    var isBusinessMeal by mutableStateOf(false)
    var hasSmallSalad by mutableStateOf(false)
    var drink by mutableStateOf(Drink.NONE)
    var price by mutableStateOf(33)
    var costumerComment by mutableStateOf ("")
    //var favorite by mutableStateOf(false)

    fun calculatePrice(happyHour: Boolean){
        val basicPrice = 33
        val drinkPrice = 7
        val smallSaladPrice = 15

        when{
            dish.isSmallDish ->{
                price = if(hasSmallSalad && drink != Drink.NONE){
                    42
                } else if(hasSmallSalad){
                    36
                } else{
                    33
                }
                if(dish.special.additionalPrice == 6){
                    price += 7
                }
            }
            dish.isSaladDish -> {
                price = if(drink != Drink.NONE){
                    dish.special.additionalPrice + drinkPrice + 1
                } else{
                    dish.special.additionalPrice
                }
            }
            happyHour -> {
                price = if(dish.special.additionalPrice > 0){
                    39
                } else{
                    33
                }
            }
            isBusinessMeal -> {
                price = if(dish.special.additionalPrice > 0){
                    46
                } else{
                    39
                }
            }
            else -> {
                price = if(drink != Drink.NONE && dish.special.additionalPrice > 0){
                    basicPrice + dish.special.additionalPrice + drinkPrice
                } else{
                    if(drink != Drink.NONE){
                        basicPrice + dish.special.additionalPrice + drinkPrice
                    } else{
                        basicPrice + dish.special.additionalPrice
                    }
                }
            }
        }

        if(hasSmallSalad && !dish.isSmallDish){
            price += smallSaladPrice
        }
        if(dish.basics.contains(Basic.EGG)){
            price += 2
        }

    }

    fun deservesDrink(happyHour: Boolean): Boolean {
        return if(dish.isSmallDish && drink == Drink.NONE && !hasSmallSalad){
            true
        } else if(happyHour && !dish.isSmallDish && !dish.isSaladDish && drink == Drink.NONE){
            true
        } else if(isBusinessMeal && drink == Drink.NONE){
            true
        } else{
            false
        }
    }
}
