package com.sushi.izishopping

import android.util.Log
import com.sushi.izishopping.viewmodel.ScannerViewModel
import fr.neren.movies.testObserver
import org.junit.Test


private const val TAG = "ScannerViewModel"

class ScannerViewModelTest {

    @Test
    fun `yields something`() {
        val model = ScannerViewModel()
        val observer = model.getInfos().testObserver()


    }
}