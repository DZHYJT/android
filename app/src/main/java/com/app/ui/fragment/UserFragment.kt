package com.app.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.app.NewsApplication
import com.app.R
import com.app.ui.activity.CollectActivity
import com.app.ui.activity.ModifyPswActivity

//import com.app.ui.activity.CollectActivity


class UserFragment : Fragment() {

    private val newsTypes1 = arrayOf(
        "shehui", "guonei", "guoji", "yule",
        "tiyu", "junshi", "keji", "caijing", "shishang"
    )
    private val newsTypes2 = arrayOf(
        "社会", "国内", "国际", "娱乐",
        "体育", "军事", "科技", "财经", "时尚"
    )

    private lateinit var changePassword: Button
    private lateinit var Mycollect:Button//收藏
    //private lateinit var table: SmartTable<RequestCount>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        changePassword = view.findViewById(R.id.button1)
        Mycollect = view.findViewById(R.id.Mycollect)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //跳转到修改密码页面
        changePassword.setOnClickListener {
            val intent = Intent(NewsApplication.context, ModifyPswActivity::class.java)
            startActivity(intent)
        }

        //跳转到收藏新闻列表页面
        Mycollect.setOnClickListener {
            val intent=Intent(NewsApplication.context, CollectActivity::class.java)
            startActivity(intent)
        }
    }
}