package com.sushi.izishopping.scanner

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sushi.izishopping.Food
import com.sushi.izishopping.api.foodApiCall
import com.sushi.izishopping.data.dao.FoodDao
import com.sushi.izishopping.data.entity.FoodEntity

private const val TAG = "ScannerViewModel"

sealed class ScannerViewModelState(
    open val errorMessage: String = "",
) {
    object Success : ScannerViewModelState()
    data class Failure(override val errorMessage: String) : ScannerViewModelState(errorMessage = errorMessage)
}

class ScannerViewModel() : ViewModel() {

    lateinit var foodDao: FoodDao

    private val state = MutableLiveData<ScannerViewModelState>()
    fun getInfos() : LiveData<ScannerViewModelState> = state

    fun findFoodInfos(barcode: String) {
        var food : Food? = foodApiCall(barcode)
        if(food != null) {
            foodDao.addFood(food.toFoodEntity())
            getAllData()
        }
    }

    fun getAllData() {
        var list : List<FoodEntity> = foodDao.readAllData()
        Log.i(TAG, "getAllData: test")
    }
}