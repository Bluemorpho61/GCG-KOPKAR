package com.alkindi.kopkar.ui.customview

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.alkindi.kopkar.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class DatePicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    deffStyleAttr: Int = android.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, deffStyleAttr) {
    private var cal = Calendar.getInstance()
    private var icon: Drawable? = null

    init {
        isFocusable = false
        setOnClickListener {
            showDatePicker()
            updateView()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        icon = ContextCompat.getDrawable(context, R.drawable.baseline_calendar_month_24)
        icon?.let {
            val right = width - paddingRight
            val left = width - it.intrinsicWidth
            val top = (height - it.intrinsicWidth) / 2
            val bottom = top + it.intrinsicWidth
            it.setBounds(left, top, right, bottom)
            it.draw(canvas)
        }
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateView()
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun updateView() {
//        val calFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        val calFormat ="dd-MM-yyyy"
        val sdf = SimpleDateFormat(calFormat, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        setText(sdf.format(cal.time))
    }
}