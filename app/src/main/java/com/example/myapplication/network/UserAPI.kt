package com.example.myapplication.network

import com.example.myapplication.network.data.UserAuth
import com.example.myapplication.network.data.UserInfo
import com.example.myapplication.network.data.UserNews
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserAPI {
    @POST("/auth/sign_in")
    suspend fun getUserInfo(@Body userAuth: UserAuth): Response<UserInfo>

    @GET("/comments/:id")
    suspend fun getUserNews(
        @Header("access_token") accessToken: String,
        @Header("uid") userId: String,
        @Header("client") client: String,
    ): Response<UserNews>
}
