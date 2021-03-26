package com.sushi.izishopping.utils.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sushi.izishopping.utils.database.dao.FoodDao
import com.sushi.izishopping.utils.database.entity.FoodEntity
import com.sushi.izishopping.utils.database.entity.ShoppinList
import com.sushi.izishopping.utils.database.entity.ShoppingListContent

@Database(entities = [FoodEntity::class, ShoppinList::class, ShoppingListContent::class], version = 1)
abstract class IziShoppingDatabase : RoomDatabase() {
    abstract fun foodDao() : FoodDao
}