package com.example.myapplication.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.network.NetworkResponse
import com.example.myapplication.network.UserRepository
import com.example.myapplication.network.data.UserAuth
import com.example.myapplication.repo.LoginRepository
import com.example.myapplication.utils.NetworkResponseState
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val userRepository = UserRepository()

    private val _emptyFieldsError = MutableLiveData<Boolean>()
    val emptyFieldsError: LiveData<Boolean>
        get() = _emptyFieldsError

    private val _validEmail = MutableLiveData<Boolean>()
    val validEmail: LiveData<Boolean>
        get() = _validEmail

    private val _userResponse = MutableLiveData<NetworkResponseState>()
    val userResponse: LiveData<NetworkResponseState>
        get() = _userResponse

    fun fieldsValuesValidation(emailValue: String, passwordValue: String) {
        if (emailValue.isEmpty() || passwordValue.isEmpty()) {
            _emptyFieldsError.value = true
        } else {
            emailValidation(emailValue)
        }
    }

    private fun emailValidation(emailValue: String) {
        val emailPattern = EMAIL_REGEX
        val emailComparable = emailValue.trim()
        _validEmail.value = emailComparable.matches(emailPattern.toRegex())
    }

    fun emptyFieldsErrorShown() {
        _emptyFieldsError.value = null
    }

    fun startRequest(userAuth: UserAuth) {
        val deviceOnline = loginRepository.isOnline()
        if (deviceOnline) {
            getUserInfo(userAuth)
        } else {
            _userResponse.value = NetworkResponseState.NO_INTERNET_CONNECTION
        }
    }

    private fun getUserInfo(userAuth: UserAuth) {
        viewModelScope.launch {
            when (val response = userRepository.getUserInfo(userAuth)) {
                is NetworkResponse.Success -> {
                    loginRepository.saveUser(userAuth, response)
                    _userResponse.value = NetworkResponseState.SUCCESS
                }
                is NetworkResponse.Error -> {
                    _userResponse.value = NetworkResponseState.INVALID_CREDENTIALS
                }
                else -> {
                    _userResponse.value = NetworkResponseState.EXCEPTION
                }
            }
        }
    }

    companion object {
        private const val EMAIL_REGEX: String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    }
}
