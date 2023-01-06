package com.depromeet.knockknock.ui.notification

import android.annotation.SuppressLint
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.depromeet.domain.model.Alarm
import com.depromeet.knockknock.R

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("notificationIsEmptyBackgroundColor")
fun ConstraintLayout.bindNotificationIsEmptyBackgroundColor(notificationList: List<Alarm>) {
    if(notificationList.isEmpty()) this.background = context.getDrawable(R.color.white)
    else this.background = context.getDrawable(R.color.background_white_mode)
}

@BindingAdapter("notificationIsEmptyView")
fun View.bindNotificationIsEmptyView(notificationList: List<Alarm>) {
    if(notificationList.isEmpty()) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

@BindingAdapter("notificationIsNotEmptyView")
fun View.bindNotificationIsNotEmptyView(notificationList: List<Alarm>) {
    if(notificationList.isEmpty()) this.visibility = View.GONE
    else this.visibility = View.VISIBLE
}


@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("notificationIsEmptyBackground")
fun View.bindNotificationIsEmptyBackground(notificationList: List<Alarm>) {
    if(notificationList.isEmpty()) this.background = context.getDrawable(R.color.white)
    else this.background = context.getDrawable(R.color.background_white_mode)
}