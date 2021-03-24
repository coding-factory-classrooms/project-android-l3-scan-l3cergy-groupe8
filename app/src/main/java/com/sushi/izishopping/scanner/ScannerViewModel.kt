package com.sushi.izishopping.scanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

private const val TAG = "ScannerViewModel"

sealed class ScannerViewModelState(
    open val errorMessage: String = "",
) {
    object Success : ScannerViewModelState()
    data class Failure(override val errorMessage: String) : ScannerViewModelState(errorMessage = errorMessage)
}

class ScannerViewModel {

    private val state = MutableLiveData<ScannerViewModelState>()

    fun getInfos() : LiveData<ScannerViewModelState> = state

    fun findFoodInfos(barcode: String) {

    }
}