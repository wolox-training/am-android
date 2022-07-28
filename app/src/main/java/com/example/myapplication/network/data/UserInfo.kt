package com.example.myapplication.network.data

import com.google.gson.annotations.SerializedName

data class UserInfo(
    val id: Int,
    val email: String,
    val name: String,
    val nickname: String,
    @SerializedName("created_at") val creationDate: String
)
