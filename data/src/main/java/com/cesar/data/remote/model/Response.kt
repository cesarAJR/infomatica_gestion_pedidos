package com.cesar.data.remote.model

import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("id")
    val id : String,
    @SerializedName("client")
    val client : String,
    @SerializedName("product")
    val product : String,
    @SerializedName("amount")
    val amount : Int,
    @SerializedName("date")
    val date : String,
    @SerializedName("imageUrl")
    val imageUrl : String
)