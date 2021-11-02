package com.example.harajtask.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product(

    val body: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("commentCount")
    val commentCount: Int,
    @SerializedName("date")
    val date: Long,
    @SerializedName("thumbURL")
    val thumbURL: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("username")
    val username: String
) : Serializable