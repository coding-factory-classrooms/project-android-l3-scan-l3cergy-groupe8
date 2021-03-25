package com.sushi.izishopping.foodlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sushi.izishopping.Food
import com.sushi.izishopping.R

private val fakeData : List<Food> = listOf(
    Food("3329770063297", "YOP Parfum Vanille", R.drawable.food_yop_vanille),
    Food("3329770063280", "YOP Parfum Framboise", R.drawable.food_yop_framboise),
    Food("3023470001015", "Galettes St. Michel", R.drawable.food_galette_st_michel),
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