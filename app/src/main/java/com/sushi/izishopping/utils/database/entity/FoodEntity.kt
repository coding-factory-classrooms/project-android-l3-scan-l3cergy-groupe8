package com.sushi.izishopping.utils.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food")
data class FoodEntity(
    @PrimaryKey(autoGenerate = true) val foodId: Long,
    @ColumnInfo(name = "barcode") val barcode: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "date_scan") val dateScan: String,
)