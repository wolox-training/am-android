package com.example.myapplication.repo

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.BuildConfig
import com.example.myapplication.network.NetworkResponse
import com.example.myapplication.network.data.UserAuth
import com.example.myapplication.network.data.UserInfo
import com.google.gson.Gson
import retrofit2.Response

class LoginRepository(app: Application) : AndroidViewModel(app) {

    private val sharedPreferences: SharedPreferences =
        app.applicationContext.getSharedPreferences(
            BuildConfig.SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )

     fun isOnline(): Boolean {
        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        }
        return false
    }

    fun saveUser(userAuth: UserAuth, response: NetworkResponse.Success<Response<UserInfo>>) {
        val editor = sharedPreferences.edit()
        editor.also {
            it.putString(USER_INFO, Gson().toJson(response.response))
            it.putString(USERNAME, userAuth.email)
            it.putString(PASSWORD, userAuth.password)
            it.commit()
        }
    }

    companion object {
        private const val USERNAME: String = "USERNAME"
        private const val PASSWORD: String = "PASSWORD"
        private const val USER_INFO: String = "USER_INFO"
    }
}
