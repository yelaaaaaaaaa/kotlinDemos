package com.example.myapplication.room

import android.view.View
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import kotlinx.android.synthetic.main.activity_room.*

class RoomDataBaseActivity : BaseActivity() {

    val instance:UserDataBase? by lazy { UserDataBase.getInstance(baseContext) }
    var userDao:UserDao? = null
    private val all: Unit
        get() {
            val all = userDao!!.getAll() ?: return
            val sb = StringBuilder()
            all.forEach { user ->
                sb.append("uid: ")
                    .append(user?.uid)
                    .append("姓名: ")
                    .append(user?.name)
                    .append("年龄: ")
                    .append(user?.age)
                    .append("城市: ")
                    .append(user?.city)
                    .append("Single: ")
                    .append(user?.isSingle)
                    .append("\n")
            }
            val text = "All Size ： " + all.size
            tv_size_room.text = text
            tv_all_room.text = sb.toString()
        }
    override fun initData() {
        deleteDatabase(UserDataBase.DB_NAME) //删除数据库，属于contextWrapper中的函数
        userDao = instance?.userDao
    }

    override fun initView() {

    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_room
    }
    fun insert(view: View?) { //        List<DbUser> users = new ArrayList<>();
        val sb = StringBuilder()
        var user: DBUser
        for (i in 0..4) {
            user = DBUser()
            user.age = 20 + i
            user.city = "北京 $i"
            user.name = "小明 $i"
            user.isSingle = i % 2 == 0
            userDao!!.insertAll(user)
            sb.append(user.toString()).append("\n")
        }
        //        userDao.insertAll(users.get(0),users.get(1));
        tv_insert_room.text = sb.toString()
        all
    }
    fun delete(view: View?) {
        val user = userDao!!.findByName("小明 " + 3, 23)
        if(user!=null) {
            userDao!!.delete(user)
            //
            tv_delete_room.text = user.toString()
            all
        }
    }

    fun update(view: View?) {
        val user = userDao!!.findByName("小明 " + 2, 22) ?: return
        user.age = 33
        user.city = "上海"
        user.name = "张三"
        user.isSingle = true
        userDao!!.update(user)
        tv_update_room.text = user.toString()
        all
    }

    fun queryId(view: View?) {
        val userById = userDao!!.getUserById(3)
        if (userById != null) {
            tv_query_id_room.text = userById.toString()
        } else {
            Toast.makeText(this, "id=3 的user查询不到", Toast.LENGTH_SHORT).show()
        }
        //为了显示操作后的更新数据
        all
    }

    fun queryAll(view: View?) {
        all
    }
}