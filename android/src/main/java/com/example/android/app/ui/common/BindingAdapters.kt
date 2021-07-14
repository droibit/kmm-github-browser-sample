package com.example.android.app.ui.common

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import coil.size.ViewSizeResolver
import coil.transform.CircleCropTransformation

@BindingAdapter(
    "visibleUnless",
    "requestFocusOnVisible",
    requireAll = false
)
fun View.bindVisibleUnless(visible: Boolean, requestFocus: Boolean = false) {
    isVisible = visible

    if (isVisible && requestFocus) {
        requestFocus()
    }
}

@BindingAdapter(
    "goneUnless",
    "requestFocusOnVisible",
    requireAll = false
)
fun View.bindGoneUnless(gone: Boolean, requestFocus: Boolean = false) {
    isGone = gone

    if (isVisible && requestFocus) {
        requestFocus()
    }
}

@BindingAdapter("imageUrl", "placeholder", "error")
fun ImageView.bindImageUrl(url: String?, placeholder: Drawable, error: Drawable) {
    load(url) {
        size(ViewSizeResolver(this@bindImageUrl))
        crossfade(true)
        placeholder(placeholder)
        error(error)
        transformations(CircleCropTransformation())
    }
}

@BindingAdapter("android:text")
fun RetryView.bindText(text: String?) {
    if (text.isNullOrEmpty()) {
        return
    }
    message = text
}

@BindingAdapter("android:text")
fun RetryView.bindMessage(message: Message?) {
    if (message == null) {
        return
    }
    this.message = message.getString(context)
}

@BindingAdapter("retryCallback")
fun RetryView.bindCallback(callback: RetryView.Callback) {
    setRetryCallback(callback)
}