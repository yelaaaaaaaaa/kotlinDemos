package com.example.myapplication.base

import com.example.myapplication.model.bean.ArticleList
import com.example.myapplication.model.bean.Banner
import com.example.myapplication.model.bean.WanResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface NetService {
    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }
    @GET("/banner/json")
    suspend fun getBanner(): WanResponse<List<Banner>>

    @GET("/article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): WanResponse<ArticleList>

    @GET("/user_article/list/{page}/json")
    suspend fun getSquareArticleList(@Path("page") page: Int): WanResponse<ArticleList>
}