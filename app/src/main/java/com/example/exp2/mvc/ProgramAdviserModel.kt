package com.example.exp2.mvc

import android.content.Context
import androidx.annotation.StringRes
import com.example.exp2.R

/**
 * MVC 中的 Model（模型）层：负责保存编程语言数据，并对外提供查询能力。
 *
 * 所有文案均以字符串资源 ID 的形式保存，通过 Context 解析为实际文本，
 * 从而避免在代码中出现硬编码字符串。
 */
class ProgramAdviserModel(private val context: Context) {

    /** 一条语言数据：语言名称与对应的建议文案都保存为字符串资源 ID */
    data class Language(
        @param:StringRes val nameResId: Int,
        @param:StringRes val adviceResId: Int,
    )

    private val languages: List<Language> = listOf(
        Language(R.string.language_kotlin, R.string.advice_kotlin),
        Language(R.string.language_java, R.string.advice_java),
        Language(R.string.language_python, R.string.advice_python),
        Language(R.string.language_cpp, R.string.advice_cpp),
        Language(R.string.language_javascript, R.string.advice_javascript),
    )

    /** 获取全部可选的语言名称，用于填充下拉框 */
    fun getLanguageNames(): List<String> = languages.map { context.getString(it.nameResId) }

    /**
     * 按语言名称查询对应的建议结果。
     *
     * @param languageName 下拉框中选中的语言名称
     * @return 形如“语言：建议”的结果文本；未匹配到时返回提示文案
     */
    fun query(languageName: String): String {
        val language = languages.firstOrNull { context.getString(it.nameResId) == languageName }
            ?: return context.getString(R.string.advice_unknown)
        return context.getString(
            R.string.adviser_result_format,
            context.getString(language.nameResId),
            context.getString(language.adviceResId),
        )
    }
}
