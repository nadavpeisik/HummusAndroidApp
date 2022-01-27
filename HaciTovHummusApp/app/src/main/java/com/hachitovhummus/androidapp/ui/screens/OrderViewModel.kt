package com.hachitovhummus.androidapp.ui.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hachitovhummus.androidapp.R
import com.hachitovhummus.androidapp.model.*

class OrderViewModel(var happyHour: Boolean) : ViewModel() {
    private val _order = MutableLiveData(Order())
    val order: LiveData<Order> = _order
    private val _enabledBasics = MutableLiveData(setOf(Basic.CHICKPEA, Basic.TAHINI, Basic.FUL, Basic.EGG))
    val enabledBasics: LiveData<Set<Basic>> = _enabledBasics

    // UI driven events functions

    fun onBusinessDishSelect(){
        //Log.d("ERROR Before", "${_businessDish.value}")
        if(!_order.value!!.isBusinessMeal){
            onSpecialChange(Special.NONE)
            order.value!!.dish.isSmallDish = false
        }
        _order.value!!.isBusinessMeal = !_order.value!!.isBusinessMeal
        checkForClean()
        order.value!!.calculatePrice(happyHour)
        //Log.d("ERROR After", "${_businessDish.value}")
    }

    fun onSmallDishSelect(){
        //Log.d("ERROR Before", "${_smallDish.value}")
        if(!order.value!!.dish.isSmallDish){
            onSpecialChange(Special.NONE)
            _order.value!!.isBusinessMeal = false
        }
        order.value!!.dish.isSmallDish = !order.value!!.dish.isSmallDish
        checkForClean()
        order.value!!.calculatePrice(happyHour)
        //Log.d("ERROR After", "${_smallDish.value}")
    }

    fun onSpecialChange(newSpecial: Special) {
        //Log.d("ERROR Before", "${_special.value}")
        if(order.value!!.dish.special == newSpecial){
            order.value!!.dish.special = Special.NONE
            _order.value!!.dish.nameSpecialPart = ""
        }
        else{
            order.value!!.dish.special = newSpecial
            order.value!!.dish.basics.clear()
            _order.value!!.dish.nameBasicsPart = ""
            _order.value!!.dish.nameSpecialPart = order.value!!.dish.special.type
        }
        _enabledBasics.value = order.value!!.dish.special.enabledBasics
        order.value!!.dish.isSaladDish = order.value!!.dish.special.isSalad
        if(order.value!!.dish.isSaladDish){
            _order.value!!.hasSmallSalad = false
            _order.value!!.dish.spices.addAll(setOf(Spice.CUMIN, Spice.PAPRIKA, Spice.PARSLEY, Spice.OLIVE_OIL))
            _order.value!!.dish.excludingSpices = ""
        }
        _order.value!!.dish.name = _order.value!!.dish.nameSpecialPart + _order.value!!.dish.nameBasicsPart
        checkForClean()
        _order.value!!.dish.imageID = dishesPicturesMap[order.value!!.dish.buildImageNameFromPrefixes()]!!
        order.value!!.calculatePrice(happyHour)
        //Log.d("ERROR After", "${_special.value}")
    }

    fun onBasicSelect(item: Basic){
        if(_order.value!!.dish.nameSpecialPart == "קלאסי"){
            _order.value!!.dish.nameSpecialPart = ""
        }

        if(order.value!!.dish.basics.contains(item)){
            order.value!!.dish.basics.remove(item)
        }
        else{
            order.value!!.dish.basics.add(item)
        }
        order.value!!.dish.updateNameBasicsPart()

        if(order.value!!.dish.basics.isEmpty()){
            _order.value!!.dish.nameSpecialPart = order.value!!.dish.special.type
        }

        if(_order.value!!.dish.nameBasicsPart != "" && _order.value!!.dish.nameSpecialPart != "" && !_order.value!!.dish.nameSpecialPart.contains("עם")){
            _order.value!!.dish.nameSpecialPart = _order.value!!.dish.nameSpecialPart + " עם "
        }

        _order.value!!.dish.name = _order.value!!.dish.nameSpecialPart + _order.value!!.dish.nameBasicsPart
        checkForClean()
        _order.value!!.dish.imageID = dishesPicturesMap[order.value!!.dish.buildImageNameFromPrefixes()]!!
        //Log.d("Basics Current Set", "${_basics.value}")
    }

    fun onSpiceSelect(item: Spice){
        if(_order.value!!.dish.spices.contains(item)){
            _order.value!!.dish.spices.remove(item)
        }
        else{
            _order.value!!.dish.spices.add(item)
        }
        order.value!!.dish.updateExcludingSpices()
        //Log.d("Spices Current Set", "${_spices.value}")
    }

    fun onSmallSaladSelect(){
        _order.value!!.hasSmallSalad = !_order.value!!.hasSmallSalad
        order.value!!.calculatePrice(happyHour)
    }

    fun onDrinkChange(newDrink: Drink) {
        //Log.d("ERROR Before", "${_drink.value}")
        if(_order.value!!.drink == newDrink){
            _order.value!!.drink = Drink.NONE
        }
        else{
            _order.value!!.drink = newDrink
            if(!_order.value!!.isBusinessMeal && !order.value!!.dish.isSaladDish && !order.value!!.dish.isSmallDish){
                _order.value!!.isBusinessMeal = true
            }
        }
        order.value!!.calculatePrice(happyHour)
        //Log.d("ERROR After", "${_drink.value}")
    }

    fun updateComment(userComment: String){
        order.value!!.costumerComment = userComment
    }

    // Utilities

    private fun checkForClean(){
        if(order.value!!.dish.special == Special.NONE && order.value!!.dish.basics.isEmpty()){
            _order.value!!.dish.name = "נקי"
        }
    }

    private val dishesPicturesMap = mapOf(
        "ms" to R.drawable.ms,
        "ms_f" to R.drawable.ms_f,
        "ms_e" to R.drawable.ms_e,
        "ms_f_e" to R.drawable.ms_f_e,
        "mu" to R.drawable.mu,
        "mu_c" to R.drawable.mu_c,
        "mu_c_e" to R.drawable.mu_c_e,
        "mu_c_t" to R.drawable.mu_c_t,
        "mu_c_t_e" to R.drawable.mu_c_t_e,
        "mu_e" to R.drawable.mu_e,
        "mu_t" to R.drawable.mu_t,
        "mu_t_e" to R.drawable.mu_t_e,
        "no" to R.drawable.no,
        "no_c" to R.drawable.no_c,
        "no_c_e" to R.drawable.no_c_e,
        "no_c_f" to R.drawable.no_c_f,
        "no_c_f_e" to R.drawable.no_c_f_e,
        "no_c_t" to R.drawable.no_c_t,
        "no_c_t_e" to R.drawable.no_c_t_e,
        "no_c_t_f" to R.drawable.no_c_t_f,
        "no_c_t_f_e" to R.drawable.no_c_t_f_e,
        "no_e" to R.drawable.no_e,
        "no_f" to R.drawable.no_f,
        "no_f_e" to R.drawable.no_f_e,
        "no_t" to R.drawable.no_t,
        "no_t_e" to R.drawable.no_t_e,
        "no_t_f" to R.drawable.no_t_f,
        "no_t_f_e" to R.drawable.no_t_f_e,
        "sh" to R.drawable.sh,
        "sh_c" to R.drawable.sh_c,
        "sh_c_t" to R.drawable.sh_c_t,
        "sh_t" to R.drawable.sh_t,
        "bs" to R.drawable.bs,
        "bs_e" to R.drawable.bs_e,
        "ss" to R.drawable.ss,
        "ss_e" to R.drawable.ss_e,
    )
}