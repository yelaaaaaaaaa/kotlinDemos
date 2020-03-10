package com.example.myapplication.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DBUser::class],version = 1)
abstract class UserDataBase :RoomDatabase(){
    abstract val userDao:UserDao?

    companion object{
        const val DB_NAME = "db_user"
        private var instance :UserDataBase? = null
        @Synchronized
        fun getInstance(context: Context?):UserDataBase?{
            if (instance==null){
                instance = Room.databaseBuilder(context!!,UserDataBase::class.java, DB_NAME)
                    .allowMainThreadQueries() //默认room不允许在主线程操作数据库，这里设置允许
                     .build()
            }
          return  instance
        }
    }
}