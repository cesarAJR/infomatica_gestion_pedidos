package com.cesar.infomatica_gestion_pedidos

import android.app.Application
import com.cesar.data.di.dataModule
import com.cesar.domain.di.domainModule
import com.cesar.infomatica_gestion_pedidos.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@MyApp)
            modules(dataModules + domainModules + appModules)
        }
    }
}

val appModules = listOf(appModule)
val domainModules = listOf(domainModule)
val dataModules = listOf(dataModule)
