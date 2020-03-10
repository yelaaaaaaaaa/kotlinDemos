package com.example.myapplication.ipcclient

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.IMyAidlInterface
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_ipcclient.*


class IpcClientActivity : AppCompatActivity() {
    var mIMyAidlInterface: IMyAidlInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ipcclient)
        button2.setOnClickListener { v: View? ->
            var intent  = Intent()
            intent.action = "com.example.myapplication"
            intent.`package`="com.example.myapplication"
            bindService(intent, ConnectCallBack(), Context.BIND_AUTO_CREATE)
        }
    }

    inner class ConnectCallBack : ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {
            mIMyAidlInterface = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mIMyAidlInterface  = IMyAidlInterface.Stub.asInterface(service)
            login()
        }


    }

    private fun login() {
        mIMyAidlInterface?.login("silencezwm", "123456");
    }
}