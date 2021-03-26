package com.sushi.izishopping.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sushi.izishopping.model.Food
import com.sushi.izishopping.utils.api.foodApiCall
import com.sushi.izishopping.utils.database.dao.FoodDao

private const val TAG = "ScannerViewModel"

sealed class ScannerViewModelState(
    open val errorMessage: String = "",
) {
    data class Success(var barcode : String) : ScannerViewModelState()
    data class Failure(override val errorMessage: String) : ScannerViewModelState(errorMessage = errorMessage)
}

class ScannerViewModel() : ViewModel() {

    lateinit var foodDao: FoodDao

    private val state = MutableLiveData<ScannerViewModelState>()
    fun getInfos() : LiveData<ScannerViewModelState> = state

    fun findFoodInfos(barcode: String) {
        var food : Food = foodApiCall(barcode)
        if(food != null) {
            foodDao.addFood(food.toFoodEntity())
            // TODO CHANGE BARCODE TO ENTITY
            state.postValue(ScannerViewModelState.Success(food.barcode))
        } else {
            state.postValue(ScannerViewModelState.Failure("Le produit n'a pas été trouvé."))
        }
    }
}