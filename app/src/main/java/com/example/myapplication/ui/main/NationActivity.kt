package com.example.myapplication.ui.main

import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity

class NationActivity :BaseActivity(){
    override fun getLayoutResId() = R.layout.activity_navigation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
    }
    override fun initData() {

    }

    override fun initView() {

    }



}