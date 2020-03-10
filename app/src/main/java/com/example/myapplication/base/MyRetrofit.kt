package com.example.myapplication.base

import com.example.myapplication.App
import com.example.myapplication.utils.NetWorkUtils
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import java.io.File

object MyRetrofit :BaseRetrofitClient(){
    val service by lazy { getService(NetService::class.java, NetService.BASE_URL) }
    val cookieJar by lazy { PersistentCookieJar(SetCookieCache(),SharedPrefsCookiePersistor(App.context)) }
    override fun handleBuilder(builder: OkHttpClient.Builder) {
        val httpCacheDirectory = File(App.context.cacheDir,"responses")
        val cacheSize = 10 * 1024 * 1024L // 10 MiB
        val cache =Cache(httpCacheDirectory,cacheSize)
       builder.cache(cache).cookieJar(cookieJar).addInterceptor { chain ->
            var request = chain.request()
            if (!NetWorkUtils.isNetworkAvailable(App.context)) {
                request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
            }
            val response = chain.proceed(request)
            if (!NetWorkUtils.isNetworkAvailable(App.context)) {
                val maxAge = 60 * 60
                response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=$maxAge")
                    .build()
            } else {
                val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
                response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .build()
            }
            response
        }
    }

}