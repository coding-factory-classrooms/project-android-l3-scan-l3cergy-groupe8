package com.sushi.izishopping.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sushi.izishopping.data.dao.FoodDao
import com.sushi.izishopping.data.entity.Food

@Database(entities = [Food::class], version = 1, exportSchema = false)
abstract class IziShoppingDatabase : RoomDatabase() {

    abstract fun foodDao() : FoodDao

    companion object{
        @Volatile
        private var INSTANCE: IziShoppingDatabase? = null

        fun getDatabase(context: Context) : IziShoppingDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    IziShoppingDatabase::class.java,
                    "izishopping_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}