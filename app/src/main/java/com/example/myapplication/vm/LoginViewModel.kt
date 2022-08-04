package com.example.myapplication.vm

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.BuildConfig
import com.example.myapplication.network.NetworkResponse
import com.example.myapplication.network.UserRepository
import com.example.myapplication.network.data.UserAuth
import com.example.myapplication.utils.NetworkResponseState
import com.google.gson.Gson
import kotlinx.coroutines.launch

class LoginViewModel(app: Application) : AndroidViewModel(app) {

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

    private val sharedPreferences: SharedPreferences =
        app.applicationContext.getSharedPreferences(
            BuildConfig.SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )

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

    fun statRequest(userAuth: UserAuth) {
        val deviceOnline = isOnline(getApplication())
        if (deviceOnline) {
            getUserInfo(userAuth)
        } else {
            _userResponse.value = NetworkResponseState.NO_INTERNET_CONNECTION
        }
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return true
            }
        }
        return false
    }

    private fun getUserInfo(userAuth: UserAuth) {
        viewModelScope.launch {
            when (val response = userRepository.getUserInfo(userAuth)) {
                is NetworkResponse.Success -> {
                    val editor = sharedPreferences.edit()
                    editor.also {
                        it.putString(USER_INFO, Gson().toJson(response.response))
                        it.putString(USERNAME, userAuth.email)
                        it.putString(PASSWORD, userAuth.password)
                        it.commit()
                    }
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
        private const val USERNAME: String = "USERNAME"
        private const val PASSWORD: String = "PASSWORD"
        private const val USER_INFO: String = "USER_INFO"
        private const val EMAIL_REGEX: String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    }
}
