package com.cesar.domain.core

sealed class Result<T>(val data:T?=null,val message:String?=null) {
    class SuccessFull<T>(data: T?):Result<T>(data)
    class Error<T>(message:String,data: T?=null):Result<T>(data,message)
}