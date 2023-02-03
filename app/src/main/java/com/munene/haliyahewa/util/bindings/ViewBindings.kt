package com.munene.haliyahewa.util.bindings

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Simplification to check and setup view as visible.
 */
@set:BindingAdapter("visible")
var View.visible
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }
