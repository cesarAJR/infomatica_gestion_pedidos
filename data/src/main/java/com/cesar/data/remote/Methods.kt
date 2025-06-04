package com.cesar.data.remote


import com.cesar.data.remote.model.OrderRequest
import com.cesar.data.remote.model.OrderResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface Methods {
    @GET("pedidos")
    suspend fun getListOrder(): Response<List<OrderResponse>>

    @POST("pedidos")
    suspend fun addOrder(@Body orderRequest: OrderRequest): Response<OrderResponse>

    @PUT("pedidos/{id}")
    suspend fun editOrder(@Path("id") id : String ,@Body orderRequest: OrderRequest): Response<OrderResponse>

    @DELETE("pedidos/{id}")
    suspend fun deleteOrder(@Path("id") id : String): Response<OrderResponse>
}