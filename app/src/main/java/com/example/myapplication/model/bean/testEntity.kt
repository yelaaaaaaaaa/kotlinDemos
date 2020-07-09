package com.example.myapplication.model.bean

/**
 * Created by  On 2020/6/9
 */
data class testEntity(
    val code: Int,
    val data: List<Data>,
    val message: String
)

data class Data(
    val backImg: String,
    val branchBank: String,
    val cardNo: String,
    val icon: String,
    val id: Int,
    val name: String,
    val openBank: String,
    val userId: Int
)