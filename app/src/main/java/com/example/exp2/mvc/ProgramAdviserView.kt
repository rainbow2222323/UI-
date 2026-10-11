package com.example.exp2.mvc

import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView

/**
 * MVC 中的 View（视图）层接口：只描述界面需要具备的显示与交互能力。
 * Controller 通过该接口间接操作界面，与具体控件实现解耦。
 */
interface ProgramAdviserView {

    /** 用给定的语言名称列表填充下拉框选项 */
    fun showLanguageOptions(languageNames: List<String>)

    /** 输出查询结果 */
    fun showResult(result: String)

    /** 返回下拉框当前选中的语言名称 */
    fun getSelectedLanguage(): String

    /** 注册“查询”按钮的点击事件 */
    fun setOnQueryClickListener(listener: () -> Unit)
}

/**
 * View 层接口的具体实现：把界面能力绑定到布局中的实际控件上。
 */
class ProgramAdviserViewImpl(
    private val spinner: Spinner,
    private val queryButton: Button,
    private val resultView: TextView,
) : ProgramAdviserView {

    override fun showLanguageOptions(languageNames: List<String>) {
        val adapter = ArrayAdapter(
            spinner.context,
            android.R.layout.simple_spinner_item,
            languageNames,
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    override fun showResult(result: String) {
        resultView.text = result
    }

    override fun getSelectedLanguage(): String = spinner.selectedItem?.toString().orEmpty()

    override fun setOnQueryClickListener(listener: () -> Unit) {
        queryButton.setOnClickListener { listener() }
    }
}
