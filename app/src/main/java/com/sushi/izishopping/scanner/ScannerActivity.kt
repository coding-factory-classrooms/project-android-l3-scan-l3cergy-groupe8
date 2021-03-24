package com.sushi.izishopping.scanner

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.sushi.izishopping.databinding.ActivityScannerBinding


class ScannerActivity : AppCompatActivity() {

    protected val model : ScannerViewModel by viewModels()
    private lateinit var binding : ActivityScannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.scannerButton.setOnClickListener {
            val scanner = IntentIntegrator(this)
            scanner.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
            scanner.setOrientationLocked(true)
            scanner.setBeepEnabled(true)
            scanner.captureActivity = CaptureActivity::class.java
            scanner.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents != null) {
                    Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                    model.findFoodInfos(result.contents)
                } else {
                    Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}