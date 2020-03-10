package com.example.myapplication.ui.main.eventbustest.activity

import android.content.Intent
import android.os.Bundle

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.eventbus.EventBus
import com.example.myapplication.eventbus.UI
import com.example.myapplication.ui.main.eventbustest.domain.CrossActivityEvent
import com.example.myapplication.ui.main.eventbustest.domain.ExceptionEvent
import kotlinx.android.synthetic.main.activity_eventbusmain.*


/**
 *
 * @FileName:
 *          com.example.myapplication.ui.main.eventbustest.activity.MainActivity
 * @author: Tony Shen
 * @date: 2019-08-26 19:39
 * @version: V1.0 <描述当前版本功能>
 */
class EventBusMainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_eventbusmain)

        text1.setOnClickListener {

            val i = Intent(this@EventBusMainActivity, TestEventBusActivity::class.java)
            startActivity(i)
        }

        text2.setOnClickListener {

            val i = Intent(this@EventBusMainActivity, TestCrossActivity::class.java)
            startActivity(i)
        }

        text3.setOnClickListener {

            val i = Intent(this@EventBusMainActivity, TestExceptionActivity::class.java)
            startActivity(i)
        }

        text4.setOnClickListener {

            val i = Intent(this@EventBusMainActivity, TestStickyActivity::class.java)
            startActivity(i)
        }

        registerEvents()

    }

    private fun registerEvents() {

        EventBus.register(this.javaClass.simpleName, UI, CrossActivityEvent::class.java) {
            Toast.makeText(this@EventBusMainActivity, "来自MainActivity的Toast", Toast.LENGTH_SHORT).show()
        }

        EventBus.register(this.javaClass.simpleName, UI, ExceptionEvent::class.java,{
            val str: String? = null
            println(str!!.substring(0)) // 故意制造空指针异常
        },{
            it.printStackTrace()  // 打印异常信息
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        EventBus.unregister(this.javaClass.simpleName)
    }
}