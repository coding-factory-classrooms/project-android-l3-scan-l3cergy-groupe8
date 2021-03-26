package com.sushi.izishopping.utils.api

import android.os.StrictMode
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.sushi.izishopping.model.Food
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "ApiRequests"

//Contient tous les calls API
interface FoodApiCall {
    @GET("v0/product/{barcode}.json")
    fun foodInformation(@Path("barcode") barcode: String): Call<FoodInfo>
}

interface CustomApi {
    fun foodApiCall(barcode: String): Food
}

class CustomApiClass : CustomApi {
    override fun foodApiCall(barcode: String): Food {
        //Permet de passer outre le blocage nous empêchant de run un call API sur le Thread principal
        //nous permettant donc de faire un appel synchrone et de renvoyer newFood
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX)

        lateinit var newFood : Food

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

        //Deux possibilités : execute() et enqueue()
        //execute() permet de faire l'apple API de manière synchrone, qui nous permet de return newFood
        val response: Response<FoodInfo> = apiCall.foodInformation(barcode).execute()
        val apiResponse: FoodInfo = response.body()!!
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

        newFood = Food (
            apiResponse.code,
            apiResponse.product.product_name,
            sdf.format(Date()),
            apiResponse.product.image_url,
            apiResponse.product.nutrition_grades
        )

        //et enqueue() qui permet de faire l'appel API de manière asynchrone (ce qui devrait être fait)
        //mais qui dans ce cas nous empêche de return newFood correctement, car celui-ci est return
        //AVANT d'être mis à jour par l'API
        /*apiCall.foodInformation(barcode).enqueue(object : Callback<FoodInfo> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<FoodInfo>, foodInfo: Response<FoodInfo>)  {
                Log.i(TAG, "onResponse: response = ${foodInfo.body()}")
                // On attribue les données récupérés à un Food
                newFood = Food(
                    foodInfo.body()!!.code,
                    foodInfo.body()!!.product.product_name,
                    LocalDateTime.now().toString(),
                    foodInfo.body()!!.product.image_url,
                    foodInfo.body()!!.product.nutrition_grades
                )
                Log.i(TAG, "onResponse: newFood = $newFood")
            }

            override fun onFailure(call: Call<FoodInfo>, t: Throwable) {
                Log.i(TAG, "onFailure: P'tite erreur")
                newFood = Food("", "MANGES TES MANGEMORTS", "", "", "")
                t.printStackTrace()
            }
        })}*/

        return newFood
    }

}
///*
////Permet de créer un appel à l'API graâce à Retrofit via l'interface FoodApiCall
//fun foodApiCall(barcode: String): Food {
//
//    //Permet de passer outre le blocage nous empêchant de run un call API sur le Thread principal
//    //nous permettant donc de faire un appel synchrone et de renvoyer newFood
//    StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX)
//
//    lateinit var newFood : Food
//
//    val logging = HttpLoggingInterceptor()
//    logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
//    val client = OkHttpClient.Builder()
//        .addInterceptor(logging)
//        .build()
//
//    //Création d'un moshi builder qui nous permettra de traduire notre JSOn en objet Kotlin
//    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
//
//    //On build Retrofit avec l'URL de base (complétée dans FoosApiCall)
//    val retrofit = Retrofit.Builder()
//        .baseUrl("https://world.openfoodfacts.org/api/")
//        //On y ajoute le client HttpClient afin d'intercepter les logs et de logger les appels
//        .client(client)
//        //On ajoute le convertisseur Moshi créé plus haut
//        .addConverterFactory(MoshiConverterFactory.create(moshi))
//        .build()
//
//    //On crée un apiCall de type FoodApiCall
//    val apiCall = retrofit.create(FoodApiCall::class.java)
//
//    //Appel de foodInformation
//
//    //Deux possibilités : execute() et enqueue()
//    //execute() permet de faire l'apple API de manière synchrone, qui nous permet de return newFood
//    val response: Response<FoodInfo> = apiCall.foodInformation(barcode).execute()
//    val apiResponse: FoodInfo = response.body()!!
//    val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
//
//    newFood = Food (
//        apiResponse.code,
//        apiResponse.product.product_name,
//        sdf.format(Date()),
//        apiResponse.product.image_url,
//        apiResponse.product.nutrition_grades
//    )
//
//    //et enqueue() qui permet de faire l'appel API de manière asynchrone (ce qui devrait être fait)
//    //mais qui dans ce cas nous empêche de return newFood correctement, car celui-ci est return
//    //AVANT d'être mis à jour par l'API
//    /*apiCall.foodInformation(barcode).enqueue(object : Callback<FoodInfo> {
//        @RequiresApi(Build.VERSION_CODES.O)
//        override fun onResponse(call: Call<FoodInfo>, foodInfo: Response<FoodInfo>)  {
//            Log.i(TAG, "onResponse: response = ${foodInfo.body()}")
//            // On attribue les données récupérés à un Food
//            newFood = Food(
//                foodInfo.body()!!.code,
//                foodInfo.body()!!.product.product_name,
//                LocalDateTime.now().toString(),
//                foodInfo.body()!!.product.image_url,
//                foodInfo.body()!!.product.nutrition_grades
//            )
//            Log.i(TAG, "onResponse: newFood = $newFood")
//        }
//
//        override fun onFailure(call: Call<FoodInfo>, t: Throwable) {
//            Log.i(TAG, "onFailure: P'tite erreur")
//            newFood = Food("", "MANGES TES MANGEMORTS", "", "", "")
//            t.printStackTrace()
//        }
//    })}*/
//
//    return newFood
//}
// */