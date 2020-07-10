package com.lengjiye.code.todo.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.lengjiye.code.R
import com.lengjiye.code.base.BaseActivity
import com.lengjiye.code.constant.IntentKey
import com.lengjiye.code.databinding.ActivityAddTodoBinding
import com.lengjiye.code.todo.bean.TodoData
import com.lengjiye.code.todo.viewmodel.AddTodoViewModel
import com.lengjiye.code.utils.ToolBarUtils
import com.lengjiye.code.widgets.dialog.CurrencyDialog
import com.lengjiye.tools.DateTimeTool


/**
 * @Author: lz
 * @Date: 2020-05-25
 * @Description: 添加ODO工具
 */
class AddTodoActivity : BaseActivity<ActivityAddTodoBinding, AddTodoViewModel>(), TextWatcher {

    private var todoData: TodoData? = null

    private var type: Int? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_add_todo
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
            .setNormalTitle(R.string.s_44)
            .setNormalTitleColor(R.color.c_ff)
            .setBackListener {
                showDialog()
            }
            .builder()
        this.setSupportActionBar(toolbar)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        //时间选择器
        val pvTime = TimePickerBuilder(this@AddTodoActivity,
            OnTimeSelectListener { date, v ->
                mBinding.edTime.text = DateTimeTool.getFormatDate(date)
            }).addOnCancelClickListener {
            setSubmitEnable()
        }.build()
        mBinding.edTime.setOnClickListener {
            pvTime.show()
        }

        mBinding.btnSubmit.setOnClickListener {
            if (todoData == null) {
                mViewModel.addTodo(mBinding.edTitle.text.toString(), mBinding.edContent.text.toString(), mBinding.edTime.text.toString(), type)
            } else {
                todoData?.dateStr = mBinding.edTime.text.toString()
                todoData?.content = mBinding.edContent.text.toString()
                todoData?.title = mBinding.edTitle.text.toString()
                mViewModel.updateTodo(todoData!!)
            }
        }

        mBinding.edTitle.addTextChangedListener(this)
        mBinding.edContent.addTextChangedListener(this)
    }

    private fun setSubmitEnable() {
        mBinding.btnSubmit.isEnabled = !(mBinding.edTitle.text.isNullOrEmpty() || mBinding.edContent.text.isNullOrEmpty() || mBinding.edTime.text.isNullOrEmpty())
    }

    override fun initIntent(savedInstanceState: Bundle?) {
        super.initIntent(savedInstanceState)
        todoData = intent?.extras?.getParcelable(IntentKey.KEY_OBJECT)
        type = intent?.extras?.getInt(IntentKey.KEY_TYPE)
    }

    override fun initLiveDataListener() {
        super.initLiveDataListener()
        mViewModel.operateResult.observe(this, Observer {
            if (it) finish()
        })
    }

    override fun initData() {
        super.initData()
        todoData?.let {
            mBinding.edTitle.setText(it.title)
            mBinding.edContent.setText(it.content)
            mBinding.edTime.text = it.dateStr
        }
    }

    override fun afterTextChanged(s: Editable?) {
        setSubmitEnable()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    private fun showDialog() {
        CurrencyDialog.Builder().setContent("sdcsdc").setSubmitListener { view, dialog ->
            dialog.dismiss()
            finish()
        }.build().show(supportFragmentManager)
    }
}
