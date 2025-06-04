package com.cesar.data.di

import android.content.Context
import androidx.room.Room
import com.cesar.data.core.BasicInterceptor
import com.cesar.data.database.AppDatabase
import com.cesar.data.database.dao.OrderDao
import com.cesar.data.datasource.local.OrderLocalDataSource
import com.cesar.data.datasource.local.OrderLocalDataSourceImp
import com.cesar.data.datasource.remote.api.OrderRemoteDataSource
import com.cesar.data.datasource.remote.api.OrderRemoteDataSourceImp
import com.cesar.data.remote.Methods
import com.cesar.data.repository.OrderRepositoryImp
import com.cesar.domain.repository.OrderRepository
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val dataModule = module {
    single { provideBasicInterceptor() }
    single { provideRetrofit(get()) }
    single { provideDatabase(androidContext()) }
    single { provideOrderDao(get())  }
    factory<OrderRemoteDataSource> { OrderRemoteDataSourceImp(get()) }
    factory<OrderLocalDataSource> { OrderLocalDataSourceImp(get()) }
    factory<OrderRepository> { OrderRepositoryImp(get(),get(),androidContext()) }
}


fun provideRetrofit(okHttpClient: OkHttpClient) : Methods {
    return Retrofit.Builder()
        .baseUrl("https://683d0211199a0039e9e3ec12.mockapi.io/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create()
}

fun provideBasicInterceptor() : OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(BasicInterceptor())
        .build()
}

fun provideDatabase(context : Context) : AppDatabase = Room.databaseBuilder(
    context,
    AppDatabase::class.java,
    "dbOrder"
).build()

fun provideOrderDao(appDatabase: AppDatabase) : OrderDao = appDatabase.OrderDao()
