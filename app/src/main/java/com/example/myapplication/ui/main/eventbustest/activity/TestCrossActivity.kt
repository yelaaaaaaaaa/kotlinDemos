package com.example.myapplication.ui.main.eventbustest.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.eventbus.EventBus
import com.example.myapplication.ui.main.eventbustest.domain.CrossActivityEvent


/**
 *
 * @FileName:
 *          com.example.myapplication.ui.main.eventbustest.activity.TestCrossActivity
 * @author: Tony Shen
 * @date: 2019-08-27 00:13
 * @version: V1.0 <描述当前版本功能>
 */
class TestCrossActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        EventBus.post(CrossActivityEvent())
    }
}