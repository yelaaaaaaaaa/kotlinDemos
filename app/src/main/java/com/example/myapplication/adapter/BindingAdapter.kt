package com.example.myapplication.adapter

import android.os.Build
import android.text.Html
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("htmlText")
fun bindHtmlText(textView: TextView,text:String){
    textView.text =if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
       Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(text)
    }
}