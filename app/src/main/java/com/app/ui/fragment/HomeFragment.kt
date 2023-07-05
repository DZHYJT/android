package com.app.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.app.NewsApplication
import com.app.R
import com.app.ui.activity.MainActivity
import com.app.util.showToast
import com.google.android.material.tabs.TabLayout
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class HomeFragment : Fragment() {

    // 类型,shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
    private val newsTypeList = listOf("shehui", "guoji", "yule", "keji", "tiyu", "caijing")
    private val titleList = listOf("社会", "国际", "娱乐", "科技", "体育", "财经")

    // 初始化一个空fragment列表
    private val fragmentList = ArrayList<NewsFragment>()

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var toolbar: Toolbar
    private lateinit var avatar: CircleImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        tabLayout = view.findViewById(R.id.news_tab_layout)
        viewPager = view.findViewById(R.id.news_view_pager)
        toolbar = view.findViewById(R.id.home_tool_bar)
        avatar = view.findViewById(R.id.avatar)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 动态加载菜单
        toolbar.inflateMenu(R.menu.home_tool_bar_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.full_screen -> "全屏".showToast()
            }
            false
        }

        // 设置头像点击事件
        avatar.setOnClickListener {
            // TODO 跳到MainActivity的导航栏的第三栏：我的页面
            val intent = Intent(NewsApplication.context, MainActivity::class.java)
            intent.putExtra("id", 2)
            startActivity(intent)
        }

        // 向fragmentList添加一堆不同类别的新闻碎片对象
        for (i in newsTypeList.indices) {
            fragmentList.add(NewsFragment(newsTypeList[i], titleList[i]))
        }

        // 设置缓存数量！！！！！
        viewPager.offscreenPageLimit = titleList.size
        viewPager.adapter = Adapter(childFragmentManager)

        // 实现viewPager左右滑动与tabLayout这个标签选择器的联动
        tabLayout.setupWithViewPager(viewPager)

    }

    inner class Adapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getPageTitle(position: Int): CharSequence {
            // 设置标题
            return titleList[position]
        }

    }
}