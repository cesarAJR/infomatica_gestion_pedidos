package com.cesar.domain.model

data class Order(
    val id:String?=null,
    var client : String,
    var product : String,
    var amount : Int,
    var date : String,
    val imageUrl : String?=null,
    val isPending : Boolean?=false
)