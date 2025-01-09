package com.alkindi.kopkar.ui.customview

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import android.text.InputType
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.alkindi.kopkar.R

class CustomEditTextPw @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs), View.OnTouchListener {

    private var profileUserImage: Drawable =
        ContextCompat.getDrawable(context, R.drawable.baseline_lock_24)!!
    private var viewPassword: Drawable =
        ContextCompat.getDrawable(context, R.drawable.baseline_visibility_pw)!!
    private var hidePassword: Drawable =
        ContextCompat.getDrawable(context, R.drawable.baseline_visibility_off_pw)!!

    private var isPasswordVisible = false

    init {
        setup()
        componentLogic()
        setOnTouchListener(this)
    }

    private fun setup() {
        // Wrap the drawables with InsetDrawable to add margins
        val wrappedProfileUserImage = InsetDrawable(profileUserImage, 40, 0, 0, 0) // 40dp marginStart
        val wrappedViewPassword = InsetDrawable(viewPassword, 0, 0, 40, 0) // 40dp marginEnd

        // Set the drawable for the left (lock icon) and right (eye icon)
        setCompoundDrawablesWithIntrinsicBounds(wrappedProfileUserImage, null, wrappedViewPassword, null)

        // Set input type to password by default
        inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT

        // Add padding so the text doesn’t overlap with the icons
        compoundDrawablePadding = 16
        hint = "Password"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START

        // Create custom rounded background
        val customBorder = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 30f // Rounded corners
            setColor(Color.parseColor("#e6f0ff")) // Light background color
            setStroke(2, Color.parseColor("#d3e0ff")) // Border color
        }

        // Apply background
        background = customBorder
    }

    private fun componentLogic() {
        // Toggle password visibility logic
        val wrappedProfileUserImage = InsetDrawable(profileUserImage, 40, 0, 0, 0)
        val wrappedHidePassword = InsetDrawable(hidePassword, 0, 0, 40, 0)
        val wrappedViewPassword = InsetDrawable(viewPassword, 0, 0, 40, 0)

        if (isPasswordVisible) {
            inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            setCompoundDrawablesWithIntrinsicBounds(wrappedProfileUserImage, null, wrappedHidePassword, null)
        } else {
            inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
            setCompoundDrawablesWithIntrinsicBounds(wrappedProfileUserImage, null, wrappedViewPassword, null)
        }
        // Ensure the cursor stays at the end after toggling
        setSelection(text?.length ?: 0)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        // Check if the touch event occurred on the right drawable (eye icon)
        if (event != null && event.action == MotionEvent.ACTION_UP) {
            if (event.rawX >= (right - compoundDrawables[2].bounds.width())) {
                isPasswordVisible = !isPasswordVisible
                componentLogic()
                return true
            }
        }
        return false
    }
}