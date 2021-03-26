package com.sushi.izishopping

import android.app.Application
import androidx.room.Room
import com.sushi.izishopping.utils.database.IziShoppingDatabase

class App : Application() {

    companion object {
        lateinit var database : IziShoppingDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this,
            IziShoppingDatabase::class.java, "IziShopping"
        ).allowMainThreadQueries().build()
    }
}