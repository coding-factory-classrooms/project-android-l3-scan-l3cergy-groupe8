package com.sushi.izishopping.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.sushi.izishopping.databinding.ActivityFoodDetailBinding
import com.sushi.izishopping.utils.DownloadImageFromUrl
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

        model.getInfos().observe(this, Observer { updateUi(it!!) })

        model.getFoodDetail(intent.getParcelableExtra("food"))
    }

    private fun updateUi(state: FoodDetailViewModelState?) {
        when(state) {
            is FoodDetailViewModelState.Loading ->
                Log.i(TAG, "updateUi: ")
            is FoodDetailViewModelState.Success -> {
                binding.detailTitletextView.text = state.foodItem.name
                DownloadImageFromUrl(binding.foodDetailImageView).execute(state.foodItem.imgLink)
                binding.scanDetailTextView.text = state.foodItem.dateScan
                binding.nutriScoreTextView.text = state.foodItem.nutriScore
                binding.barcodeTextView.text = state.foodItem.barcode
            }
            is FoodDetailViewModelState.Failure ->
                Log.e(TAG, "updateUi: ${state.errorMessage}")
        }
    }
}