package com.sushi.izishopping.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sushi.izishopping.adapter.FoodAdapter
import com.sushi.izishopping.databinding.ActivityFoodListBinding
import com.sushi.izishopping.model.Food
import com.sushi.izishopping.viewmodel.FoodListViewModel
import com.sushi.izishopping.viewmodel.FoodListViewModelState

private const val TAG = "FoodListActivity"

class FoodListActivity : AppCompatActivity() {
    private val model : FoodListViewModel by viewModels()
    private lateinit var binding : ActivityFoodListBinding
    private lateinit var adapter : FoodAdapter

    private var foodList : MutableList<Food> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model.getInfos().observe(this, Observer {updateUi(it!!)})

        adapter = FoodAdapter(listOf())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        model.getFoodList()
    }

    private fun updateUi(state: FoodListViewModelState) {
        when(state) {
            is FoodListViewModelState.Loading -> TODO()
            is FoodListViewModelState.Empty -> TODO()
            is FoodListViewModelState.Success -> {
                adapter.updateDataSet(state.foodList.toMutableList())
                Log.i(TAG, "updateUi: $foodList")
            }
            is FoodListViewModelState.Failure ->
                Log.i(TAG, "updateUi: ${state.errorMessage}")
        }
    }
}