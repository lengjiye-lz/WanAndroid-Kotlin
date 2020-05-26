package com.lengjiye.code.todo.activity

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import com.lengjiye.code.base.BaseActivity
import com.lengjiye.code.R
import com.lengjiye.code.databinding.ActivityTodoBinding
import com.lengjiye.code.project.adapter.ProjectFragmentItemAdapter
import com.lengjiye.code.todo.adapter.TodoAdapter
import com.lengjiye.code.todo.viewmodel.TodoViewModel
import com.lengjiye.code.utils.LayoutManagerUtils
import com.lengjiye.code.utils.ToolBarUtils
import com.lengjiye.tools.ResTool

/**
 * @Author: lz
 * @Date: 2020-05-25
 * @Description: TODO工具
 */
class TodoActivity : BaseActivity<ActivityTodoBinding, TodoViewModel>() {

    private val adapter by lazy { TodoAdapter(this, null) }

    private var page: Int = 1

    //状态， 1-完成；0未完成
    private var status: Int = 0
    private var type = TodoType.All

    private enum class TodoType(val type: String) {
        All("全部"),
        WORK("工作"),
        LIFE("生活"),
        RECREATION("娱乐")
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_todo
    }

    /**
     * 此界面不需要显示悬浮窗
     */
    override fun getBaseLayoutId(): Int? {
        return null
    }

    override fun initToolBar() {
        super.initToolBar()
        val toolbar = ToolBarUtils.Builder(findViewById(R.id.toolbar))
            .setType(ToolBarUtils.NORMAL_TYPE)
            .setNormalTitleColor(R.color.c_ff)
            .setCloseListener {
                finish()
            }
            .builder()
        this.setSupportActionBar(toolbar)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        addTabLayout()
        setDivider()
        mBinding.rlList.layoutManager = LayoutManagerUtils.verticalLinearLayoutManager(this)
        mBinding.rlList.adapter = adapter
    }

    override fun initLiveDataListener() {
        super.initLiveDataListener()
        mViewModel.todoBean.observe(this, Observer {
            page = it.curPage
            adapter.replaceAll(it.datas.toMutableList())
        })
    }

    private fun addTabLayout() {
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(TodoType.All.type))
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(TodoType.WORK.type))
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(TodoType.LIFE.type))
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(TodoType.RECREATION.type))
    }

    override fun initData() {
        super.initData()
        refresh()
    }

    private fun refresh() {
        page = 1
        status = 0
        type = TodoType.All
        loadData()
    }

    private fun loadData() {
        mViewModel.getTodoList(page, status, type.ordinal)
    }

    @SuppressLint("ResourceType")
    private fun setDivider() {
        val linearLayout = mBinding.tabLayout.getChildAt(0) as LinearLayout
        linearLayout.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        linearLayout.dividerDrawable = Drawable.createFromXml(resources, resources.getXml(R.drawable.tag_linearlayout_vertical_divider))
        linearLayout.dividerPadding = ResTool.getDimens(R.dimen.d_16)
    }
}
