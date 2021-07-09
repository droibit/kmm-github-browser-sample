package com.example.android.app.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.android.app.R

class RetryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val messageText: TextView

    private val retryButton: Button

    var message: String
        get() = messageText.text.toString()
        set(value) {
            messageText.text = value
        }

    init {
        View.inflate(context, R.layout.view_retry, this)

        messageText = findViewById(R.id.message)
        messageText.text = "Error!"
        retryButton = findViewById(R.id.retryButton)

        isFocusable = true
        isClickable = true
    }

    fun setRetryCallback(callback: Callback?) {
        if (callback == null) {
            retryButton.setOnClickListener(null)
        } else {
            retryButton.setOnClickListener {
                callback.retry()
            }
        }
    }

    fun interface Callback {
        fun retry()
    }
}