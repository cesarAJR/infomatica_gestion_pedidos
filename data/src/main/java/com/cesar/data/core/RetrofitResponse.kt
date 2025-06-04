package com.cesar.data.core

import retrofit2.Response

class RetrofitResponse<T> {
    fun getResult(response:Response<T>): Result<T?> {
          try {
            if (response.isSuccessful){
              return  Result.success(response.body())
            }
          return  Result.failure(Exception("ocurrio un error"))
        }catch (e:Exception){
              return  Result.failure(e)
        }
    }

}