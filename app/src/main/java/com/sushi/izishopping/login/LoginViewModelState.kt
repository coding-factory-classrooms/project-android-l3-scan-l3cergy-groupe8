package com.sushi.izishopping.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

sealed class LoginViewModelState(
    open val errorMessage: String = "",
    open val loginButtonEnabled: Boolean = false
) {
    // on va toujours renvoyer un seul et meme objet car rien a dire en cas de Success
    object Success : LoginViewModelState()
    data class Failure(override val errorMessage: String) :
        LoginViewModelState(errorMessage = errorMessage,
            loginButtonEnabled = true
        )
    data class UpdateLogin(override val loginButtonEnabled: Boolean)
        : LoginViewModelState(loginButtonEnabled = loginButtonEnabled)
}

class LoginViewModel : ViewModel() {
    private val state = MutableLiveData<LoginViewModelState>()
    fun getState() : LiveData<LoginViewModelState> = state

    fun login(username: String, password: String) {
        if (validLogin(username, password)) {
            // dans ce cas on renvoit un state d'ecran "Success"
            state.value = LoginViewModelState.Success
        } else {
            state.value = LoginViewModelState.Failure("Invalid Credentials")
        }
    }

    fun updateLogin(username: String, password: String) {
        val buttonEnabled = username.isNotBlank() && password.isNotBlank()
        state.value = LoginViewModelState.UpdateLogin(loginButtonEnabled = buttonEnabled)

    }


}

// la regle de gestion (notre pattern) veut que nous mettions cette fonction coté viewmodel
fun validLogin(username: String, password: String): Boolean =
    username == "izi" && password == "shoppin"