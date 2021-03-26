package com.sushi.izishopping.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sushi.izishopping.data.dao.FoodDao
import com.sushi.izishopping.data.entity.FoodEntity
import com.sushi.izishopping.data.entity.ShoppinList
import com.sushi.izishopping.data.entity.ShoppingListContent

@Database(entities = [FoodEntity::class, ShoppinList::class, ShoppingListContent::class], version = 1)
abstract class IziShoppingDatabase : RoomDatabase() {
    abstract fun foodDao() : FoodDao
}