package com.sushi.izishopping.api

//Les deux data class ci-dessous sont découpées ainsi afin de faire écho au modèle de données
//présent dans le JSON récupéré dans l'appel à l'API

//Contient le code bar du produit et un Product
data class FoodInfo(
    val code: String,
    val product: Product
)

//Product du JSON de l'api
data class Product(
    val product_name: String,
    val image_url: String,
    val nutrition_grades: String
)

