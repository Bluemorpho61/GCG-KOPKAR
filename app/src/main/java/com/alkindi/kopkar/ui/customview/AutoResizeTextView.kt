package com.alkindi.kopkar.ui.customview

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class AutoResizeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    deffStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, deffStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = measuredWidth
        val text = text.toString()
        val paint = Paint()
        paint.textSize = textSize

        while (paint.measureText(text) > width && textSize > 0) {
            textSize -= 1
            paint.textSize = textSize
        }
        setMeasuredDimension(width, measuredHeight)
    }
}