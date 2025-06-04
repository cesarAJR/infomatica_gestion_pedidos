package com.cesar.data.util

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresPermission
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkUtil {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isConnected(context: Context): Boolean {
        var isConnected = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw      = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }else{
            val wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            val mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            if (wifi != null) isConnected = wifi.isConnected
            if (mobile != null && !isConnected) isConnected = mobile.isConnected
            return isConnected
        }
    }
}