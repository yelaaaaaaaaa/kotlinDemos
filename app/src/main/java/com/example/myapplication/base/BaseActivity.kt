package com.example.myapplication.base

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseActivity : AppCompatActivity(),CoroutineScope by MainScope(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        initView()
        initData()
    }

    abstract fun initData()

    abstract fun initView()

    abstract fun getLayoutResId(): Int

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}