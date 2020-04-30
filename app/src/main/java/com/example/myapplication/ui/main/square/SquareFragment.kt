package com.example.myapplication.ui.main.square

import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.SquareAdapter
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.base.BaseVmFragment
import com.example.myapplication.ext.dp2px
import com.example.myapplication.model.ArticleViewModel
import com.example.myapplication.ui.main.BrowserActivity
import com.example.myapplication.view.CustomLoadMoreView
import com.example.myapplication.view.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_new_home.*
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class SquareFragment : BaseVmFragment<ArticleViewModel>(){
    private val mViewModel:ArticleViewModel by viewModel()
    private val squareAdapter by lazy { SquareAdapter() }
    override fun getLayoutResId(): Int {
        return R.layout.fragment_square
    }

    override fun initView() {
        initRecycleView()
    }

    override fun initData() {
        refresh()
    }
    private fun loadMore() {
        mViewModel.getSquareArticleList(false)
    }

    fun refresh() {
        mViewModel.getSquareArticleList(true)
    }
    override fun startObserve() {
        mViewModel.apply {
            uiState.observe(this@SquareFragment, Observer { it->
                homeRefreshLayout.isRefreshing = it.isRefresh

                it.showSuccess?.let { list ->
                    squareAdapter.run {
                        if (it.isRefresh) replaceData(list.datas)
                        else addData(list.datas)
                        loadMoreModule.isEnableLoadMore = true
                       // setEnableLoadMore(true)
                        loadMoreModule.loadMoreComplete()
//                        loadMoreComplete()
                    }
                }
                if (it.showEnd) squareAdapter.loadMoreModule.loadMoreEnd()

                it.showError?.let { message ->
                    activity?.toast(if (message.isBlank()) "网络异常" else message)
                }
            })
        }
    }
    private fun initRecycleView() {
        squareAdapter.run {
            setOnItemClickListener{ _, _, position->
                val bundle = bundleOf(BrowserActivity.URL to squareAdapter.data[position].link)
                Navigation.findNavController(homeRecycleView).navigate(R.id.action_tab_to_browser,bundle)
            }
            loadMoreModule.loadMoreView = CustomLoadMoreView()
//            setLoadMoreView(CustomLoadMoreView())
//            setOnLoadMoreListener({ loadMore() }, homeRecycleView)
            loadMoreModule.setOnLoadMoreListener {loadMore()}
        }
        homeRecycleView.run{
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(SpaceItemDecoration(homeRecycleView.dp2px(10)))
            adapter = squareAdapter
        }
    }
}