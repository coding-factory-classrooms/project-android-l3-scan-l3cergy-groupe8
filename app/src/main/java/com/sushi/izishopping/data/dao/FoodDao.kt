package com.sushi.izishopping.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sushi.izishopping.data.entity.Food

@Dao
interface FoodDao {

    @Query("SELECT * FROM Food ORDER BY id ASC")
    fun readAllData(): LiveData<List<Food>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFood(food: Food)
}