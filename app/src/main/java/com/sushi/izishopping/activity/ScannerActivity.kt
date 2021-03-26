package com.sushi.izishopping.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.zxing.integration.android.IntentIntegrator
import com.sushi.izishopping.App
import com.sushi.izishopping.databinding.ActivityScannerBinding
import com.sushi.izishopping.utils.api.foodApiCall
import com.sushi.izishopping.viewmodel.ScannerViewModel
import com.sushi.izishopping.viewmodel.ScannerViewModelState

private const val TAG = "ScannerActivity"

class ScannerActivity : AppCompatActivity() {
    private var debugMode : Boolean = false
    private val defaultBarcode : String = "3329770063297"

    private val model : ScannerViewModel by viewModels()
    private lateinit var binding : ActivityScannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model.foodDao = App.database.foodDao()

        model.getInfos().observe(this, Observer { state -> updateUi(state) })

        if(!debugMode) {
            val scanner = IntentIntegrator(this)
            scanner.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
            scanner.setOrientationLocked(true)
            scanner.setBeepEnabled(true)
            scanner.captureActivity = CaptureActivity::class.java
            scanner.initiateScan()
        } else {
            model.findFoodInfos(defaultBarcode)
        }
    }

    private fun updateUi(state: ScannerViewModelState) {
        when(state) {
            is ScannerViewModelState.Success -> {
                val intent = Intent(this, FoodDetailActivity::class.java)
                intent.putExtra("food", foodApiCall(state.barcode) )
                startActivity(intent)
                finish()
            }
            is ScannerViewModelState.Failure -> {
                Toast.makeText(this, state.errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents != null) {
                    model.findFoodInfos(result.contents)
                    Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}