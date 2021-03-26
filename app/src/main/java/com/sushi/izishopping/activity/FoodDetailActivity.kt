package com.sushi.izishopping.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.sushi.izishopping.databinding.ActivityFoodDetailBinding
import com.sushi.izishopping.viewmodel.FoodDetailViewModel
import com.sushi.izishopping.viewmodel.FoodDetailViewModelState


private const val TAG = "FoodDetailActivity"

class FoodDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFoodDetailBinding
    private val model : FoodDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val barcode : String = intent.getStringExtra("barcode")
        Log.i(TAG, "onCreate: barcode -> $barcode")
        binding.detailTitletextView.text = barcode

        model.getInfos().observe(this, Observer { state ->
            updateUi(state)
        })

        model.getFoodDetail()
    }

    private fun updateUi(state: FoodDetailViewModelState?) {
        when(state) {
//            is FoodDetailViewModelState.Loading -> TODO()
            is FoodDetailViewModelState.Empty -> TODO()
            is FoodDetailViewModelState.Success -> {
                // display detail of the product chosen

            }
            is FoodDetailViewModelState.Failure -> TODO()
//            else -> TODO()
        }
    }
}