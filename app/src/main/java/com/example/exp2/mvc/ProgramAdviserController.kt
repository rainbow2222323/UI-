package com.example.exp2.mvc

/**
 * MVC 中的 Controller（控制器）层：连接 Model 与 View。
 * 负责把用户在 View 上的操作转换为对 Model 的查询，
 * 再把查询结果写回 View 显示。
 */
class ProgramAdviserController(
    private val model: ProgramAdviserModel,
    private val view: ProgramAdviserView,
) {

    /** 初始化控制器：填充下拉框选项并注册“查询”按钮的点击事件 */
    fun init() {
        view.showLanguageOptions(model.getLanguageNames())
        view.setOnQueryClickListener {
            val selectedLanguage = view.getSelectedLanguage()
            view.showResult(model.query(selectedLanguage))
        }
    }
}
