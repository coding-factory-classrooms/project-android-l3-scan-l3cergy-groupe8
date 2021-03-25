package com.sushi.izishopping.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sushi.izishopping.data.entity.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodViewModel(application: Application): AndroidViewModel(application) {

    private val readAllData: LiveData<List<Food>>
    private val repository: FoodRepository

    init {
        val foodDao = IziShoppingDatabase.getDatabase(application).foodDao()
        repository = FoodRepository(foodDao)
        readAllData = repository.readAllData
    }

    fun addFood(food: Food) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFood(food)
        }
    }
}