package com.example.myapplication.ui.main.eventbustest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.myapplication.R
import com.example.myapplication.eventbus.EventBus
import com.example.myapplication.ui.main.eventbustest.app.BaseFragment
import com.example.myapplication.ui.main.eventbustest.domain.Fragment2Event

/**
 *
 * @FileName:
 *          com.example.myapplication.ui.main.eventbustest.fragment.Fragment2
 * @author: Tony Shen
 * @date: 2019-08-26 21:27
 * @version: V1.0 <描述当前版本功能>
 */
class Fragment2 : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_2, container, false)

        v.findViewById<Button>(R.id.button2).setOnClickListener {

            EventBus.post(Fragment2Event())
        }

        return v
    }
}