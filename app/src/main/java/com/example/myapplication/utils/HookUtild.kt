package com.example.myapplication.utils

import android.content.Context
import android.view.View
import java.lang.reflect.Proxy

object HookUtild {
    @Suppress("NAME_SHADOWING")
    fun hook(context: Context, view: View?) {
        try {
            val method =
                View::class.java.getDeclaredMethod("getListenerInfo")
            method.isAccessible = true
            val mListenerInfo = method.invoke(view)
            val listenerInfoClz =
                Class.forName("android.view.View\$ListenerInfo")
            val field =
                listenerInfoClz.getDeclaredField("mOnClickListener")
            val onClickListener =
                field[mListenerInfo] as View.OnClickListener
            val proxyOnClickListener =
                Proxy.newProxyInstance(
                    context.javaClass.classLoader,
                    arrayOf<Class<*>>(
                        View.OnClickListener::class.java
                    )
                ) { proxy, method, args -> method.invoke(onClickListener,args) }
            field.set(mListenerInfo,proxyOnClickListener)
            field.get(mListenerInfo)
        } catch (e: Exception) {
        }
    }
}