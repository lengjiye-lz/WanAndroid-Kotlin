package com.lengjiye.code.todo.activity

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.lengjiye.code.base.BaseActivity
import com.lengjiye.code.R
import com.lengjiye.code.constant.IntentKey
import com.lengjiye.code.databinding.ActivityTodoBinding
import com.lengjiye.code.todo.adapter.TodoAdapter
import com.lengjiye.code.todo.viewmodel.TodoViewModel
import com.lengjiye.code.utils.LayoutManagerUtils
import com.lengjiye.code.utils.ToolBarUtils
import com.lengjiye.code.utils.startActivity
import com.lengjiye.code.widgets.MyItemTouchHelperCallback
import com.lengjiye.tools.ResTool
import com.lengjiye.tools.log.log
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.MaterialHeader

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

    private var itemHelperCallback: MyItemTouchHelperCallback? = null

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
            .setNormalTitle(R.string.s_39)
            .setNormalTitleColor(R.color.c_ff)
            .setBackListener {
                finish()
            }
            .setMoreRes(R.drawable.ic_add_circle_ffffff_24dp)
            .setMoreListener {
                startActivity<AddTodoActivity>()
            }
            .builder()
        this.setSupportActionBar(toolbar)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        addTabLayout()
        mBinding.srlLayout.setRefreshHeader(MaterialHeader(this))
        mBinding.srlLayout.setRefreshFooter(BallPulseFooter(this))

        mBinding.srlLayout.setOnRefreshListener {
            refresh()
        }

        mBinding.srlLayout.setOnLoadMoreListener {
            loadData()
        }

        mBinding.rlList.layoutManager = LayoutManagerUtils.verticalLinearLayoutManager(this)
        itemHelperCallback = MyItemTouchHelperCallback(adapter)
        itemHelperCallback?.let {
            ItemTouchHelper(it).attachToRecyclerView(mBinding.rlList)
        }
        mBinding.rlList.adapter = adapter

        mBinding.rgGroup.setOnCheckedChangeListener { group, checkedId ->
            status = when (checkedId) {
                R.id.rb_no_suc -> {
                    0
                }
                R.id.rb_suc -> {
                    1
                }
                else -> {
                    0
                }
            }
            refresh()
        }

        adapter.setOnItemClickListener { v, position, item ->
            startActivity<AddTodoActivity>(Bundle().apply {
                putParcelable(IntentKey.KEY_OBJECT, item)
            })
        }

    }

    override fun initLiveDataListener() {
        super.initLiveDataListener()
        mViewModel.todoBean.observe(this, Observer {
            if (it.over) {// 没有更多数据
                mBinding.srlLayout.setEnableLoadMore(false)
            }
            itemHelperCallback?.resetViewLocation()
            if (page == 1) {
                mBinding.srlLayout.finishRefresh()
                adapter.replaceAll(it.datas.toMutableList())
            } else {
                mBinding.srlLayout.finishLoadMore()
                adapter.addAll(it.datas.toMutableList())
            }
            page = it.curPage
        })
    }

    private fun addTabLayout() {
        setDivider()
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(TodoType.All.type))
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(TodoType.WORK.type))
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(TodoType.LIFE.type))
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(TodoType.RECREATION.type))
        mBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab?.position
                type = TodoType.values()[position!!]
                refresh()
            }
        })
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            // 不处理拖动事件
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            log("direction:$direction")
            // 滑动事件
            val position = viewHolder.adapterPosition
            adapter.getItems().removeAt(position)
            adapter.notifyItemRemoved(position)
            adapter.notifyDataSetChanged()
        }
    })

    override fun initData() {
        super.initData()
        type = TodoType.All
        status = 0
        refresh()
    }

    private fun refresh() {
        page = 1
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
