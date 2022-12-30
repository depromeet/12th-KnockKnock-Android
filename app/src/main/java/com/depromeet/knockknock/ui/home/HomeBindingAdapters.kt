package com.depromeet.knockknock.ui.home

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

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("homeImage")
fun ImageView.bindHomeImage(number: Int) {
    when(number) {
        1 -> {
            Glide.with(context)
                .load(R.mipmap.img_home_1_foreground)
                .transform(CenterCrop())
                .into(this) }
        2 -> {
            Glide.with(context)
                .load(R.mipmap.img_home_2_foreground)
                .transform(CenterCrop())
                .into(this)}
        3 -> {
            Glide.with(context)
                .load(R.mipmap.img_home_3_foreground)
                .transform(CenterCrop())
                .into(this)}
        4 -> {
            Glide.with(context)
                .load(R.mipmap.img_home_4_foreground)
                .transform(CenterCrop())
                .into(this)}
    }
}

@BindingAdapter("homeEmptyRecentImg")
fun ConstraintLayout.bindHomeEmptyRecentImg(notifications: List<Notification>) {
    if(notifications.isEmpty())
        this.visibility = View.VISIBLE
    else
        this.visibility = View.GONE
}

@BindingAdapter("existedAlarm")
fun ImageView.bindExistedAlarm(existed: Boolean) {
    if(existed)
        this.visibility = View.VISIBLE
    else
        this.visibility = View.GONE
}