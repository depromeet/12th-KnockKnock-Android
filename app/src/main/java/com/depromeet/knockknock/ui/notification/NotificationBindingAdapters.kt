package com.depromeet.knockknock.ui.notification

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.depromeet.knockknock.R
import com.depromeet.knockknock.ui.notification.model.Notification

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("notificationIsEmptyBackgroundColor")
fun ConstraintLayout.bindNotificationIsEmptyBackgroundColor(notificationList: List<Notification>) {
    if(notificationList.isEmpty()) this.background = context.getDrawable(R.color.white)
    else this.background = context.getDrawable(R.color.background_white_mode)
}

@BindingAdapter("notificationIsEmptyView")
fun View.bindNotificationIsEmptyView(notificationList: List<Notification>) {
    if(notificationList.isEmpty()) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

@BindingAdapter("notificationIsNotEmptyView")
fun View.bindNotificationIsNotEmptyView(notificationList: List<Notification>) {
    if(notificationList.isEmpty()) this.visibility = View.GONE
    else this.visibility = View.VISIBLE
}


@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("notificationIsEmptyBackground")
fun View.bindNotificationIsEmptyBackground(notificationList: List<Notification>) {
    if(notificationList.isEmpty()) this.background = context.getDrawable(R.color.white)
    else this.background = context.getDrawable(R.color.background_white_mode)
}