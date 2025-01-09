package com.alkindi.kopkar.ui.customview

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.alkindi.kopkar.R

class CustomButtonForUploadAttachment @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), View.OnTouchListener {
    private var imgIcon: ImageView
    private var tvButtonText: TextView
    private var fileName: String? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_upload_attachment_button, this, true)

        imgIcon = findViewById(R.id.imgIcon)
        tvButtonText = findViewById(R.id.tvButtonText)

        setOnClickListener {
            openFilePicker()
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
        }
        (context as? Activity)?.startActivityForResult(intent, REQUEST_CODE_PICK_FILE)
    }

    fun setFileName(fileName: String) {
        this.fileName = fileName
        tvButtonText.text = fileName
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_PICK_FILE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                val fileName = getFileName(uri)
                setFileName(fileName)
            }
        }
    }

    private fun getFileName(uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                   val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (nameIndex >=0){
                        result =it.getString(nameIndex)
                    }
                }
            }
        }
        return result ?: uri.path?.substringAfterLast('/') ?: "Unknown"
    }

    override fun onTouch(v: View?, evt: MotionEvent?): Boolean {
        return false
    }


    companion object {
        const val REQUEST_CODE_PICK_FILE = 1001
    }
}