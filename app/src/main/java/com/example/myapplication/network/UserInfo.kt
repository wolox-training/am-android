package com.example.myapplication.network

import com.squareup.moshi.Json

data class UserInfo(
    val id: Int,
    val email: String,
    val name: String,
    val nickname: String,
    @Json(name = "created_at") val creationDate: String
)
