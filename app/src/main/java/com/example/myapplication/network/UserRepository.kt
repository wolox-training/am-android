package com.example.myapplication.network

import com.example.myapplication.network.data.UserAuth
import com.example.myapplication.network.data.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserRepository {
    private val api = ApiBuilder("https://w-android-training.herokuapp.com").create(UserAPI::class.java)
    suspend fun getUserInfo(userAuth: UserAuth) : Response<UserInfo> =
        withContext(Dispatchers.IO) {
            api.getUserInfo(userAuth)
        }
}
