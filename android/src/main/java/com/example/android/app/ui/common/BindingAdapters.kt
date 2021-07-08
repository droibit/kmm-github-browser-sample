package com.example.android.app.ui.common

import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

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