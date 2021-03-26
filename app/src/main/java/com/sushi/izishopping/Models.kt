package com.sushi.izishopping

import com.sushi.izishopping.data.entity.FoodEntity

data class Food(
    val barcode: String,
    val name: String,
    val dateScan: String,
    val imgLink: String,
    val nutriScore : String
) {
    fun toFoodEntity() : FoodEntity {
        return FoodEntity(
            0,
            this.barcode,
            this.name,
            this.dateScan
        )
    }
}

