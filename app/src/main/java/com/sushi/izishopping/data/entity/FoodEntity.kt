package com.sushi.izishopping.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food")
data class Food(
    @PrimaryKey(autoGenerate = true) val foodId: Long,
    @ColumnInfo(name = "barcode") val barcode: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "date_scan") val dateScan: String,
    @ColumnInfo(name = "img_link") val imgLink: String,
    @ColumnInfo(name = "nutri_score") val nutriScore : String
)