package com.lengjiye.code.test

import com.lengjiye.code.R
import com.lengjiye.code.base.BaseActivity
import com.lengjiye.code.databinding.ActivityInjectBinding
import com.lengjiye.code.databinding.ActivityLoginBinding
import com.lengjiye.code.login.viewmodel.LoginViewModel
import com.lengjiye.code.todo.bean.TestBean
import com.lengjiye.code.utils.ToolBarUtils
import com.lengjiye.code.utils.inject.InjectExtras
import com.lengjiye.code.utils.inject.InjectUtils
import com.lengjiye.tools.log.log

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description: 测试
 */
class InjectActivity : BaseActivity<ActivityInjectBinding, LoginViewModel>() {

    @InjectExtras("test1")
    private var name: String? = null

    @InjectExtras("test2")
    private var test2: Int? = null

    @InjectExtras("test3")
    private var test3: Boolean? = null

    @InjectExtras("test4")
    private var test4: Float? = null

    @InjectExtras("test5")
    private var test5: Double? = null

    @InjectExtras("test6")
    private var test6: Array<TestBean>? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_inject
    }

    override fun getBaseLayoutId(): Int? {
        return null
    }

    override fun initToolBar() {
        super.initToolBar()
        ToolBarUtils.Builder(findViewById(R.id.toolbar)).setType(ToolBarUtils.NORMAL_TYPE)
            .setBackRes(R.drawable.ic_back_ffffff_24dp).setNormalTitleColor(R.color.c_ff)
            .setBackListener {
                finish()
            }.builder()
    }

    override fun initData() {
        super.initData()
        InjectUtils.injectExtras1(this)
        mBinding.tvText.text = "name:${name}  test2:$test2  test3:$test3 test4:$test4 test5:$test5  test6:$${test6?.get(0)?.userId} ${test6?.get(0)?.type}"
        log("name:${name}  test2:$test2  test3:$test3  test6:${test6?.get(0)?.userId} ${test6?.get(0)?.type}")
    }
}
