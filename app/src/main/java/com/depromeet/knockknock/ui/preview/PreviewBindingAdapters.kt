package com.depromeet.knockknock.ui.preview

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imgLoad")
fun ImageView.bindImgLoad(uri: String) {
    if (uri != "") Glide.with(this).load(uri).into(this)
}