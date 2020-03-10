package com.example.myapplication.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.telecom.ConnectionService

class NetWorkUtils {
    companion object{
        fun isNetworkAvailable(context: Context):Boolean{
            val manager =context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val netWork =manager.activeNetwork
                val info = manager.getNetworkCapabilities(netWork)
                !(null == info || (!info.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)&&!info.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)))
            } else {
                val info = manager.activeNetworkInfo
                !(null == info || !info.isAvailable)
            }
        }
    }
}