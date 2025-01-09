package com.alkindi.kopkar.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import java.text.DecimalFormat
import java.text.NumberFormat

class CustomEditTextMoneyInput @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs), View.OnTouchListener {
    private var currentText = ""
    private val decimalFormat: NumberFormat = DecimalFormat("#,###")

    init {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(cs: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(cs: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() != currentText) {
                    this@CustomEditTextMoneyInput.removeTextChangedListener(this)

                    val cleanString = s.toString().replace("[,.]".toRegex(), "")

                    if (cleanString.isNotEmpty()) {
                        val parsed = cleanString.toLongOrNull() ?: 0L
                        val formatted = decimalFormat.format(parsed)

                        currentText = formatted
                        this@CustomEditTextMoneyInput.setText(formatted)
                        this@CustomEditTextMoneyInput.setSelection(formatted.length)
                    }
                    this@CustomEditTextMoneyInput.addTextChangedListener(this)
                }
            }

        })
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        return false
    }


}