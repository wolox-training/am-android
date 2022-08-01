package com.example.myapplication.vm

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.BuildConfig

class SplashViewModel(app: Application) : AndroidViewModel(app) {

    private val _userIsLogged = MutableLiveData<Boolean>()
    val userIsLogged: LiveData<Boolean>
        get() = _userIsLogged

    private val sharedPreferences: SharedPreferences =
        app.applicationContext.getSharedPreferences(BuildConfig.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun retrieveSavedUser() {
        val savedEmail = sharedPreferences.getString(USERNAME, null)
        val savedPassword = sharedPreferences.getString(PASSWORD, null)
        _userIsLogged.value = savedEmail != null && savedPassword != null
    }

    companion object {
        private const val USERNAME: String = "USERNAME"
        private const val PASSWORD: String = "PASSWORD"
    }
}
