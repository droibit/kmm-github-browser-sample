package com.example.android.app.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.withStyledAttributes
import com.example.android.app.R

class EmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_empty, this)

        context.withStyledAttributes(attrs, R.styleable.EmptyView, defStyleAttr) {
            // val background = getColor(
            //     R.styleable.ProgressView_android_background,
            //     ContextCompat.getColor(context, R.color.black_800_alpha_010)
            // )
            // setBackgroundColor(background)

            val text = getString(
                R.styleable.EmptyView_android_text,
            ) ?: context.getString(R.string.no_data)

            val textView = findViewById<TextView>(R.id.emptyText)
            textView.text = text
        }

        isFocusable = true
        isClickable = true
    }
}