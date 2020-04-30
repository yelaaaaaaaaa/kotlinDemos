package com.example.myapplication.base

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.myapplication.R


/**
 * Created by luyao
 * on 2019/10/25 13:16
 */
open class BaseBindAdapter<T>(layoutResId: Int, br: Int) : BaseQuickAdapter<T, BaseBindAdapter.BindViewHolder>(layoutResId),LoadMoreModule {

    private val _br: Int = br

    override fun convert(helper: BindViewHolder, item: T) {
        val binding: ViewDataBinding ?= DataBindingUtil.getBinding(helper.itemView)
        binding?.run {
            setVariable(_br, item)
            executePendingBindings()

        }
    }

    override fun onItemViewHolderCreated(viewHolder: BindViewHolder, viewType: Int) {
        DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
//        val binding = DataBindingUtil.inflate<ViewDataBinding>(mLayoutInflater, layoutResId, parent, false)
//            ?: return super.getItemView(layoutResId, parent)
//        return binding.root.apply {
//            setTag(R.id.BaseQuickAdapter_databinding_support, binding)
//        }
    }

    class BindViewHolder(view: View) : BaseViewHolder(view) {
           // get() = itemView.getTag(R.id.BaseQuickAdapter_databinding_support) as ViewDataBinding
    }
}