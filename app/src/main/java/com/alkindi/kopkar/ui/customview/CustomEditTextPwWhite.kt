package com.alkindi.kopkar.ui.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import android.text.InputType
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.alkindi.kopkar.R

class CustomEditTextPwWhite @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs), View.OnTouchListener {
    private var viewPassword: Drawable =
        ContextCompat.getDrawable(context, R.drawable.baseline_remove_red_eye_24)!!
    private var hidePassword: Drawable =
        ContextCompat.getDrawable(context, R.drawable.baseline_visibility_off_black)!!

    private var isPasswordVisible = false

    init {
        setup()
    }

    private fun setup() {
        val wrappedViewPassword = InsetDrawable(viewPassword, 0, 0, 40, 0)
        setCompoundDrawablesWithIntrinsicBounds(null, null, wrappedViewPassword, null)

        inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT

        compoundDrawablePadding = 16
        hint =""
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }

}