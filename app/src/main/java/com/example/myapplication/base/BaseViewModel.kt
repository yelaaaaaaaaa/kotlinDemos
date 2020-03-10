package com.example.myapplication.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

open class BaseViewModel:ViewModel(){
    open class BaseUiModel<T>(
        var showLoading: Boolean = false,
        var showError: String? = null,
        var showSuccess: T? = null,
        var showEnd: Boolean = false, // 加载更多
        var isRefresh: Boolean = false // 刷新
    )

    val mException : MutableLiveData<Throwable> =MutableLiveData()

    fun launchOnUI(block:suspend CoroutineScope.() ->Unit){
        viewModelScope.launch { block() }
    }
    fun launch(tryBlock: suspend CoroutineScope.() -> Unit) {
        launchOnUI {
            tryCatch(tryBlock, {}, {}, true)
        }
    }

    private suspend fun tryCatch(tryBlock: suspend CoroutineScope.() -> Unit,
                         catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
                         finallyBlock: suspend CoroutineScope.() -> Unit,
                         handleCancellationExceptionManually: Boolean = false) {
        coroutineScope{
            try {
                tryBlock()
            }catch (e : Throwable){
                if (e !is CancellationException || handleCancellationExceptionManually) {
                    mException.value = e
                    catchBlock(e)
                } else {
                    throw e
                }
            }finally {
                finallyBlock()
            }
        }
    }
}