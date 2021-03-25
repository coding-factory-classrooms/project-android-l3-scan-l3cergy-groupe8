package com.sushi.izishopping.data

import androidx.lifecycle.LiveData
import com.sushi.izishopping.data.dao.FoodDao
import com.sushi.izishopping.data.entity.Food

class FoodRepository(private val foodDao: FoodDao) {

    val readAllData: LiveData<List<Food>> = foodDao.readAllData()

    suspend fun addFood(food: Food) {
        foodDao.addFood(food)
    }
}