package com.sushi.izishopping.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.sushi.izishopping.App
import com.sushi.izishopping.databinding.ActivityShoppinglistCreationBinding
import com.sushi.izishopping.viewmodel.ShoppinglistCreationViewModel
import com.sushi.izishopping.viewmodel.ShoppinglistCreationViewModelState

private const val TAG = "ShoppinglistCreationActivity"

class ShoppinglistCreationActivity : AppCompatActivity() {

    private val model: ShoppinglistCreationViewModel by viewModels()
    private lateinit var binding: ActivityShoppinglistCreationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppinglistCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model.getState().observe(this, Observer { updateUI(it!!) })
        model.shoppinglistDao = App.database.shoppinglistDao()

        binding.createShoppinglistButton.setOnClickListener {
            model.createShoppinglist(
                binding.shoppinglistNameEditText.text.toString()
            )
        }

        binding.shoppinglistNameEditText.doAfterTextChanged { model.updateShoppinglistName(
            binding.shoppinglistNameEditText.text.toString()
        ) }

        model.updateShoppinglistName(
            binding.shoppinglistNameEditText.text.toString()
        )
    }

    @SuppressLint("LongLogTag")
    private fun updateUI(state: ShoppinglistCreationViewModelState) {
        Log.i(TAG, "updateUI: state=$state")
        when (state) {
            is ShoppinglistCreationViewModelState.Success -> {
                binding.createShoppinglistButton.isEnabled = state.creationButtonEnabled
                Toast.makeText(this,
                    "Création de liste effectuée",
                    Toast.LENGTH_SHORT).show()

                navigateToFoodListActivity(binding.shoppinglistNameEditText.text.toString().toInt())
            }
            is ShoppinglistCreationViewModelState.Failure -> {
                binding.createShoppinglistButton.isEnabled = state.creationButtonEnabled
                Toast.makeText(this,
                    state.errorMessage,
                    Toast.LENGTH_LONG).show()
            }
            is ShoppinglistCreationViewModelState.UpdateShoppinglistName -> {
                binding.createShoppinglistButton.isEnabled = state.creationButtonEnabled
            }

        }
    }

    private fun navigateToFoodListActivity(shoppinglistId:Int) {
        val intent = Intent(this, FoodListActivity::class.java)
        intent.putExtra("shoppinglistId", shoppinglistId)
        startActivity(intent)
        finish()
    }
}