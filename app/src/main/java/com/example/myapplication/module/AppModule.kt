package com.example.myapplication.module

import com.example.myapplication.base.MyRetrofit
import com.example.myapplication.base.NetService
import com.example.myapplication.model.ArticleViewModel
import com.example.myapplication.model.repository.HomeRepository
import com.example.myapplication.model.repository.SquareRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { ArticleViewModel(get(),get()) }
}
val repositoryModule = module {
    single { MyRetrofit.getService(NetService::class.java, NetService.BASE_URL) }
    single { HomeRepository() }
    single { SquareRepository() }
}
val appModule = listOf(viewModelModule, repositoryModule)