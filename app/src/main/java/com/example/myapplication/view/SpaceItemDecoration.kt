package com.example.myapplication.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Lu
 * on 2018/3/15 21:45
 */
class SpaceItemDecoration(space: Int) : RecyclerView.ItemDecoration() {

    private val mSpace = space

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = 0
        outRect.bottom = mSpace
    }

//    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
//        outRect?.top = 0
//        outRect?.bottom = mSpace
//    }
}