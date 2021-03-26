package com.sushi.izishopping.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list")
data class ShoppinList(
    @PrimaryKey(autoGenerate = true) val shoppingListId: Int,
    @ColumnInfo(name = "name") val name: String,
)