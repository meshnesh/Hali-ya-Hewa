package com.munene.haliyahewa.util.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 * Set image loaded from drawable resources.
 *
 * @param name Image name to load from drawable resources.
 */
@BindingAdapter("imageName", requireAll = false)
fun ImageView.imageName(name: String?) {
    Glide.with(context)
        .load(resources.getIdentifier(name, "drawable", context.packageName))
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}
