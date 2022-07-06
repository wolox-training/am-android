package com.example.myapplication.vm

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class LoginViewModel (app: Application): AndroidViewModel(app) {

    val email = MutableLiveData<String?>()
    val password = MutableLiveData<String?>()
    val toast = MutableLiveData<Boolean>()
    val save = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()
    val sharedPreferences: SharedPreferences = app.applicationContext.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)


    fun nullValidation() {
        if (email.value == null && password.value == null) {
            toast.value = true
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
            error.value = true
        }
    }

    fun setInformation(savedEmail: String?, savedPassword: String?) {
        if (savedEmail != null && savedPassword != null) {
            email.value = savedEmail
            password.value = savedPassword
        }
    }
}
