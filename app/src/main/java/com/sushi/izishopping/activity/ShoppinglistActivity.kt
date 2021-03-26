package com.sushi.izishopping.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sushi.izishopping.App
import com.sushi.izishopping.adapter.ShoppinglistAdapter
import com.sushi.izishopping.databinding.ActivityShoppinglistListBinding
import com.sushi.izishopping.model.Shoppinglist
import com.sushi.izishopping.utils.OnSwipeTouchListener
import com.sushi.izishopping.viewmodel.ShoppinglistListModelState
import com.sushi.izishopping.viewmodel.ShoppinglistViewModel

private const val TAG = "ShoppinglistActivity"

class ShoppinglistActivity : AppCompatActivity(){
    private val model : ShoppinglistViewModel by viewModels()
    private lateinit var binding : ActivityShoppinglistListBinding
    private lateinit var adapter : ShoppinglistAdapter

    private var shoppinglistList : MutableList<Shoppinglist> = mutableListOf()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppinglistListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model.shoppinglistDao = App.database.shoppinglistDao()

        binding.shoppinglistFloatingActionButton.setOnClickListener {
            val scannerIntent = Intent(this, ShoppinglistCreationActivity::class.java)
            startActivity(scannerIntent)
        }

        model.getInfosShoppinglist().observe(this, {updateUi(it!!)})

        adapter = ShoppinglistAdapter(listOf())
        binding.shoppingListRecyclerView.adapter = adapter
        binding.shoppingListRecyclerView.layoutManager = LinearLayoutManager(this)

        this.binding.goToFoodListFloatingActionButton.setOnClickListener {
            val foodListIntent = Intent(this, FoodListActivity::class.java)
            foodListIntent.putExtra("shoppingListId", 0)
            startActivity(foodListIntent)
        }

        //Ajout d'un swipe pour changer de vue entre les recyclerView ( peu fonctionnel )
        /*shoppingListRecyclerView.setOnTouchListener(
            object: OnSwipeTouchListener(this){
                override fun onSwipeRight() {
                    val foodListIntent = Intent(context, FoodListActivity::class.java)
                    foodListIntent.putExtra("shoppingListId", 0)
                    startActivity(foodListIntent)
                }
            })*/
        model.getShoppinglistList()
    }

    private fun updateUi(state: ShoppinglistListModelState) {
        when(state) {
            is ShoppinglistListModelState.Loading ->
                binding.chargementImageView.visibility = View.VISIBLE
            is ShoppinglistListModelState.Empty ->
                Toast.makeText(this,"La liste est vide", Toast.LENGTH_SHORT).show()
            is ShoppinglistListModelState.Success -> {
                adapter.updateDataSet(state.shoppinglistList.toMutableList())
                Log.i(TAG, "updateUi: $shoppinglistList")
            }
            is ShoppinglistListModelState.Failure ->
                Toast.makeText(this, state.errorMessage, Toast.LENGTH_LONG).show()
        }
    }
}