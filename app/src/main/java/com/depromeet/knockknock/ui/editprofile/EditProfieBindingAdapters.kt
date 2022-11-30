package com.depromeet.knockknock.ui.editprofile

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

@BindingAdapter("editBtnVisibie")
fun TextView.bindEditBtnVisibie(enable: Boolean) {
    if(enable) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}