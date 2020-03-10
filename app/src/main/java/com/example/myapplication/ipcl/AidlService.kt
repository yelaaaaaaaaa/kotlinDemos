package com.example.myapplication.ipcl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.example.myapplication.IMyAidlInterface

class AidlService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        return MyBinder()
    }

    internal inner class MyBinder : IMyAidlInterface.Stub() {
        override fun login(username: String?, password: String?) {
            Log.d(TAG, "=====:login$username==$password");
        }

        @Throws(RemoteException::class)
        override fun basicTypes(
            anInt: Int,
            aLong: Long,
            aBoolean: Boolean,
            aFloat: Float,
            aDouble: Double,
            aString: String
        ) {
            Log.d(TAG, "=====:basicTypes")
        } //        @Override
//        public void login(String username, String password) throws RemoteException {
//            Log.d(TAG, "=====:login" + username + "==" + password);
//        }
    }

    companion object {
        private val TAG = AidlService::class.java.name
    }
}