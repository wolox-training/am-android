package com.example.myapplication.ui

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.repo.LoginRepository
import com.example.myapplication.utils.NetworkResponseState
import com.example.myapplication.vm.LoginViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LoginFragmentTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var loginRepository: LoginRepository

    private lateinit var loginFragment: LoginFragment

    @Mock
    private lateinit var loginViewModel: LoginViewModel


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        loginFragment = LoginFragment()
        loginViewModel = LoginViewModel(
            loginRepository
        )
    }

    @Test
    fun `test Invalid Credentials Toast Show`() {
        Mockito.`when`(loginViewModel.userResponse)
            .thenReturn(MutableLiveData(NetworkResponseState.INVALID_CREDENTIALS))
        loginFragment.userResponseObserver()
        Mockito.verify(loginFragment).showToast("Invalid credentials")
    }
}