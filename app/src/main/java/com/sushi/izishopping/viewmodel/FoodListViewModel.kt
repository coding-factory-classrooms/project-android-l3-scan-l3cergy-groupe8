package com.sushi.izishopping.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sushi.izishopping.model.Food

private val fakeData : List<Food> = listOf(
    Food("3329770063297", "YOP Parfum Vanille", "","https://static.openfoodfacts.org/images/products/332/977/006/3297/front_fr.48.400.jpg","e"),
    Food("3329770063280", "YOP Parfum Framboise", "","https://static.openfoodfacts.org/images/products/332/977/006/3297/front_fr.48.400.jpg","e"),
    Food("3023470001015", "Galettes St. Michel", "","https://static.openfoodfacts.org/images/products/332/977/006/3297/front_fr.48.400.jpg","e"),
)

sealed class FoodListViewModelState(
    open val errorMessage: String = ""
) {
    class Loading() : FoodListViewModelState()
    class Empty() : FoodListViewModelState(errorMessage = "La liste d'aliment est vide.")
    data class Success(val foodList : List<Food>) : FoodListViewModelState()
    class Failure() : FoodListViewModelState(errorMessage = "Une erreur est survenue durant la récupération de la liste.")
}

class FoodListViewModel : ViewModel() {

    private val state = MutableLiveData<FoodListViewModelState>()

    fun getInfos() : LiveData<FoodListViewModelState> = state

    fun getFoodList() {

        state.postValue(FoodListViewModelState.Loading())
//        TODO("Ajouter récupération des data via la BDD")

        when {
            fakeData.isEmpty() -> {
                state.postValue(FoodListViewModelState.Empty())
            }
            fakeData.isNotEmpty() -> {
                state.postValue(FoodListViewModelState.Success(fakeData))
            }
            else -> {
                state.postValue(FoodListViewModelState.Failure())
            }
        }
    }
}