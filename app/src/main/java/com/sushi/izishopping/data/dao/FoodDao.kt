package com.sushi.izishopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sushi.izishopping.data.entity.FoodEntity

@Dao
interface FoodDao {
    @Query("SELECT * FROM food ORDER BY foodId ASC")
    fun readAllData(): List<FoodEntity>

    @Insert
    fun addFood(food: FoodEntity)
}