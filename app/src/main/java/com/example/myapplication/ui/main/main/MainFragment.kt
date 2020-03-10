package com.example.myapplication.ui.main.main

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.ipcclient.IpcClientActivity
import com.example.myapplication.ui.main.eventbustest.activity.EventBusMainActivity
import com.example.myapplication.ui.main.home.HomeFragment
import com.example.myapplication.ui.main.navigation.NavigationFragment
import com.example.myapplication.ui.main.project.ProjectTypeFragment
import com.example.myapplication.ui.main.square.SquareFragment
import com.example.myapplication.ui.main.system.SystemFragment
import com.example.myapplication.utils.Preference
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.*

class MainFragment :BaseFragment(){
    private val isLogin by Preference(Preference.IS_LOGIN,false)
    private val titleList = arrayOf("首页", "广场", "最新项目", "体系", "导航")
    private val fragmentList = arrayListOf<Fragment>()
    private val homeFragment by lazy { HomeFragment() } // 首页
    private val squareFragment by lazy { SquareFragment() } // 广场
    private val lastedProjectFragment by lazy { ProjectTypeFragment.newInstance(0, true) } // 最新项目
    private val systemFragment by lazy { SystemFragment() } // 体系
    private val navigationFragment by lazy { NavigationFragment() } // 导航
    private var mOnPageChangeCallback: ViewPager2.OnPageChangeCallback? = null
    override fun getLayoutResId(): Int {
       return R.layout.fragment_home
    }
    init {
        fragmentList.add(homeFragment)
        fragmentList.add(squareFragment)
        fragmentList.add(lastedProjectFragment)
        fragmentList.add(systemFragment)
        fragmentList.add(navigationFragment)
    }
    override fun initView() {
        initViewPager()
        addFab.setOnClickListener { v: View? ->
            val intent = Intent(context, EventBusMainActivity::class.java)
           startActivity(intent)
        }
    }

    private fun initViewPager() {
        viewPager.offscreenPageLimit=1
        viewPager.adapter =object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return titleList.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragmentList[position]
            }

        }

        TabLayoutMediator(tabLayout,viewPager){ tab, position ->
            tab.text = titleList[position]
        }.attach()
    }

    override fun initData() {

    }

    override fun onResume() {
        super.onResume()
        if (mOnPageChangeCallback == null) mOnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 1) addFab.show() else addFab.hide()
            }
        }
        mOnPageChangeCallback?.let { viewPager.registerOnPageChangeCallback(it) }
    }

    override fun onStop() {
        super.onStop()
        mOnPageChangeCallback?.let { viewPager.unregisterOnPageChangeCallback(it) }
    }
}
