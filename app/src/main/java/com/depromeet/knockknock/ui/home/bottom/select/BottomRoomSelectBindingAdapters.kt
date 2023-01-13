package com.depromeet.knockknock.ui.home.bottom.select

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.depromeet.domain.model.Notification
import com.depromeet.domain.model.NotificationList
import com.depromeet.knockknock.R
import com.depromeet.knockknock.util.bindBookmarkContentsImage

@BindingAdapter("isEmptyList")
fun ConstraintLayout.bindIsEmptyList(empty: Boolean) {
    if(empty)
        this.visibility = View.VISIBLE
    else
        this.visibility = View.GONE
}