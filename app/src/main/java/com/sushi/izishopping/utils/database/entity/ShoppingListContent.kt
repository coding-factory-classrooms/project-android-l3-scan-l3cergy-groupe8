package com.sushi.izishopping.utils.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "shopping_list_content", primaryKeys = ["shopping_list_id", "food_id"])
data class ShoppingListContent(
    @ColumnInfo(name = "shopping_list_id") val shoppingListId : Long,
    @ColumnInfo(name = "food_id") val foodId : Long
)