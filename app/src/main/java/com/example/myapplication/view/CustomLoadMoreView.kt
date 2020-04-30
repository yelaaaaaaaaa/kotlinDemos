package com.example.myapplication.view


import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView
import com.chad.library.adapter.base.util.getItemView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.myapplication.R


/**
 * Created by Lu
 * on 2018/3/21 22:14
 */
class CustomLoadMoreView : BaseLoadMoreView() {
    override fun getLoadComplete(holder: BaseViewHolder): View {
        return holder.getView(R.id.load_more_load_end_view)
    }
//    override fun getLayoutId(): Int {
//        return R.layout.view_load_more
//    }
//
//    override fun getLoadingViewId(): Int {
//        return R.id.load_more_loading_view
//    }
//
//    override fun getLoadFailViewId(): Int {
//        return R.id.load_more_load_fail_view
//    }
//
//    override fun getLoadEndViewId(): Int {
//        return R.id.load_more_load_end_view
//    }

    override fun getLoadEndView(holder: BaseViewHolder): View {
        return holder.getView(R.id.load_more_load_end_view)
    }

    override fun getLoadFailView(holder: BaseViewHolder): View {
        return holder.getView(R.id.load_more_load_fail_view)
    }

    override fun getLoadingView(holder: BaseViewHolder): View {
        return holder.getView(R.id.load_more_loading_view)
    }

    override fun getRootView(parent: ViewGroup): View {
        return parent.getItemView(R.layout.view_load_more)
       // return parent.addView(R.layout.view_load_more)
    }
}