package com.sushi.izishopping.api

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.sushi.izishopping.Food
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.time.LocalDateTime

private const val TAG = "ApiRequests"

//Contient tous les calls API
interface FoodApiCall {
    @GET("v0/product/{barcode}.json")
    fun foodInformation(@Path("barcode") barcode: String): Call<FoodInfo>


}

//Permet de créer un appel à l'API graâce à Retrofit via l'interface FoodApiCall
fun foodApiCall(barcode : String){
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    //Création d'un moshi builder qui nous permettra de traduire notre JSOn en objet Kotlin
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    //On build Retrofit avec l'URL de base (complétée dans FoosApiCall)
    val retrofit = Retrofit.Builder()
        .baseUrl("https://world.openfoodfacts.org/api/")
        //On y ajoute le client HttpClient afin d'intercepter les logs et de logger les appels
        .client(client)
        //On ajoute le convertisseur Moshi créé plus haut
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    //On crée un apiCall de type FoodApiCall
    val apiCall = retrofit.create(FoodApiCall::class.java)

    //Appel de foodInformation
    apiCall.foodInformation(barcode).enqueue(object : Callback<FoodInfo> {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onResponse(call: Call<FoodInfo>, foodInfo: Response<FoodInfo>) {
            Log.i(TAG, "onResponse: response = ${foodInfo.body()}")
            // On attribue les données récupérés à un Food
            val newFood = Food(
                foodInfo.body()!!.code,
                foodInfo.body()!!.product.product_name,
                LocalDateTime.now().toString()
            )
            Log.i(TAG, "onResponse: newFood = $newFood")
        }

        override fun onFailure(call: Call<FoodInfo>, t: Throwable) {
            Log.i(TAG, "onFailure: P'tite erreur")
            t.printStackTrace()
        }

    })
}