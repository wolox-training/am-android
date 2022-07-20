package com.example.myapplication.vm

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LoginViewModel(app: Application) : AndroidViewModel(app) {

    private val _email = MutableLiveData<String?>()
    val email: LiveData<String?>
        get() = _email

    private val _password = MutableLiveData<String?>()
    val password: LiveData<String?>
        get() = _password

    private val _emptyFieldsError = MutableLiveData<Boolean>()
    val emptyFieldsError: LiveData<Boolean>
        get() = _emptyFieldsError

    private val _invalidEmail = MutableLiveData<Boolean>()
    val invalidEmail: LiveData<Boolean>
        get() = _invalidEmail

    private val _validEmail = MutableLiveData<Boolean>()
    val validEmail: LiveData<Boolean>
        get() = _validEmail

    private val _signUpClick = MutableLiveData<Boolean>()
    val signUpClick: LiveData<Boolean>
        get() = _signUpClick

    private val _termsAndConditionsClick = MutableLiveData<Boolean>()
    val termsAndConditionsClick: LiveData<Boolean>
        get() = _termsAndConditionsClick

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
            _invalidEmail.value = true
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

    fun signUpClicked() {
        _signUpClick.value = true
    }

    fun termsAndConditionsClicked() {
        _termsAndConditionsClick.value = true
    }

    fun emptyFieldsErrorShown() {
        _emptyFieldsError.value = null
    }

    fun invalidEmailErrorShown() {
        _invalidEmail.value = null
    }

    fun homePageNavigated() {
        _validEmail.value = null
    }

    fun signUpNavigated() {
        _signUpClick.value = null
    }

    fun termsAndConditionsIntentDone() {
        _termsAndConditionsClick.value = null
    }

    companion object {
        private const val USERNAME: String = "USERNAME"
        private const val PASSWORD: String = "PASSWORD"
        private const val SHARED_PREFERENCES_NAME: String = "PREFERENCE_NAME"
        private const val EMAIL_REGEX: String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    }
}
