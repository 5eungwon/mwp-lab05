package kr.ac.mwplab.calculator

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.content.res.ColorStateList
import android.view.Gravity
import android.view.inputmethod.EditorInfo
import android.text.InputType
import android.widget.*
import java.util.Locale

class MainActivity : Activity() {
    private lateinit var first: EditText
    private lateinit var second: EditText
    private lateinit var operators: RadioGroup
    private lateinit var result: TextView
    private lateinit var expression: TextView
    private val calculator = FourBasicOpt()
    private val symbols = listOf("+", "−", "×", "÷")
    private val operatorIds = listOf(R.id.add, R.id.subtract, R.id.multiply, R.id.divide)
    private fun dp(value: Int) = (value * resources.displayMetrics.density).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scroll = ScrollView(this)
        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(24), dp(20), dp(24), dp(24))
        }
        scroll.addView(content)
        scroll.setOnApplyWindowInsetsListener { view, insets ->
            view.setPadding(insets.systemWindowInsetLeft, insets.systemWindowInsetTop,
                insets.systemWindowInsetRight, insets.systemWindowInsetBottom)
            insets
        }
        setContentView(scroll)
        scroll.requestApplyInsets()
        fun label(text: String, size: Float = 16f) = TextView(this).apply {
            this.text = text
            textSize = size
            setTextColor(Color.rgb(32, 39, 61))
            setPadding(0, dp(12), 0, dp(8))
            content.addView(this)
        }
        label("계산기", 26f).setTypeface(null, Typeface.BOLD)
        fun rounded(color: Int) = GradientDrawable().apply {
            setColor(color)
            cornerRadius = dp(14).toFloat()
        }
        fun input(title: String, viewId: Int) : EditText {
            label(title)
            return EditText(this).apply {
                id = viewId
                hint = "숫자 입력"
                background = rounded(Color.WHITE)
                setPadding(dp(16), dp(12), dp(16), dp(12))
                textSize = 28f
                inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_NUMBER_FLAG_SIGNED
                setSingleLine(true)
                minHeight = dp(56)
                content.addView(this, LinearLayout.LayoutParams(-1, -2))
            }
        }
        first = input("첫 번째 숫자", R.id.first)

        operators = RadioGroup(this).apply {
            id = R.id.operators
            orientation = RadioGroup.HORIZONTAL
            symbols.forEachIndexed { index, symbol ->
                addView(RadioButton(this@MainActivity).apply {
                    id = operatorIds[index]
                    text = symbol
                    textSize = 26f
                    buttonDrawable = null
                    background = StateListDrawable().apply {
                        addState(intArrayOf(android.R.attr.state_checked), rounded(Color.rgb(66, 85, 212)))
                        addState(intArrayOf(), rounded(Color.WHITE))
                    }
                    setTextColor(ColorStateList(
                        arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf()),
                        intArrayOf(Color.WHITE, Color.rgb(32, 39, 61))
                    ))
                    contentDescription = listOf("더하기", "빼기", "곱하기", "나누기")[index]
                    gravity = Gravity.CENTER
                    minHeight = dp(56)
                }, LinearLayout.LayoutParams(0, dp(56), 1f).apply {
                    if (index > 0) marginStart = dp(8)
                })
            }
            check(R.id.add)
            content.addView(this, LinearLayout.LayoutParams(-1, -2).apply {
                topMargin = dp(16)
                bottomMargin = dp(4)
            })
        }
        second = input("두 번째 숫자", R.id.second)
        second.imeOptions = EditorInfo.IME_ACTION_DONE
        second.setOnEditorActionListener { _, action, _ ->
            if (action == EditorInfo.IME_ACTION_DONE) { calculate(); true } else false
        }
        content.addView(Button(this).apply {
            text = "=  계산"
            isAllCaps = false
            background = rounded(Color.rgb(66, 85, 212))
            setTextColor(Color.WHITE)
            textSize = 18f
            minHeight = dp(56)
            setOnClickListener { calculate() }
        }, LinearLayout.LayoutParams(-1, -2).apply { topMargin = dp(24) })
        expression = label("결과", 14f).apply {
            setPadding(0, dp(28), 0, dp(4))
            text = savedInstanceState?.getString("expression") ?: text
            setTextColor(Color.rgb(106, 112, 132))
        }
        result = label("—", 36f).apply {
            setTypeface(null, Typeface.BOLD)
            setTextIsSelectable(true)
            accessibilityLiveRegion = android.view.View.ACCESSIBILITY_LIVE_REGION_POLITE
            text = savedInstanceState?.getString("result") ?: text
        }
    }

    private fun calculate() {
        val x = first.text.toString().trim().toDoubleOrNull()
        val y = second.text.toString().trim().toDoubleOrNull()
        first.error = if (x == null || !x.isFinite()) "올바른 숫자를 입력해주세요." else null
        second.error = if (y == null || !y.isFinite()) "올바른 숫자를 입력해주세요." else null
        if (x == null || y == null || !x.isFinite() || !y.isFinite()) {
            result.textSize = 20f
            expression.text = "입력 확인"
            result.text = "숫자 두 개를 입력해주세요."
            return
        }
        val index = operatorIds.indexOf(operators.checkedRadioButtonId)
        val answer = when (index) {
            0 -> calculator.add(x, y)
            1 -> calculator.subtract(x, y)
            2 -> calculator.multiply(x, y)
            else -> calculator.divide(x, y)
        }
        fun format(value: Double) = String.format(Locale.US, "%.12g", value).let {
            if (it.contains('.')) it.substringBefore('e').trimEnd('0').trimEnd('.') +
                (if (it.contains('e')) "e" + it.substringAfter('e') else "") else it
        }
        expression.text = "${format(x)} ${symbols[index]} ${format(y)} ="
        result.textSize = if (answer.isFinite()) 36f else 20f
        result.text = if (answer.isFinite()) format(answer)
            else "더 작은 숫자를 입력해주세요."
        if (index == 3 && y == 0.0) expression.text = "0으로 나누면 결과는 0입니다."
        (getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager)
            .hideSoftInputFromWindow(second.windowToken, 0)
        first.clearFocus()
        second.clearFocus()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("result", result.text.toString())
        outState.putString("expression", expression.text.toString())
        super.onSaveInstanceState(outState)
    }
}
