package com.example.myapplication.network.data

import com.google.gson.annotations.SerializedName

data class SingleNews(
    val id: Int,
    val commenter: String,
    val comment: String,
    val date: String,
    val avatar: String,
    val likes: List<Int?>,
    @SerializedName("created_at") val creationDate: String,
    @SerializedName("updated_at") val updateDate: String
)
