package com.example.myapplication.model.repository

import com.example.myapplication.base.BaseRepository
import com.example.myapplication.base.MyRetrofit
import com.example.myapplication.base.Results
import com.example.myapplication.model.bean.ArticleList

class SquareRepository :BaseRepository(){
   suspend fun getSquareArticleList(currentPage: Int): Results<ArticleList> {
        return safeApiCall(call = {requestSquareArticleList(currentPage)},errorMessage = "")
    }

    private suspend fun requestSquareArticleList(currentPage: Int): Results<ArticleList> {
        return executeResponse(MyRetrofit.service.getSquareArticleList(currentPage))
    }

}