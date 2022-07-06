package com.example.myapplication.vm

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.EditText
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

    private val sharedPreferences: SharedPreferences =
        app.applicationContext.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

    fun fieldsValuesValidation(emailValue: EditText, passwordValue: EditText) {
        _email.value = emailValue.text.toString()
        _password.value = passwordValue.text.toString()
        if (email.value == null && password.value == null) {
            _emptyFieldsError.value = true
        } else {
            emailValidation()
        }
    }

    private fun emailValidation() {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val emailComparable = email.value!!.trim()
        if (emailComparable.matches(emailPattern.toRegex())) {
            val editor = sharedPreferences.edit()
            editor.putString("USERNAME", email.value)
            editor.putString("PASSWORD", password.value)
            editor.commit()
        } else {
            _invalidEmail.value = true
        }
    }

    fun retrieveSavedUser() {
        val savedEmail = sharedPreferences.getString("USERNAME", null)
        val savedPassword = sharedPreferences.getString("PASSWORD", null)
        if (savedEmail != null && savedPassword != null) {
            _email.value = savedEmail
            _password.value = savedPassword
        }
    }

    fun emptyFieldsErrorShown() {
        _emptyFieldsError.value = false
    }

    fun invalidEmailErrorShown() {
        _invalidEmail.value = false
    }
}
