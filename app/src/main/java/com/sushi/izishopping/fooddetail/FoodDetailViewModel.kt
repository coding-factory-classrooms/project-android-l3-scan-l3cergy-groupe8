package com.sushi.izishopping.fooddetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sushi.izishopping.Food

private val foodItem : Food =
    Food("3329770063297", "YOP Parfum Vanille", "","https://static.openfoodfacts.org/images/products/332/977/006/3297/front_fr.48.400.jpg","e")


sealed class FoodDetailViewModelState (
    open val errorMessage: String = ""
) {
    class Loading() : FoodDetailViewModelState()
    data class Success(val foodItem : Food) : FoodDetailViewModelState()
    class Failure() : FoodDetailViewModelState(errorMessage = "Une erreur est survenue durant la récupération du détail du produit.")
}

class FoodDetailViewModel : ViewModel() {
    private val state = MutableLiveData<FoodDetailViewModelState>()

    fun getInfos() : LiveData<FoodDetailViewModelState> = state

    fun getFoodDetail(foodExtra : Food) {

        state.postValue(FoodDetailViewModelState.Loading())
//        TODO("Ajouter récupération des data via la BDD")

        when {
            foodItem != null -> {
                state.postValue(FoodDetailViewModelState.Success(foodExtra))
            }
            else -> {
                state.postValue(FoodDetailViewModelState.Failure())
            }
        }
    }
}