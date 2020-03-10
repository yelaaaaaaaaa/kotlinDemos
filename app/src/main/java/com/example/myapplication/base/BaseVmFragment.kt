package com.example.myapplication.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseVmFragment<VM : BaseViewModel>(useBinding : Boolean = false) : Fragment(){
    private val _useBinding = useBinding
    private lateinit var mBinding:ViewDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      return if (_useBinding){
           mBinding = DataBindingUtil.inflate(inflater,getLayoutResId(),container,false)
           mBinding.root
       }else{
           inflater.inflate(getLayoutResId(), container, false)
       }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initData()
        startObserve()
        super.onViewCreated(view, savedInstanceState)
    }

    abstract fun getLayoutResId(): Int
    abstract fun initView()
    abstract fun initData()
    abstract fun startObserve()
}