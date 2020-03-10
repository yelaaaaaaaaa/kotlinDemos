package com.example.myapplication.ui.main.home

import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.myapplication.R
import com.example.myapplication.adapter.HomeArticleAdapter
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.base.BaseVmFragment
import com.example.myapplication.ext.dp2px
import com.example.myapplication.ipcclient.IpcClientActivity
import com.example.myapplication.model.ArticleViewModel
import com.example.myapplication.model.bean.Banner
import com.example.myapplication.ui.main.BrowserActivity
import com.example.myapplication.utils.GlideImageLoader
import com.example.myapplication.utils.Preference
import com.example.myapplication.view.CustomLoadMoreView
import com.example.myapplication.view.SpaceItemDecoration
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_new_home.*
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment :BaseVmFragment<ArticleViewModel>(){
    private val mViewModel:ArticleViewModel by viewModel()
    private val isLogin by Preference(Preference.IS_LOGIN,false)
    private val bannerImages = mutableListOf<String>()
    private val bannerTitles = mutableListOf<String>()
    private val bannerUrls = mutableListOf<String>()
    private val banner by lazy { com.youth.banner.Banner(activity) }
    private val homeArticleAdapter by lazy { HomeArticleAdapter() }
    override fun getLayoutResId(): Int {
        return R.layout.fragment_new_home
    }


    override fun initView() {
        initRecycleView()
        initBanner()
        homeRefreshLayout.run {
            setOnRefreshListener { refresh() }
        }
    }

    private fun initBanner() {
        banner.run {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,banner.dp2px(200))
            setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
            setImageLoader(GlideImageLoader())
            setOnBannerListener { position ->
                run {
                    Navigation.findNavController(banner).navigate(R.id.action_tab_to_browser, bundleOf(BrowserActivity.URL to bannerUrls[position]))
                }
            }
        }
    }

    private fun initRecycleView() {
        homeRecycleView.run {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(SpaceItemDecoration(homeRecycleView.dp2px(10)))
        }
        homeArticleAdapter.run {
            setOnItemClickListener(){ _, _, position->
                val bundle = bundleOf(BrowserActivity.URL to homeArticleAdapter.data[position].link)
                Navigation.findNavController(homeRecycleView).navigate(R.id.action_tab_to_browser,bundle)
            }
            onItemChildClickListener = this@HomeFragment.onItemChildClickListener
            if (headerLayoutCount > 0) removeAllHeaderView()
            addHeaderView(banner)
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({loadMore()},homeRecycleView)
        }
        homeRecycleView.run{
            adapter = homeArticleAdapter
        }
    }

    private fun loadMore() {
        mViewModel.getHomeArticleList(false)
    }

    private val onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
        when (view.id) {
            R.id.articleStar -> {
                if (isLogin) {
                    homeArticleAdapter.run {
                        data[position].run {
                            collect = !collect
                           // mViewModel.collectArticle(id, collect)
                        }
                        notifyDataSetChanged()
                    }
                } else {
                    Navigation.findNavController(homeRecycleView).navigate(R.id.action_tab_to_login)
                }
            }
        }
    }
    override fun initData() {
        refresh()
    }
    fun refresh() {
        homeArticleAdapter.setEnableLoadMore(false)
        mViewModel.getHomeArticleList(true)
    }
    override fun startObserve() {
        mViewModel.apply {
            mBanners.observe(this@HomeFragment, Observer { it ->
                it?.let{setBanner(it)}
            })
            uiState.observe(this@HomeFragment, Observer { it->
                homeRefreshLayout.isRefreshing = it.isRefresh

                it.showSuccess?.let { list ->
                    homeArticleAdapter.run {
                        if (it.isRefresh) replaceData(list.datas)
                        else addData(list.datas)
                        setEnableLoadMore(true)
                        loadMoreComplete()
                    }
                }
                if (it.showEnd) homeArticleAdapter.loadMoreEnd()

                it.showError?.let { message ->
                    activity?.toast(if (message.isBlank()) "网络异常" else message)
                }
            })
        }
    }

    private fun setBanner(bannerList: List<Banner>) {
       for (item in bannerList){
            bannerImages.add(item.imagePath)
           bannerTitles.add(item.title)
           bannerUrls.add(item.url)
       }
        banner.setImages(bannerImages)
            .setBannerTitles(bannerTitles)
            .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
            .setDelayTime(3000).start()

    }


}