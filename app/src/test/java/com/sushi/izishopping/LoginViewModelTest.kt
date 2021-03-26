package com.sushi.izishopping

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sushi.izishopping.viewmodel.LoginViewModel
import com.sushi.izishopping.viewmodel.LoginViewModelState
import fr.neren.movies.testObserver
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `invalid credentials yields state Failure`() {

        // on instancie le model
        val model = LoginViewModel()
        // on instancie l'observer qui va regarder tout ce qui se passe au niveau du livedata
        val observer = model.getState().testObserver()

        // on declenche notre action
        model.login("wrong", "wrong")

        Assert.assertEquals(
            listOf(
                LoginViewModelState.Failure("Invalid Credentials")
            ),
            observer.observedValues
        )
    }

    @Test
    fun `valid credentials yields state Success`() {

        // on instancie le model
        val model = LoginViewModel()
        // on instancie l'observer qui va regarder tout ce qui se passe au niveau du livedata
        val observer = model.getState().testObserver()

        // on declenche notre action
        model.login("kotlin", "rocks")

        Assert.assertEquals(
            listOf(
                LoginViewModelState.Success
            ),
            observer.observedValues
        )
    }

    @Test
    fun `input credentials yields an enabled login button`() {
        val model = LoginViewModel()
        val observer = model.getState().testObserver()

        model.updateLogin(username = " ", password = " ")
        model.updateLogin(username = "a", password = "")
        model.updateLogin(username = "a", password = "b")

        Assert.assertEquals(
            listOf(
                LoginViewModelState.UpdateLogin(loginButtonEnabled = false),
                LoginViewModelState.UpdateLogin(loginButtonEnabled = false),
                LoginViewModelState.UpdateLogin(loginButtonEnabled = true)
            ),
            observer.observedValues
        )


    }
}