package com.sushi.izishopping.scanner

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.sushi.izishopping.FoodApi
import com.sushi.izishopping.FoodResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val TAG = "ScannerViewModel"

sealed class ScannerViewModelState(
    open val errorMessage: String = "",
) {
    object Success : ScannerViewModelState()
    data class Failure(override val errorMessage: String) : ScannerViewModelState(errorMessage = errorMessage)
}

class ScannerViewModel : ViewModel() {

    private val state = MutableLiveData<ScannerViewModelState>()
    fun getInfos() : LiveData<ScannerViewModelState> = state

    fun findFoodInfos(barcode: String) {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()) .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://world.openfoodfacts.org/api/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(FoodApi::class.java)

        retrofit.foodInformation(barcode).enqueue(object : Callback<FoodResponse> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<FoodResponse>, response: Response<FoodResponse>) {
                Log.i(TAG, "onResponse: response = ${response.body()}")
            }

            override fun onFailure(call: Call<FoodResponse>, t: Throwable) {
                Log.i(TAG, "onFailure: P'tite erreur")
                t.printStackTrace()
            }

        })
    }
}