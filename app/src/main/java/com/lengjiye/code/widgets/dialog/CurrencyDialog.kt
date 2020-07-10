package com.lengjiye.code.widgets.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.lengjiye.code.R
import com.lengjiye.code.constant.IntentKey
import com.lengjiye.tools.ResTool
import com.lengjiye.tools.ScreenTool

class CurrencyDialog : DialogFragment() {

    private var layout: Int? = null
    private var clLayout: ConstraintLayout? = null
    private var tvTitle: TextView? = null
    private var tvContent: TextView? = null
    private var btnCancel: Button? = null
    private var btnSubmit: Button? = null
    private var horizontalLine: View? = null
    private var verticalLine: View? = null
    private var clContent: ConstraintLayout? = null

    private var params: Params? = null

    private var dismissListener: ((dialog: CurrencyDialog) -> Unit)? = null

    init {
        setStyle(STYLE_NO_FRAME, R.style.common_dialog_style)
    }

    companion object {
        fun newInstance(arguments: Bundle? = null) = CurrencyDialog().apply {
            this.arguments = arguments?.apply {
                params = getParcelable(IntentKey.KEY_OBJECT)
            }
        }
    }

    fun setContentView(layout: Int?) {
        this.layout = layout
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val currencyView: View
        if (layout == null) {
            currencyView = inflater.inflate(R.layout.dialog_currency, container, false)
            clLayout = currencyView.findViewById(R.id.cl_layout)
            tvTitle = currencyView.findViewById(R.id.tv_title)
            tvContent = currencyView.findViewById(R.id.tv_content)
            btnCancel = currencyView.findViewById(R.id.btn_cancel)
            btnSubmit = currencyView.findViewById(R.id.btn_submit)
            horizontalLine = currencyView.findViewById(R.id.v_horizontal_line)
            verticalLine = currencyView.findViewById(R.id.v_vertical_line)
            clContent = currencyView.findViewById(R.id.cl_content)

            initData()
        } else {
            currencyView = inflater.inflate(layout!!, container, false)
        }
        return currencyView
    }

    private fun initData() {
        if (params == null) {
            return
        }
        setContentView(params?.layout)
        setTitle(params?.title, params?.titleColor)
        setContent(params?.content, params?.contentColor)
        setCancelListener(params?.cancel, params?.contentColor, params?.cancelBg, params?.cancelListener)
        setSubmitListener(params?.submit, params?.submitColor, params?.submitBg, params?.submitListener)
    }

    fun setTitle(title: String?, color: Int?) {
        if (title == null) {
            tvTitle?.visibility = View.GONE
            return
        }
        tvTitle?.text = title
        color?.let { tvTitle?.setTextColor(ResTool.getColor(it)) }
    }

    fun setContent(content: String?, color: Int?) {
        if (content == null) {
            tvContent?.visibility = View.GONE
            return
        }
        tvContent?.text = content
        color?.let { tvContent?.setTextColor(ResTool.getColor(it)) }
    }

    fun setCancelListener(cancel: String?, color: Int?, bgRes: Int?, listener: ((view: View, dialog: CurrencyDialog) -> Unit)?) {
        if (listener == null) {
            btnCancel?.visibility = View.GONE
            btnSubmit?.setBackgroundResource(R.drawable.bg_currency_dialog_bottom_btn)
            btnSubmit?.layoutParams = (btnSubmit?.layoutParams as ConstraintLayout.LayoutParams).apply {
                marginStart = 0
            }
            verticalLine?.visibility = View.GONE
            clLayout?.let {
                val set = ConstraintSet()
                set.clone(it)
                set.connect(horizontalLine?.id!!, ConstraintSet.BOTTOM, btnSubmit?.id!!, ConstraintSet.TOP)
                set.connect(btnSubmit?.id!!, ConstraintSet.TOP, horizontalLine?.id!!, ConstraintSet.BOTTOM)
                set.connect(btnSubmit?.id!!, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
                set.applyTo(it)
            }
            return
        }
        btnCancel?.visibility = View.VISIBLE
        horizontalLine?.visibility = View.VISIBLE
        clContent?.setBackgroundResource(R.drawable.bg_currency_dialog_top_bg)
        color?.let { btnCancel?.setTextColor(ResTool.getColor(it)) }
        bgRes?.let { btnCancel?.setBackgroundResource(it) }
        cancel?.let { btnCancel?.text = it }
        btnCancel?.setOnClickListener {
            listener.invoke(it, this)
        }
    }

    fun setSubmitListener(submit: String?, color: Int?, bgRes: Int?, listener: ((view: View, dialog: CurrencyDialog) -> Unit)?) {
        if (listener == null) {
            btnSubmit?.visibility = View.GONE
            btnCancel?.setBackgroundResource(R.drawable.bg_currency_dialog_bottom_btn)
            verticalLine?.visibility = View.GONE
            return
        }
        btnSubmit?.visibility = View.VISIBLE
        horizontalLine?.visibility = View.VISIBLE
        clContent?.setBackgroundResource(R.drawable.bg_currency_dialog_top_bg)
        color?.let { btnSubmit?.setTextColor(ResTool.getColor(it)) }
        bgRes?.let { btnSubmit?.setBackgroundResource(it) }
        submit?.let { btnSubmit?.text = it }
        btnSubmit?.setOnClickListener {
            listener.invoke(it, this)
        }
    }

    override fun onStart() {
        // Set the dialog to not focusable.
        dialog?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        )
        val layoutParams = dialog?.window?.attributes
        layoutParams?.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams?.width = (ScreenTool.getScreenWidth() * 0.8).toInt()
        dialog?.window?.attributes = layoutParams
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        // Show the dialog with NavBar hidden.
        super.onStart()

        // Set the dialog to focusable again.
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    }

    fun show(manager: FragmentManager) {
        this.show(manager, this.javaClass.name)
    }

    fun setOnDismissListener(listener: ((dialog: CurrencyDialog) -> Unit)?): CurrencyDialog {
        this.dismissListener = listener
        return this
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissListener?.invoke(this)
    }

    class Builder() {
        private val params = Params()

        fun setContentView(layout: Int?): Builder {
            params.layout = layout
            return this
        }

        fun setTitle(title: String?, color: Int = R.color.c_1a): Builder {
            params.title = title
            params.titleColor = color
            return this
        }

        fun setTitle(title: Int?, color: Int = R.color.c_1a): Builder {
            params.title = title?.let { ResTool.getString(it) }
            params.titleColor = color
            return this
        }

        fun setContent(title: String?, color: Int = R.color.c_1a): Builder {
            params.content = title
            params.contentColor = color
            return this
        }

        fun setContent(title: Int?, color: Int = R.color.c_1a): Builder {
            params.content = title?.let { ResTool.getString(it) }
            params.contentColor = color
            return this
        }


        fun setCancelListener(listener: ((view: View, dialog: CurrencyDialog) -> Unit)?): Builder {
            params.cancelListener = listener
            return this
        }

        fun setCancelListener(bgRes: Int, listener: ((view: View, dialog: CurrencyDialog) -> Unit)?): Builder {
            params.cancelBg = bgRes
            params.cancelListener = listener
            return this
        }

        fun setCancelListener(color: Int, bgRes: Int, listener: ((view: View, dialog: CurrencyDialog) -> Unit)?): Builder {
            params.cancelColor = color
            params.cancelBg = bgRes
            params.cancelListener = listener
            return this
        }

        fun setCancelListener(cancel: String, color: Int, bgRes: Int, listener: ((view: View, dialog: CurrencyDialog) -> Unit)?): Builder {
            params.cancel = cancel
            params.cancelColor = color
            params.cancelBg = bgRes
            params.cancelListener = listener
            return this
        }

        fun setSubmitListener(listener: ((view: View, dialog: CurrencyDialog) -> Unit)?): Builder {
            params.submitListener = listener
            return this
        }

        fun setSubmitListener(bgRes: Int, listener: ((view: View, dialog: CurrencyDialog) -> Unit)?): Builder {
            params.submitBg = bgRes
            params.submitListener = listener
            return this
        }

        fun setSubmitListener(color: Int, bgRes: Int, listener: ((view: View, dialog: CurrencyDialog) -> Unit)?): Builder {
            params.submitColor = color
            params.submitBg = bgRes
            params.submitListener = listener
            return this
        }

        fun setSubmitListener(submit: String, color: Int, bgRes: Int, listener: ((view: View, dialog: CurrencyDialog) -> Unit)?): Builder {
            params.submit = submit
            params.submitColor = color
            params.submitBg = bgRes
            params.submitListener = listener
            return this
        }

        fun build(): CurrencyDialog {
            return newInstance(Bundle().apply {
                putParcelable(IntentKey.KEY_OBJECT, params)
            })
        }
    }

    private class Params() : Parcelable {
        var layout: Int? = null

        var title: String? = null
        var titleColor: Int? = null

        var content: String? = null
        var contentColor: Int? = null

        var cancel: String? = null
        var cancelColor: Int? = null
        var cancelBg: Int? = null
        var cancelListener: ((view: View, dialog: CurrencyDialog) -> Unit)? = null

        var submit: String? = null
        var submitColor: Int? = null
        var submitBg: Int? = null
        var submitListener: ((view: View, dialog: CurrencyDialog) -> Unit)? = null

        constructor(parcel: Parcel) : this() {
            layout = parcel.readValue(Int::class.java.classLoader) as? Int
            title = parcel.readString()
            titleColor = parcel.readValue(Int::class.java.classLoader) as? Int
            content = parcel.readString()
            contentColor = parcel.readValue(Int::class.java.classLoader) as? Int
            cancel = parcel.readString()
            cancelColor = parcel.readValue(Int::class.java.classLoader) as? Int
            cancelBg = parcel.readValue(Int::class.java.classLoader) as? Int
            submit = parcel.readString()
            submitColor = parcel.readValue(Int::class.java.classLoader) as? Int
            submitBg = parcel.readValue(Int::class.java.classLoader) as? Int
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeValue(layout)
            parcel.writeString(title)
            parcel.writeValue(titleColor)
            parcel.writeString(content)
            parcel.writeValue(contentColor)
            parcel.writeString(cancel)
            parcel.writeValue(cancelColor)
            parcel.writeValue(cancelBg)
            parcel.writeString(submit)
            parcel.writeValue(submitColor)
            parcel.writeValue(submitBg)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Params> {
            override fun createFromParcel(parcel: Parcel): Params {
                return Params(parcel)
            }

            override fun newArray(size: Int): Array<Params?> {
                return arrayOfNulls(size)
            }
        }
    }
}