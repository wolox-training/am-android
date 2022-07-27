package com.example.myapplication.network

import com.example.myapplication.network.data.UserAuth
import com.example.myapplication.network.data.UserInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {
    @POST("/auth/sign_in")
    suspend fun getUserInfo(@Body userAuth: UserAuth): Response<UserInfo>
}
