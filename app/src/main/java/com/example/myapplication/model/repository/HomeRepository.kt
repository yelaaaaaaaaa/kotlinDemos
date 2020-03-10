package com.example.myapplication.model.repository

import com.example.myapplication.base.BaseRepository
import com.example.myapplication.base.MyRetrofit
import com.example.myapplication.base.Results
import com.example.myapplication.model.bean.ArticleList
import com.example.myapplication.model.bean.Banner

class HomeRepository:BaseRepository(){
    suspend fun getBanners(): Results<List<Banner>> {
        return safeApiCall(call = {requestBanners()},errorMessage = "")
    }

    private suspend fun requestBanners(): Results<List<Banner>> {
       return executeResponse(MyRetrofit.service.getBanner())
    }

    suspend fun getArticleList(currentPage: Int): Results<ArticleList> {
        return safeApiCall(call = {requestArticleList(currentPage)},errorMessage = "")
    }
    private suspend fun requestArticleList(currentPage: Int): Results<ArticleList> {
        return executeResponse(MyRetrofit.service.getHomeArticles(currentPage))
    }
}