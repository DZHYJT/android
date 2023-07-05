package com.app.ui.activity

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.app.R
import com.app.custom.DBHelper
import com.app.model.News
import org.litepal.LitePal


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //数据库
        val dbsqLiteOpenHelper = DBHelper(this@DetailActivity, "collection.db", null, 1)
        var db: SQLiteDatabase = dbsqLiteOpenHelper.getWritableDatabase()

        // 设置标题栏
        val toolbar = findViewById<Toolbar>(R.id.detail_tool_bar)
        setSupportActionBar(toolbar)

        //获取当前的事件的收藏状态,初始化收藏图标
        var collects=findViewById<ImageView>(R.id.collects)
        //获取传入的参数：新闻ID
        var ItemId = intent.getLongExtra("id=",0).toString()
        Log.d("id:",ItemId)

        //查
        var choice=0
        val cursor = db.query(
            "collection",
            arrayOf("id","new","state"),
            "new=?",
            arrayOf("${ItemId}"),
            null,
            null,
            null
        )
        var flag=0
        while(cursor.moveToNext()){
            flag=1
            choice=cursor.getInt(cursor.getColumnIndex("state"))
            Log.d("choice1:",choice.toString())
        }
        if(flag==0){
            var values : ContentValues= ContentValues()
            values.put("state",0)
            values.put("new","${ItemId}")
            db.insert("collection", null, values)
        }
        Log.d("choice2:",choice.toString())
        Log.d("cursor:",cursor.toString())
        cursor.close()// 关闭游标，释放资源

        if(choice==1){
            collects.setImageResource(R.drawable.collecting)
        }else if(choice==0){
            collects.setImageResource(R.drawable.collect)
        }

        collects.setOnClickListener(View.OnClickListener { v ->
            var value:ContentValues= ContentValues()
            if (v === collects) {
                if (choice==1) {
                    collects.setImageResource(R.drawable.collect)
                    value.put("state",0)
                    db.update("collection", value, "new=?", arrayOf("${ItemId}"))
                    choice=0
                } else {
                    collects.setImageResource(R.drawable.collecting)
                    value.put("state",1)
                    db.update("collection", value, "new=?", arrayOf("${ItemId}"))
                    choice=1
                }
            }
        })

        // 去掉默认的标题
        title = ""
        // 设置标题的居中文字
        val realTitle = findViewById<TextView>(R.id.detail_real_title)
        realTitle.text = intent.getStringExtra("news_from=")
        //
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 打开新闻网页
        val webView = findViewById<WebView>(R.id.news_web_view)
        val url = intent.getStringExtra("url=")
        if (url != null) {
            webView.loadUrl(url)
        }
    }

    // 给Activity加载标题栏的菜单项
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //写一个menu的资源文件.然后创建就行了.
        menuInflater.inflate(R.menu.detail_tool_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //给系统自带的home按钮(小箭头)添加点击事件：销毁本页面返回上一级
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


}