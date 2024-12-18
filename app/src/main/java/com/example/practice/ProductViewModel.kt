package com.example.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    fun eco_false(name: String,eco_check:Boolean){
        viewModelScope.launch {
            try {
                val response = ApiClient.productApi.getProductsByNameAndEcoCheck(name,eco_check)
                _products.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun searchProductsByName(name: String) {
        viewModelScope.launch {
            try {
                val response = ApiClient.productApi.getProductsByName(name)
                _products.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
