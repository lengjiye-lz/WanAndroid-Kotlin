package com.lengjiye.code.utils

import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.lengjiye.code.R
import com.lengjiye.code.widgets.AutoTextView
import com.lengjiye.code.widgets.DeleteEditTextView
import com.lengjiye.tools.ResTool

/**
 * toolbar 工具
 */
class ToolBarUtils {

    companion object {
        const val NORMAL_TYPE = 0
        const val SEARCH_TYPE = 1
        const val EXIT_TYPE = 2

        fun getNormalTitle(toolbar: Toolbar): TextView {
            return toolbar.findViewById(R.id.tool_tv_title)
        }

        fun getSearchTitle(toolbar: Toolbar): AutoTextView {
            return toolbar.findViewById(R.id.tool_tv_search_title)
        }

        fun getSearchExit(toolbar: Toolbar): DeleteEditTextView {
            return toolbar.findViewById(R.id.et_search)
        }
    }

    class Builder(val toolbar: Toolbar) {
        private var params: Params = Params()

        fun setType(type: Int): Builder {
            params.type = type
            return this
        }

        fun setBackListener(backListener: (view: View) -> Unit): Builder {
            params.backListener = backListener
            return this
        }

        fun setBackRes(backRes: Int): Builder {
            params.backRes = backRes
            return this
        }

        fun setCloseListener(closeListener: (view: View) -> Unit): Builder {
            params.closeListener = closeListener
            return this
        }

        fun setCloseRes(closeRes: Int): Builder {
            params.closeRes = closeRes
            return this
        }

        fun setNormalTitle(normalTitleRes: Int): Builder {
            params.normalTitleRes = normalTitleRes
            return this
        }

        fun setNormalTitle(normalTitle: String): Builder {
            params.normalTitle = normalTitle
            return this
        }

        fun setNormalTitleColor(normalTitleColor: Int): Builder {
            params.normalTitleColor = normalTitleColor
            return this
        }

        fun setNormalTitleSize(normalTitleSize: Int): Builder {
            params.normalTitleSize = normalTitleSize
            return this
        }

        // search
        fun setSearchBgListener(searchBgListener: (view: View) -> Unit): Builder {
            params.searchBgListener = searchBgListener
            return this
        }

        fun setSearchBgRes(searchBgRes: Int): Builder {
            params.searchBgRes = searchBgRes
            return this
        }

        fun setSearchLogoListener(searchLogoListener: (view: View) -> Unit): Builder {
            params.searchLogoListener = searchLogoListener
            return this
        }

        fun setSearchLogoRes(searchLogoRes: Int): Builder {
            params.searchLogoRes = searchLogoRes
            return this
        }

        fun setSearchRes(searchRes: Int): Builder {
            params.searchRes = searchRes
            return this
        }

        fun setSearchListener(searchListener: (view: View) -> Unit): Builder {
            params.searchListener = searchListener
            return this
        }

        fun setSearchTitleRes(searchTitleRes: Int): Builder {
            params.searchTitleRes = searchTitleRes
            return this
        }

        fun setSearchTitle(searchTitle: String): Builder {
            params.searchTitle = searchTitle
            return this
        }

        fun setSearchTitleColor(searchTitleColor: Int): Builder {
            params.searchTitleColor = searchTitleColor
            return this
        }

        fun setSearchTitleSize(searchTitleSize: Int): Builder {
            params.searchTitleSize = searchTitleSize
            return this
        }

        fun setSearchTitleListener(searchTitleListener: (view: View) -> Unit): Builder {
            params.searchTitleListener = searchTitleListener
            return this
        }

        fun builder(): Toolbar {
            when (params.type) {
                NORMAL_TYPE -> {
                    setNormalLayout(toolbar, params)
                }

                SEARCH_TYPE -> {
                    setSearchLayout(toolbar, params)
                }

                EXIT_TYPE -> {
                    setExitSearchLayout(toolbar, params)
                }
            }
            return toolbar
        }


        private fun setNormalLayout(view: View, params: Params) {
            val toolIvBack = view.findViewById<ImageView>(R.id.tool_iv_back)
            val toolTvTitle = view.findViewById<TextView>(R.id.tool_tv_title)
            val toolIvClose = view.findViewById<ImageView>(R.id.tool_iv_close)
            val toolIvMore = view.findViewById<ImageView>(R.id.tool_iv_more)

            toolIvBack.visibility = View.VISIBLE
            toolTvTitle.visibility = View.VISIBLE


            params.backRes?.let {
                toolIvBack.setImageResource(it)
            }

            params.backListener?.let { listener ->
                toolIvBack.setOnClickListener {
                    listener.invoke(it)
                }
            }

            params.closeRes?.let {
                toolIvClose.visibility = View.VISIBLE
                toolIvClose.setImageResource(it)
            }

            params.closeListener?.let { listener ->
                toolIvClose.visibility = View.VISIBLE
                toolIvClose.setOnClickListener {
                    listener.invoke(it)
                }
            }

            params.moreRes?.let {
                toolIvMore.visibility = View.VISIBLE
                toolIvMore.setImageResource(it)
            }

            params.moreListener?.let { listener ->
                toolIvMore.visibility = View.VISIBLE
                toolIvMore.setOnClickListener {
                    listener.invoke(it)
                }
            }

            params.normalTitleRes?.let {
                toolTvTitle.setText(it)
            }

            params.normalTitle?.let {
                toolTvTitle.text = it
            }

            params.normalTitleColor?.let {
                toolTvTitle.setTextColor(ResTool.getColor(it))
            }

            params.normalTitleSize?.let {
                toolTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, it.toFloat())
            }
        }

        private fun setExitSearchLayout(view: View, params: Params) {
            val toolIvBack = view.findViewById<ImageView>(R.id.tool_iv_back)
            val toolVSearchEt = view.findViewById<View>(R.id.tool_v_search_et)
            val etSearch = view.findViewById<DeleteEditTextView>(R.id.et_search)

            toolVSearchEt.visibility = View.VISIBLE
            etSearch.visibility = View.VISIBLE
            toolIvBack.visibility = View.VISIBLE

            params.backRes?.let {
                toolIvBack.setImageResource(it)
            }

            params.backListener?.let { listener ->
                toolIvBack.setOnClickListener {
                    listener.invoke(it)
                }
            }

        }

        private fun setSearchLayout(view: View, params: Params) {
            val toolVSearch = view.findViewById<View>(R.id.tool_v_search)
            val toolIvLogo = view.findViewById<ImageView>(R.id.tool_iv_logo)
            val toolIvSearch = view.findViewById<ImageView>(R.id.tool_iv_search)
            val toolTvSearchTitle = view.findViewById<AutoTextView>(R.id.tool_tv_search_title)

            toolVSearch.visibility = View.VISIBLE
            toolIvSearch.visibility = View.VISIBLE
            toolTvSearchTitle.visibility = View.VISIBLE

            params.searchBgRes?.let {
                toolVSearch.setBackgroundResource(it)
            }

            params.searchBgListener?.let { listener ->
                toolVSearch.setOnClickListener {
                    listener.invoke(it)
                }
            }

            params.searchLogoRes?.let {
                toolIvLogo.visibility = View.VISIBLE
                toolIvLogo.setImageResource(it)
            }

            params.searchLogoListener?.let { listener ->
                toolIvLogo.visibility = View.VISIBLE
                toolIvLogo.setOnClickListener {
                    listener.invoke(it)
                }
            }

            params.searchRes?.let {
                toolIvSearch.setImageResource(it)
            }

            params.searchListener?.let { listener ->
                toolIvSearch.setOnClickListener {
                    listener.invoke(it)
                }
            }

            params.searchTitleRes?.let {

                toolTvSearchTitle.setText(ResTool.getString(it))
            }

            params.searchTitle?.let {
                toolTvSearchTitle.setText(it)
            }

            params.searchTitleListener?.let { listener ->
                toolTvSearchTitle.setOnClickListener {
                    listener.invoke(it)
                }
            }

            params.searchTitleColor?.let {
                toolTvSearchTitle.setTextColor(ResTool.getColor(it))
            }

            params.searchTitleSize?.let {
                toolTvSearchTitle.setTextSize(it.toFloat())
            }
        }
    }


    private class Params {
        var type = NORMAL_TYPE
        var backRes: Int? = null
        var backListener: ((View) -> Unit?)? = null
        var closeListener: ((View) -> Unit?)? = null
        var closeRes: Int? = null
        var moreListener: ((View) -> Unit?)? = null
        var moreRes: Int? = null
        var normalTitleRes: Int? = null
        var normalTitle: String? = null
        var normalTitleColor: Int? = null
        var normalTitleSize: Int? = null

        var searchBgRes: Int? = null
        var searchBgListener: ((View) -> Unit?)? = null
        var searchLogoRes: Int? = null
        var searchLogoListener: ((View) -> Unit?)? = null
        var searchRes: Int? = null
        var searchListener: ((View) -> Unit?)? = null
        var searchTitleRes: Int? = null
        var searchTitle: String? = null
        var searchTitleColor: Int? = null
        var searchTitleSize: Int? = null
        var searchTitleListener: ((View) -> Unit?)? = null
    }
}