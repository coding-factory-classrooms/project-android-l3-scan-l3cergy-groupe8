package com.sushi.izishopping

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.sushi.izishopping.model.Food
import com.sushi.izishopping.utils.api.foodApiCall
import com.sushi.izishopping.utils.database.dao.FoodDao
import com.sushi.izishopping.viewmodel.ScannerViewModel
import com.sushi.izishopping.viewmodel.ScannerViewModelState
import fr.neren.movies.TestObserver
import fr.neren.movies.testObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test


private const val TAG = "ScannerViewModel"

class ScannerViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var model : ScannerViewModel
    lateinit var listfoodDAO : FoodDao
    lateinit var observer : TestObserver<ScannerViewModelState>

    @Before
    fun setup() {
        model = ScannerViewModel()
        listfoodDAO = mock<FoodDao>()
        model.foodDao = listfoodDAO
        observer = model.getInfos().testObserver()
    }

    @Test
    fun `findFoodInfos yields state Success`() {

        val testFood = Food(
            "3329770063297",
            "YOP Parfum Vanille",
            "26/03/2021",
            "",
            "")

        whenever(foodApiCall(testFood.barcode)).thenReturn(testFood)
    }
}