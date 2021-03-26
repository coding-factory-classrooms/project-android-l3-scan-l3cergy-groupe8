package com.sushi.izishopping.utils.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sushi.izishopping.utils.database.entity.FoodEntity

@Dao
interface FoodDao {
    @Query("SELECT * FROM food ORDER BY foodId ASC")
    fun readAllData(): List<FoodEntity>

    @Insert
    fun addFood(food: FoodEntity)
}