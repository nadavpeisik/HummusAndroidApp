package com.hachitovhummus.androidapp.ui.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hachitovhummus.androidapp.R
import com.hachitovhummus.androidapp.model.*

class OrderViewModel() : ViewModel() {

    //Order
    private val _order = MutableLiveData(Order())
    val order: LiveData<Order> = _order

    val basicPrice = 25
    var drinkPrice = 0
    var smallSaladPrice = 0
    private val _price = MutableLiveData(basicPrice)
    val price: LiveData<Int> = _price

    // Business Dish
    private val _businessDish = MutableLiveData(false)
    val businessDish: LiveData<Boolean> = _businessDish

    fun onBusinessDishSelect(){
        if(!_businessDish.value!!){
            onSpecialChange(Special.NONE)
            _smallDish.value = false
        }
        _businessDish.value = !_businessDish.value!!
        checkForClean()
        calculatePrice()
    }

    // Small Dish
    private val _smallDish = MutableLiveData(false)
    val smallDish: LiveData<Boolean> = _smallDish

    fun onSmallDishSelect(){
        if(!_smallDish.value!!){
            onSpecialChange(Special.NONE)
            _businessDish.value = false
        }
        _smallDish.value = !_smallDish.value!!
        checkForClean()
        calculatePrice()
    }

    // Basics
    private val _basics = MutableLiveData(setOf<Basic>())
    val basics: LiveData<Set<Basic>> = _basics

    fun onBasicSelect(item: Basic){

        if(_order.value!!.dish.nameSpecialPart == "קלאסי"){
            _order.value!!.dish.nameSpecialPart = ""
        }

        if(_basics.value!!.contains(item)){
            _basics.value = _basics.value!!.toMutableSet().also {
                it.remove(item)
            }
        }
        else{
            _basics.value = _basics.value!! + setOf(item)
        }
        _order.value!!.dish.nameBasicsPart = DishBasicsToString()


        if(_basics.value!!.isEmpty()){
            _order.value!!.dish.nameSpecialPart = _special.value!!.type
        }


        if(_order.value!!.dish.nameBasicsPart != "" && _order.value!!.dish.nameSpecialPart != "" && !_order.value!!.dish.nameSpecialPart.contains("עם")){
            _order.value!!.dish.nameSpecialPart = _order.value!!.dish.nameSpecialPart + " עם "
        }

        _order.value!!.dish.name = _order.value!!.dish.nameSpecialPart + _order.value!!.dish.nameBasicsPart
        checkForClean()
        _order.value!!.dish.imageID = dishesPicturesMap[buildImageNameFromPrefixes()]!!
    }

    // Specials
    private val _special = MutableLiveData(Special.NONE)
    val special: LiveData<Special> = _special

    private val _enabledBasics = MutableLiveData(setOf<Basic>(Basic.CHICKPEA, Basic.TAHINI, Basic.FUL, Basic.EGG))
    val enabledBasics: LiveData<Set<Basic>> = _enabledBasics

    private val _isSaladDish = MutableLiveData(false)
    val isSaladDish: LiveData<Boolean> = _isSaladDish

    fun onSpecialChange(newSpecial: Special) {
        if(_special.value == newSpecial){
            _special.value = Special.NONE
            _order.value!!.dish.nameSpecialPart = ""
        }
        else{
            _special.value = newSpecial
            _basics.value = setOf()
            _order.value!!.dish.nameBasicsPart = ""
            _order.value!!.dish.nameSpecialPart = _special.value!!.type
            val specialPrefix: String = _special.value!!.name.substring(0,1).lowercase() + '_'
        }
        _enabledBasics.value = _special.value!!.enabledBasics
        _isSaladDish.value = _special.value!!.isSalad
        if(_isSaladDish.value!!){
            _smallSalad.value = false
            _spices.value = setOf(Spice.CUMIN, Spice.PAPRIKA, Spice.PARSLEY, Spice.OLIVE_OIL)
            _order.value!!.dish.excludingSpices = ""
        }
        _order.value!!.dish.name = _order.value!!.dish.nameSpecialPart + _order.value!!.dish.nameBasicsPart
        checkForClean()
        _order.value!!.dish.imageID = dishesPicturesMap[buildImageNameFromPrefixes()]!!
        calculatePrice()
    }

    fun calculatePrice(){
        if(_smallDish.value!!){
            if(_smallSalad.value!! && drink.value!! != Drink.NONE){
                _price.value = 42
            }
            else if(_smallSalad.value!!){
                _price.value = 36
            }
            else{
                _price.value = 33
            }
            if(_special.value!!.additionalPrice == 6){
                _price.value = _price.value!! + 7
            }
        }
        else if(_isSaladDish.value!!){
            if(drinkPrice>0){
                _price.value = _special.value!!.additionalPrice + drinkPrice + smallSaladPrice + 1
            }
            else{
                _price.value = _special.value!!.additionalPrice + drinkPrice + smallSaladPrice
            }
        }
        else{
            if(_businessDish.value!!){
                if(_special.value!!.additionalPrice > 0){
                    _price.value = 46 + smallSaladPrice
                }
                else{
                    _price.value = 39 + smallSaladPrice
                }
            }
            else{
                if(drinkPrice > 0 && _special.value!!.additionalPrice > 0){
                    _price.value = basicPrice + _special.value!!.additionalPrice + drinkPrice + smallSaladPrice
                }
                else{
                    _price.value = basicPrice + _special.value!!.additionalPrice + drinkPrice + smallSaladPrice
                }
            }
        }
    }

    fun checkForClean(){
        if(_special.value == Special.NONE && _basics.value!!.isEmpty()){
            _order.value!!.dish.name = "נקי"
        }
    }

    // Spices
    private val _spices = MutableLiveData(setOf<Spice>(Spice.CUMIN, Spice.PAPRIKA, Spice.PARSLEY, Spice.OLIVE_OIL))
    val spices: LiveData<Set<Spice>> = _spices

    fun onSpiceSelect(item: Spice){
        if(_spices.value!!.contains(item)){
            _spices.value = _spices.value!!.toMutableSet().also {
                it.remove(item)
            }
        }
        else{
            _spices.value = _spices.value!! + setOf(item)
        }
        _order.value!!.dish.excludingSpices = DishSpicesToSpicesToExludeString()
        Log.d("Spices Current Set", "${_spices.value}")
    }

    // Small Salad
    private val _smallSalad = MutableLiveData(false)
    val smallSalad: LiveData<Boolean> = _smallSalad

    fun onSmallSaladSelect(){
        _smallSalad.value = !_smallSalad.value!!
        smallSaladPrice = if(_smallSalad.value!!){
            15
        } else{
            0
        }
        calculatePrice()
    }

    // Drinks
    private val _drink = MutableLiveData(Drink.NONE)
    val drink: LiveData<Drink> = _drink

    fun onDrinkChange(newDrink: Drink) {
        Log.d("ERROR Before", "${_drink.value}")
        if(_drink.value == newDrink){
            _drink.value = Drink.NONE
            drinkPrice = 0
        }
        else{
            _drink.value = newDrink
            drinkPrice = 7
        }
        calculatePrice()
        Log.d("ERROR After", "${_drink.value}")
    }

    // User Comment
    private val _useComment = MutableLiveData("")
    val userComment: LiveData<String> = _useComment

    fun updateComment(userComment: String){
        _useComment.value = userComment
    }

    fun deservesDrink(): Boolean {
        if(_smallDish.value!! && _drink.value!! == Drink.NONE && !_smallSalad.value!!){
            println(true)
            return true
        }
        else if(_businessDish.value!! && _drink.value!! == Drink.NONE){
            return true
        }
        else{
            println(false)
            return false
        }
    }

    fun DishSpicesToSpicesToExludeString(): String {
        val spicesToExlude = setOf(
            Spice.CUMIN,
            Spice.PAPRIKA,
            Spice.PARSLEY,
            Spice.OLIVE_OIL
        ).subtract(_spices.value!!)

        if(spicesToExlude.isEmpty()){
            return ""
        }

        val spicesToExcludeTypes = mutableSetOf<String>()
        for(item: Spice in spicesToExlude){
            spicesToExcludeTypes.add(item.type)
        }
        return ReplaceLastCommaWithAnd("בלי " + spicesToExcludeTypes.joinToString())
    }

    fun DishBasicsToString(): String {
        return if (_basics.value!!.isEmpty()) {
            ""
        } else {
            val basicsNames = mutableSetOf<String>()
            for(item: Basic in _basics.value!!){
                basicsNames.add(item.type)
            }
            ReplaceLastCommaWithAnd(basicsNames.joinToString())
        }
    }

    fun ReplaceLastCommaWithAnd(str: String): String {
        return if (str.contains(',')) {
            str.substringBeforeLast(',') + " ו" + str.substringAfterLast(", ")
        } else {
            str
        }
    }

    fun buildImageNameFromPrefixes(): String{
        var imageName: String = _special.value!!.prefix
        if(_basics.value!!.contains(Basic.CHICKPEA)){
            imageName += "_c"
        }
        if(_basics.value!!.contains(Basic.TAHINI)){
            imageName += "_t"
        }
        if(_basics.value!!.contains(Basic.FUL)){
            imageName += "_f"
        }
        if(_basics.value!!.contains(Basic.EGG)){
            imageName += "_e"
        }

        return imageName
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