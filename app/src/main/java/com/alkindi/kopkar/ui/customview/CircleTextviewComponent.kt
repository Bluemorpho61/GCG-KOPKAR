package com.alkindi.kopkar.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.alkindi.kopkar.R

class CircleTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint: Paint = Paint()
    private val borderPaint: Paint = Paint()
    private var text: String = "Hello"
    private var textColor: Int = Color.BLACK
    private var circleColor: Int = Color.WHITE
    private var borderColor: Int = Color.BLACK
    private var borderWidth: Float = 0.3f
    private var textSize: Float = 50f
    private var shadowColor: Int = Color.GRAY
    private var shadowRadius: Float = 5f
    private var shadowDx: Float = 0f
    private var shadowDy: Float = 5f

    init {
        // Load custom attributes if needed
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleTextView)
        text = typedArray.getString(R.styleable.CircleTextView_text) ?: text
        textColor = typedArray.getColor(R.styleable.CircleTextView_textColor, textColor)
        circleColor = typedArray.getColor(R.styleable.CircleTextView_circleColor, circleColor)
        borderColor = typedArray.getColor(R.styleable.CircleTextView_borderColor, borderColor)
        borderWidth = typedArray.getDimension(R.styleable.CircleTextView_borderWidth, borderWidth)
        shadowColor = typedArray.getColor(R.styleable.CircleTextView_shadowColor, shadowColor)
        shadowRadius = typedArray.getDimension(R.styleable.CircleTextView_shadowRadius, shadowRadius)
        shadowDx = typedArray.getDimension(R.styleable.CircleTextView_shadowDx, shadowDx)
        shadowDy = typedArray.getDimension(R.styleable.CircleTextView_shadowDy, shadowDy)
        textSize = typedArray.getDimension(R.styleable.CircleTextView_textSize, textSize)
        typedArray.recycle()

        paint.isAntiAlias = true
        paint.color = circleColor
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = textSize

        // Set up border paint
        borderPaint.isAntiAlias = true
        borderPaint.color = borderColor
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = borderWidth

        // Set shadow properties
        setLayerType(LAYER_TYPE_SOFTWARE, null) // Enable software rendering for shadows
        paint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw the circle
        val radius = width.coerceAtMost(height) / 2f
        canvas.drawCircle(width / 2f, height / 2f, radius, paint)

        // Draw the border
        canvas.drawCircle(width / 2f, height / 2f, radius - borderWidth / 2, borderPaint)

        // Draw the text
        paint.color = textColor
        canvas.drawText(text, width / 2f, height / 2f - (paint.descent() + paint.ascent()) / 2, paint)
    }

    fun setText(newText: String) {
        text = newText
        invalidate() // Redraw the view
    }

    fun setCircleColor(color: Int) {
        circleColor = color
        paint.color = circleColor
        invalidate() // Redraw the view
    }

    fun setBorderColor(color: Int) {
        borderColor = color
        borderPaint.color = borderColor
        invalidate() // Redraw the view
    }

    fun setBorderWidth(width: Float) {
        borderWidth = width
        borderPaint.strokeWidth = borderWidth
        invalidate() // Redraw the view
    }

    fun setShadowProperties(color: Int, radius: Float, dx: Float, dy: Float) {
        shadowColor = color
        shadowRadius = radius
        shadowDx = dx
        shadowDy = dy
        paint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor)
        invalidate() // Redraw the view
    }
}