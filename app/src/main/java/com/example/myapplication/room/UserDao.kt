package com.example.myapplication.room

import androidx.room.*

@Dao
interface UserDao {
    //查询所有数据，若返回liveData则为 LiveData<List<DbUser>>
    @Query(value = "select * from db_user")
    fun getAll(): List<DBUser?>?

    @Query("SELECT * FROM db_user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray?): List<DBUser?>? //根据uid查询

    @Query(
        "SELECT * FROM db_user WHERE uname LIKE :name AND "
                + "age LIKE :age LIMIT 1"
    )
    fun findByName(name: String?, age: Int): DBUser?

    @Query("select * from db_user where uid like :id")
    fun getUserById(id: Int): DBUser?

    @Insert
    fun insertAll(vararg users: DBUser?) //支持可变参数

    @Delete
    fun delete(user: DBUser?) //删除指定的user

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(user: DBUser?) //更新，若出现冲突，则使用替换策略，还有其他策略可选择
}
