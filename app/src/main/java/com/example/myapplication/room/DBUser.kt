package com.example.myapplication.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "db_user")
class DBUser {
    @PrimaryKey(autoGenerate = true)
    var uid = 0
    @ColumnInfo(name = "uname")
    var name: String? = null
    @ColumnInfo
    var city: String? = null
    @ColumnInfo
    var age = 0
    //如此数据表中不会有@Ignore标记的属性字段
    @Ignore
    var isSingle = false

    override fun toString(): String {
        return "DbUser{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", age=" + age +
                ", single=" + isSingle +
                '}'
    }
}