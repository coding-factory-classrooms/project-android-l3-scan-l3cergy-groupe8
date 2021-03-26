package com.sushi.izishopping.utils.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sushi.izishopping.utils.database.entity.ShoppinlistEntity

@Dao
interface ShoppinglistDao {
    @Query("SELECT * FROM shopping_list ORDER BY shoppingListId ASC")
    fun getAllShoppinglist(): List<ShoppinlistEntity>

    @Query("SELECT COUNT(food_id) FROM shopping_list_content WHERE shopping_list_id = :listId")
    fun getNbFoodInShoppinglist(listId: Int): Int

    @Insert
    fun addShoppinglist(shoppinglist: ShoppinlistEntity)

}