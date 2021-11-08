package com.hachitovhummus.androidapp.ui.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hachitovhummus.androidapp.model.Dish
import com.hachitovhummus.androidapp.model.Order
import com.hachitovhummus.androidapp.model.OrderCollection

class OrderListViewModel() : ViewModel() {

    var orderCollection = OrderCollection(listOf(),false,"","")
    private var _userName = MutableLiveData("")
    val userName: LiveData<String> = _userName

    fun setUserName(str: String){
        _userName.value = str
        orderCollection.userName = str
    }

    fun createOrderCollection(isTakeOut: Boolean){
        orderCollection = OrderCollection(listOf(),isTakeOut,_userName.value!!,"")
    }

    //orders
    private var _orders = MutableLiveData(listOf<Order>())
    val orders: LiveData<List<Order>> = _orders

    fun addOrder(order: Order) {
        _orders.value = _orders.value!! + listOf(order)
    }

    fun createOrderListFromListOfViewModels() {
        _orders.value = listOf()
        for (vm in _ordersViewModels.value!!){
            val userOrder = Order(
                Dish(
                    nameBasicsPart = vm.order.value!!.dish.nameBasicsPart,
                    nameSpecialPart = vm.order.value!!.dish.nameSpecialPart,
                    excludingSpices = vm.order.value!!.dish.excludingSpices,
                    special = vm.special.value!!,
                    basics = vm.basics.value!!,
                    enabledBasics = vm.enabledBasics.value!!,
                    spices = vm.spices.value!!,
                    isSmallDish = vm.smallDish.value!!,
                    isSaladDish = vm.isSaladDish.value!!,
                    imageID = vm.order.value!!.dish.imageID),
                drink = vm.drink.value!!,
                price = vm.price.value!!,
                hasSmallSalad = vm.smallSalad.value!!,
                costumerComment = vm.userComment.value!!)
            addOrder(userOrder)
        }
        orderCollection.orders = _orders.value!!
    }

    //ordersViewModels
    private var _ordersViewModels = MutableLiveData(listOf<OrderViewModel>())

    fun addOrder() {
        val userOrder = Order(
            Dish(
                nameBasicsPart = currentOrder.value!!.order.value!!.dish.nameBasicsPart,
                nameSpecialPart = currentOrder.value!!.order.value!!.dish.nameSpecialPart,
                excludingSpices = currentOrder.value!!.order.value!!.dish.excludingSpices,
                special = currentOrder.value!!.special.value!!,
                basics = currentOrder.value!!.basics.value!!,
                enabledBasics = currentOrder.value!!.enabledBasics.value!!,
                spices = currentOrder.value!!.spices.value!!,
                isSmallDish = currentOrder.value!!.smallDish.value!!,
                isSaladDish = currentOrder.value!!.isSaladDish.value!!,
                imageID = currentOrder.value!!.order.value!!.dish.imageID),
            drink = currentOrder.value!!.drink.value!!,
            price = currentOrder.value!!.price.value!!,
            hasSmallSalad = currentOrder.value!!.smallSalad.value!!,
            costumerComment = "test")
        if(_inEditMode.value!!){
        }
        else{
            _ordersViewModels.value = _ordersViewModels.value!! + listOf(currentOrder.value!!)
        }
        createOrderListFromListOfViewModels()
        _currentOrder.value = OrderViewModel()  //which makes a new clean order

    }

    fun removeOrder(index: Int) {
        println("RemoveOrder is called")
        _ordersViewModels.value = _ordersViewModels.value!!.toMutableList().also {
            it.removeAt(index)
        }

        createOrderListFromListOfViewModels()
        _refreshList.value = false
        _refreshList.value = true

    }

    fun editOrder(){
        _currentOrder.value = _ordersViewModels.value!![_indexToEdit.value!!]
    }

    private var _currentOrder = MutableLiveData(OrderViewModel())
    val currentOrder: LiveData<OrderViewModel> = _currentOrder

    private var _indexToEdit = MutableLiveData(-1)

    fun currentIndex(index: Int) {
        _indexToEdit.value = index
        Log.d("currentIndex", "${_indexToEdit.value}")
    }

    private var _inEditMode = MutableLiveData(false)

    fun setInEditMode(bool: Boolean) {
        _inEditMode.value = bool
    }

    private var _refreshList = MutableLiveData(true)
    val refreshList: LiveData<Boolean> = _refreshList
}