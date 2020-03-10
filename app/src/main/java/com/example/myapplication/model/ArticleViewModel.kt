package com.example.myapplication.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.base.Results
import com.example.myapplication.model.bean.ArticleList
import com.example.myapplication.model.bean.Banner
import com.example.myapplication.model.repository.HomeRepository
import com.example.myapplication.model.repository.SquareRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleViewModel(private val homeRepository: HomeRepository,private val squareRepository: SquareRepository
) : BaseViewModel() {


    sealed class ArticleType {
        object Home : ArticleType()                 // 首页
        object Square : ArticleType()                 // 广场
        object ProjectDetailList : ArticleType()                 // 首页
    }

    private val _uiState = MutableLiveData<ArticleUiModel>()
     val uiState: LiveData<ArticleUiModel>
        get() = _uiState

    private var currentPage = 0

    val mBanners:LiveData<List<Banner>> = liveData {

            val banners = homeRepository.getBanners()
            if (banners is Results.Success) emit(banners.data)

    }
    fun getHomeArticleList(isRefresh: Boolean = false) = getArticleList(ArticleType.Home, isRefresh)
    fun getSquareArticleList(isRefresh: Boolean = false) = getArticleList(ArticleType.Square, isRefresh)

    private fun getArticleList(articleType: ArticleViewModel.ArticleType, isRefresh: Boolean) {
        viewModelScope.launch (Dispatchers.Main){
            emitArticleUiState(true)
            if (isRefresh) currentPage = if (articleType is ArticleType.ProjectDetailList) 1 else 0
            val result = when (articleType) {
                ArticleType.Home -> homeRepository.getArticleList(currentPage)
                ArticleType.Square -> squareRepository.getSquareArticleList(currentPage)
                ArticleType.ProjectDetailList-> homeRepository.getArticleList(currentPage)
            }
            if (result is Results.Success) {
                val articleList = result.data
                if (articleList.offset >= articleList.total) {
                    emitArticleUiState(showLoading = false, showEnd = true)
                    return@launch
                }
                currentPage++
                emitArticleUiState(showLoading = false, showSuccess = articleList, isRefresh = isRefresh)

            } else if (result is Results.Error) {
                emitArticleUiState(showLoading = false, showError = result.exception.message)
            }
        }
    }
    private fun emitArticleUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        showSuccess: ArticleList? = null,
        showEnd: Boolean = false,
        isRefresh: Boolean = false,
        needLogin: Boolean? = null
    ) {
        val uiModel = ArticleUiModel(showLoading, showError, showSuccess, showEnd, isRefresh, needLogin)
        _uiState.value = uiModel
    }
    data class ArticleUiModel(
        val showLoading: Boolean,
        val showError: String?,
        val showSuccess: ArticleList?,
        val showEnd: Boolean, // 加载更多
        val isRefresh: Boolean, // 刷新
        val needLogin: Boolean? = null
    )
}