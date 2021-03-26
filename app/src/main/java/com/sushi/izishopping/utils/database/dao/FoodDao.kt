package com.sushi.izishopping.utils.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sushi.izishopping.utils.database.entity.FoodEntity

@Dao
interface FoodDao {
    @Query("SELECT * FROM food ORDER BY foodId ASC")
    fun getAllFood(): List<FoodEntity>

    @Query("SELECT * FROM shopping_list_content LEFT JOIN food ON food_id = foodId WHERE shopping_list_id = :listId ORDER BY foodId ASC")
    fun getAllFoodByShoppingListId(listId: Int): List<FoodEntity>

    @Insert
    fun addFood(food: FoodEntity)
}