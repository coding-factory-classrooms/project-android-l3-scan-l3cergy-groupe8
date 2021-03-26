package com.sushi.izishopping

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.sushi.izishopping.model.Food
import com.sushi.izishopping.utils.database.IziShoppingDatabase
import com.sushi.izishopping.utils.database.dao.FoodDao
import com.sushi.izishopping.utils.database.entity.FoodEntity
import com.sushi.izishopping.viewmodel.FoodListViewModel
import com.sushi.izishopping.viewmodel.FoodListViewModelState
import fr.neren.movies.TestObserver
import fr.neren.movies.testObserver
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class FoodListViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var model : FoodListViewModel
    lateinit var listfoodDAO : FoodDao
    lateinit var observer : TestObserver<FoodListViewModelState>

    @Before
    fun setup() {
        model = FoodListViewModel()
        listfoodDAO = mock<FoodDao>()
        model.foodDao = listfoodDAO
        observer = model.getInfos().testObserver()
    }

    //verifier qu'une liste d'aliment chargé genere bien un state Success
    @Test
    fun `get foodList yields state Success`() {

        val testFood = Food(
            "3329770063297",
            "YOP Parfum Vanille",
            "26/03/2021",
            "",
            "")

        val testFoodEntity : FoodEntity = FoodEntity(1, "3329770063297", "YOP Parfum Vanille", "26/03/2021")

        whenever(listfoodDAO.getAllFood())
            .thenReturn(listOf(testFoodEntity))

        model.getFoodList(null)

        Assert.assertEquals(
            listOf(
                FoodListViewModelState.Loading,
                FoodListViewModelState.Success(listOf(testFood))
            ),
            observer.observedValues
        )
    }

    @Test
    fun `get foodList yields state Empty`() {

        whenever(listfoodDAO.getAllFood())
            .thenReturn(listOf())

        model.getFoodList(null)

        Assert.assertEquals(
            listOf(
                FoodListViewModelState.Loading,
                FoodListViewModelState.Empty("La liste d'aliment est vide.")
            ),
            observer.observedValues
        )
    }


    @Test
    fun `get foodlist yields state Failure`() {

        // TODO() : le probleme ici, c'est que l'on doit tester non pas des données mais des connexions
    }



}