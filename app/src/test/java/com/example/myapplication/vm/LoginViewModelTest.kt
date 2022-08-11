package com.example.myapplication.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myapplication.getOrAwaitValue
import com.example.myapplication.repo.LoginRepository
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LoginViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var loginRepository: LoginRepository

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        loginViewModel = LoginViewModel(
            loginRepository
        )
    }

    @Test
    fun `test Mail Is Empty`() {
        val emptyEmail = ""
        val password = "Password"
        loginViewModel.fieldsValuesValidation(emptyEmail, password)
        val response = loginViewModel.emptyFieldsError.getOrAwaitValue()
        Assertions.assertThat(response).isEqualTo(true)
    }

    @Test
    fun `test Password Is Empty`() {
        val email = "mail@gmail.com"
        val emptyPassword = ""
        loginViewModel.fieldsValuesValidation(email, emptyPassword)
        val response = loginViewModel.emptyFieldsError.getOrAwaitValue()
        Assertions.assertThat(response).isEqualTo(true)
    }

    @Test
    fun `test Email Is Valid`() {
        val validEmail = "mail@gmail.com"
        val password = "password"
        loginViewModel.fieldsValuesValidation(validEmail, password)
        val response = loginViewModel.validEmail.getOrAwaitValue()
        Assertions.assertThat(response).isEqualTo(true)
    }

    @Test
    fun `test Email Is Invalid`() {
        val invalidEmail = "mail@gmail"
        val password = "password"
        loginViewModel.fieldsValuesValidation(invalidEmail, password)
        val response = loginViewModel.validEmail.getOrAwaitValue()
        Assertions.assertThat(response).isEqualTo(false)
    }
}
