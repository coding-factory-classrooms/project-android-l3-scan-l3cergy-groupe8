package com.sushi.izishopping.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.sushi.izishopping.databinding.ActivityFoodListBinding
import com.sushi.izishopping.fooddetail.FoodDetailViewModel
import com.sushi.izishopping.fooddetail.FoodDetailViewModelState


private const val TAG = "FoodDetailActivity"

class FoodDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFoodListBinding
    private val model : FoodDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model.getInfos().observe(this, Observer {
                state -> updateUi(state)
        })




        model.getFoodDetail()
    }

    private fun updateUi(state: FoodDetailViewModelState?) {
        when(state) {
            is FoodDetailViewModelState.Loading -> TODO()
            is FoodDetailViewModelState.Empty -> TODO()
            is FoodDetailViewModelState.Success -> {
                // display detail of the product chosen
                
            }
            is FoodDetailViewModelState.Failure -> TODO()
            else -> TODO()
        }
    }
}