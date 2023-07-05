package com.app

import android.annotation.SuppressLint
import android.content.Context
import org.litepal.LitePalApplication

/**
 * 继承 LitePalApplication() 是为了使用 LitePal
 */
class NewsApplication : LitePalApplication() {
    companion object {
        // 别担心，用全局的context不会内存泄露
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        // 聚合数据 https://www.juhe.cn/ 免费提供的 头条新闻API,
        const val KEY = "6f7d146652c8f2e84cc38f5859bbd807"
    }

    override fun onCreate() {
        super.onCreate()
        context = baseContext
    }
}