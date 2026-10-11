package com.example.exp2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.exp2.mvc.ProgramAdviserController
import com.example.exp2.mvc.ProgramAdviserModel
import com.example.exp2.mvc.ProgramAdviserViewImpl

class MainActivity : AppCompatActivity() {

    /** 已添加的 TextView 数量，用于生成 “This is a new textView:N” 的编号 */
    private var addedTextViewCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupDynamicTextViewArea()
        setupProgramAdviser()
    }

    /**
     * 区1：点击按钮后，用代码动态向 ScrollView 嵌套的 LinearLayout 中添加 TextView，
     * 内容超出一屏时由 ScrollView 提供滚动显示能力。
     */
    private fun setupDynamicTextViewArea() {
        val textContainer = findViewById<LinearLayout>(R.id.ll_text_container)
        val textScrollView = findViewById<ScrollView>(R.id.sv_text_area)

        findViewById<Button>(R.id.btn_add_text_view).setOnClickListener {
            addedTextViewCount++
            val textView = TextView(this).apply {
                // 文案由字符串资源格式化生成，避免硬编码字符串
                text = getString(R.string.new_text_view_format, addedTextViewCount)
                setTextColor(getColor(R.color.area1_text_color))
                textSize = DYNAMIC_TEXT_SIZE_SP
            }
            textContainer.addView(textView)
            // 添加后自动滚动到底部，方便查看最新添加的 TextView
            textScrollView.post { textScrollView.fullScroll(View.FOCUS_DOWN) }
        }
    }

    /**
     * 区2：装配 MVC 三层实现 ProgramAdviser
     * - Model：ProgramAdviserModel，保存语言数据并提供查询；
     * - View：ProgramAdviserViewImpl，封装 Spinner、查询按钮和结果文本等控件；
     * - Controller：ProgramAdviserController，响应按钮事件、查询 Model 并把结果写回 View。
     */
    private fun setupProgramAdviser() {
        val model = ProgramAdviserModel(this)
        val view = ProgramAdviserViewImpl(
            spinner = findViewById<Spinner>(R.id.spinner_language),
            queryButton = findViewById(R.id.btn_query),
            resultView = findViewById(R.id.tv_query_result),
        )
        ProgramAdviserController(model, view).init()
    }

    private companion object {
        /** 动态添加的 TextView 文字大小（单位：sp） */
        const val DYNAMIC_TEXT_SIZE_SP = 16f
    }
}
