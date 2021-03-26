package com.sushi.izishopping

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sushi.izishopping.activity.FoodListActivity
import com.sushi.izishopping.activity.ScannerActivity
import com.sushi.izishopping.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.foodListButton.setOnClickListener {
            val scannerIntent = Intent(this, FoodListActivity::class.java)
            startActivity(scannerIntent)
        }

        binding.scannerPageButton.setOnClickListener {
            val scannerIntent = Intent(this, ScannerActivity::class.java)
            startActivity(scannerIntent)
        }
    }
}