package com.app.ui.activity


import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.NewsApplication
import com.app.R
import com.app.custom.DBHelper
import com.app.model.News
import com.app.ui.adapter.CollectAdapter
import com.google.android.material.tabs.TabLayout
import org.litepal.LitePal


class CollectActivity:AppCompatActivity() {
    private lateinit var mRecyclerView: RecyclerView
    private val adapter: CollectAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collect)
        initRecycle()
    }

    private fun showList():List<News>{
        //数据库
        val dbsqLiteOpenHelper = DBHelper(this@CollectActivity, "collection.db", null, 1)
        var db: SQLiteDatabase = dbsqLiteOpenHelper.getWritableDatabase()

        var list= listOf<News>()
        val cursor = db.query(
            "collection",
            arrayOf("id","new","state"),
            "state=?",
            arrayOf("1"),
            null,
            null,
            null
        )

        while(cursor.moveToNext()){
            var id = cursor.getString(cursor.getColumnIndex("new"))
            Log.d("id?",id)
            var getNews=LitePal.where("id=?", id)//按照category查找项
                .limit(1)//指定查询个数
                .find(News::class.java)// 返回的对象的类
            list=list.plusElement(getNews[0])
            Log.d("getNews[0]:",getNews[0].toString())
            Log.d("list:",list.toString())
        }
        return list
    }

    private fun initRecycle() {
        mRecyclerView= findViewById(R.id.show_recycler_view)
        //  纵向滑动
        var linearLayoutManager : LinearLayoutManager=  LinearLayoutManager(NewsApplication.context)
        mRecyclerView.layoutManager = linearLayoutManager

        var list = showList()
        Log.d("search:",list.toString())
        var adapter =  CollectAdapter(this, list)
        mRecyclerView.adapter = adapter
        mRecyclerView.setItemAnimator(DefaultItemAnimator())
    }

}