package com.example.myapplication.vm

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.network.UserRepository
import com.example.myapplication.network.data.UserAuth
import com.google.gson.Gson
import kotlinx.coroutines.launch

class LoginViewModel(app: Application) : AndroidViewModel(app) {

    private val userRepository = UserRepository()

    private val _email = MutableLiveData<String?>()
    val email: LiveData<String?>
        get() = _email

    private val _password = MutableLiveData<String?>()
    val password: LiveData<String?>
        get() = _password

    private val _emptyFieldsError = MutableLiveData<Boolean>()
    val emptyFieldsError: LiveData<Boolean>
        get() = _emptyFieldsError

    private val _validEmail = MutableLiveData<Boolean>()
    val validEmail: LiveData<Boolean>
        get() = _validEmail

    private val _userResponseIsSuccessful = MutableLiveData<Boolean>()
    val userResponseIsSuccessful: LiveData<Boolean>
        get() = _userResponseIsSuccessful

    private val sharedPreferences: SharedPreferences =
        app.applicationContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun fieldsValuesValidation(emailValue: String, passwordValue: String) {
        if (emailValue.isEmpty() || passwordValue.isEmpty()) {
            _emptyFieldsError.value = true
        } else {
            emailValidation(emailValue, passwordValue)
        }
    }

    private fun emailValidation(emailValue: String, passwordValue: String) {
        val emailPattern = EMAIL_REGEX
        val emailComparable = emailValue.trim()
        if (emailComparable.matches(emailPattern.toRegex())) {
            val editor = sharedPreferences.edit()
            editor.also {
                it.putString(USERNAME, emailValue)
                it.putString(PASSWORD, passwordValue)
                it.commit()
            }
            _validEmail.value = true
        } else {
            _validEmail.value = false
        }
    }

    fun retrieveSavedUser() {
        val savedEmail = sharedPreferences.getString(USERNAME, null)
        val savedPassword = sharedPreferences.getString(PASSWORD, null)
        if (savedEmail != null && savedPassword != null) {
            _email.value = savedEmail
            _password.value = savedPassword
        }
    }

    fun emptyFieldsErrorShown() {
        _emptyFieldsError.value = null
    }

    fun resetValidEmailValue() {
        _validEmail.value = null
    }

     fun getUserInfo(userAuth: UserAuth) {
         viewModelScope.launch {
             val response = userRepository.getUserInfo(userAuth)
             if(response.isSuccessful){
                 val editor = sharedPreferences.edit()
                 editor.also {
                     it.putString(USER_INFO, Gson().toJson(response.body()))
                     it.commit()
                 }
                 _userResponseIsSuccessful.value = true
             }else{
                 _userResponseIsSuccessful.value = false
             }
         }
    }

    companion object {
        private const val USERNAME: String = "USERNAME"
        private const val PASSWORD: String = "PASSWORD"
        private const val USER_INFO: String = "USER_INFO"
        private const val SHARED_PREFERENCES_NAME: String = "PREFERENCE_NAME"
        private const val EMAIL_REGEX: String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    }
}
