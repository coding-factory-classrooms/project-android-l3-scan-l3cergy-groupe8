package com.sushi.izishopping

import com.squareup.moshi.Json
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

data class FoodResponse(val product: ProductApi)

data class ProductApi(val code: String, val product_name: String, val image_url: String, val nutrition_grades: String, val ingredients_text_fr: String)

data class Food(
    @field:Json(name="code") val barcode: String,
    @field:Json(name="product_name") val name: String,
    val dateScan: String
)

interface FoodApi {
    @GET("v0/product/{barcode}.json")
    fun foodInformation(@Path("barcode") barcode: String): Call<FoodResponse>
}

