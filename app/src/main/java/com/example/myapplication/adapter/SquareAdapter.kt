package com.example.myapplication.adapter

import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.base.BaseBindAdapter
import com.example.myapplication.model.bean.Article

class SquareAdapter(layoutResId : Int = R.layout.item_square) :
    BaseBindAdapter<Article>(layoutResId, BR.article){
    override fun convert(helper: BindViewHolder, item: Article) {
        super.convert(helper, item)
    }
}