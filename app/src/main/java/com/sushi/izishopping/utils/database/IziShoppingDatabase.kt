package com.sushi.izishopping.utils.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sushi.izishopping.utils.database.dao.FoodDao
import com.sushi.izishopping.utils.database.dao.ShoppinglistDao
import com.sushi.izishopping.utils.database.entity.FoodEntity
import com.sushi.izishopping.utils.database.entity.ShoppinglistContent
import com.sushi.izishopping.utils.database.entity.ShoppinlistEntity

@Database(entities = [FoodEntity::class, ShoppinlistEntity::class, ShoppinglistContent::class], version = 1)
abstract class IziShoppingDatabase : RoomDatabase() {
    abstract fun foodDao() : FoodDao
    abstract fun shoppinglistDao() : ShoppinglistDao
}