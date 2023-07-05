package com.app.ui.adapter

import com.app.R
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.NewsApplication
import com.app.model.News
import com.app.ui.activity.DetailActivity
import java.lang.StringBuilder

internal class CollectAdapter(private val context: Context, private val list: List<News>) :
    RecyclerView.Adapter<CollectAdapter.MyViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(
                context
            ).inflate(
                R.layout.news_item_one_image, parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val news = list[position]
        // (1)显示新闻标题
        holder.title.text = news.title
        // (2)显示新闻描述:用 author_name和 date两个字段拼接出来
        val stringBuilder = StringBuilder()
        stringBuilder.append(news.author_name)
            .append("      ").append(news.date)
        holder.mytext.text = stringBuilder.toString()
        Log.d("emmmm",holder.title.text.toString())
        Log.d("nani",holder.mytext.text.toString())
        //列表项点击事件
        holder.itemView.setOnClickListener {
            val intent = Intent(NewsApplication.context, DetailActivity::class.java)
            val currentNews = list[holder.adapterPosition]
            intent.putExtra("news_from=", currentNews.author_name)
            intent.putExtra("url=", currentNews.url)
            intent.putExtra("id=",currentNews.id)//用于收藏
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            ContextCompat.startActivity(NewsApplication.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * ViewHolder的类，用于缓存控件
     */
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView= view.findViewById(R.id.news_title)
        var mytext: TextView= view.findViewById(R.id.news_desc)
    }


}