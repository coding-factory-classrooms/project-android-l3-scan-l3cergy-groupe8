package com.sushi.izishopping.scanner

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.zxing.integration.android.IntentIntegrator
import com.sushi.izishopping.databinding.ActivityScannerBinding

private const val TAG = "ScannerActivity"

class ScannerActivity : AppCompatActivity() {
    private var debugMode : Boolean = true
    private val defaultBarcode : String = "3329770063297"

    private val model : ScannerViewModel by viewModels()
    private lateinit var binding : ActivityScannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model.getInfos().observe(this, Observer { Log.i(TAG, "onCreate: ") })

        binding.scannerButton.setOnClickListener {
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