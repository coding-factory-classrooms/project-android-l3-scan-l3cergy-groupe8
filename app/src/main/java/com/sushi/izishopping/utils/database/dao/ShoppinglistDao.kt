package com.sushi.izishopping.utils.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sushi.izishopping.utils.database.entity.ShoppinlistEntity

@Dao
interface ShoppinglistDao {
    @Query("SELECT * FROM shopping_list ORDER BY shoppingListId ASC")
    fun getAllShoppinglist(): List<ShoppinlistEntity>

    @Insert
    fun addShoppinglist(shoppinglist: ShoppinlistEntity)
}