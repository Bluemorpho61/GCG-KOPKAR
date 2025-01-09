package com.alkindi.kopkar.ui.customview

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.alkindi.kopkar.R

class CustomEditTextUsername @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs), View.OnTouchListener {
    private var profileUserImage: Drawable =
        ContextCompat.getDrawable(context, R.drawable.baseline_account_box_24)!!

    init {
        setup()
    }

    private fun setup() {
        val wrappedDrawable = InsetDrawable(profileUserImage, 40, 0, 0, 0)

        setCompoundDrawablesWithIntrinsicBounds(wrappedDrawable, null, null, null)
        compoundDrawablePadding = 16
        hint = "Username"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START

        val customBorder = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 30f
            setColor(Color.parseColor("#e6f0ff"))
            setStroke(2, Color.parseColor("#d3e0ff"))
        }

        background = customBorder
    }

    override fun onTouch(v: View?, evt: MotionEvent?): Boolean {
        return false
    }


}