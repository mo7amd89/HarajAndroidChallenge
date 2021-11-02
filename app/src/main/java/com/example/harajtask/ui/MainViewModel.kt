package com.example.harajtask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.harajtask.data.HarajReopImpl
import com.example.harajtask.data.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val reopImpl: HarajReopImpl
) :ViewModel(){
    private val _products=MutableLiveData<List<Product>>()
    val products:LiveData<List<Product>>
        get() =_products

    init {
        _products.value=reopImpl.getProducts()
    }
}