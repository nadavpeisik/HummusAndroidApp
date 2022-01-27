package com.hachitovhummus.androidapp.ui.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.hachitovhummus.androidapp.model.*
import java.time.LocalTime

class OrderListViewModel(isTakeOut: Boolean) : ViewModel() {
    var orderCollection = OrderCollection(takeOut = isTakeOut)
    private var happyHour : Boolean = LocalTime.now().isBefore(LocalTime.parse("12:03:00.00"))

    var currentOrderVM = OrderViewModel(happyHour)
    private var ordersViewModels = mutableStateListOf<OrderViewModel>()

    private var indexToEdit = -1
    private var inEditMode = false

    fun addOrder(){
        if(!inEditMode){
            orderCollection.orders.add(currentOrderVM.order.value!!)
            ordersViewModels.add(currentOrderVM)
        }
        currentOrderVM = OrderViewModel(happyHour)  //which makes a new clean order
    }

    fun removeOrder(index: Int) {   //orderViewModel: OrderViewModel
        //println("RemoveOrder is called")
        orderCollection.orders.removeAt(index)
        ordersViewModels.removeAt(index)
    }

    fun editOrder(){
        currentOrderVM = ordersViewModels[indexToEdit]
    }

    fun currentIndex(index: Int) {
        indexToEdit = index
        //Log.d("currentIndex", "${_indexToEdit.value}")
    }

    fun setInEditMode(bool: Boolean) {
        inEditMode = bool
        //Log.d("currentIndex", "${_indexToEdit.value}")
    }

}