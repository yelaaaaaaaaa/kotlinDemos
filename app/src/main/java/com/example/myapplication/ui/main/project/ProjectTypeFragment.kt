package com.example.myapplication.ui.main.project

import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment

class ProjectTypeFragment :BaseFragment(){
    override fun getLayoutResId(): Int {
        return R.layout.activity_bagua
    }
    companion object {
        private const val CID = "projectCid"
        private const val LASTED = "lasted"
        fun newInstance(cid: Int, isLasted: Boolean): ProjectTypeFragment {
            val fragment = ProjectTypeFragment()
            val bundle = Bundle()
            bundle.putInt(CID, cid)
            bundle.putBoolean(LASTED, isLasted)
            fragment.arguments = bundle
            return fragment
        }
    }
    override fun initView() {

    }

    override fun initData() {

    }
}