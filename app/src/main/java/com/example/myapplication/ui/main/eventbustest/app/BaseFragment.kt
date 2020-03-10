package com.example.myapplication.ui.main.eventbustest.app

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.fragment.app.Fragment


/**
 *
 * @FileName:
 *          com.example.myapplication.ui.main.eventbustest.app.BaseFragment
 * @author: Tony Shen
 * @date: 2019-08-26 21:24
 * @version: V1.0 <描述当前版本功能>
 */
open class BaseFragment : Fragment() {

    /**
     * Fragment 所在的 FragmentActivity
     */
    var mContext: Activity? = null

}