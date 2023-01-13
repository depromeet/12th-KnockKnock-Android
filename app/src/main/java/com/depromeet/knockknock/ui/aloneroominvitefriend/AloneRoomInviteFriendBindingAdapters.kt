package com.depromeet.knockknock.ui.aloneroominvitefriend

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.depromeet.knockknock.R

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("InviteFriendListSaveBtnEnable")
fun TextView.bindInviteFriendListSaveBtnEnable(enable: Boolean) {
    if(enable) {
        this.setTextColor(context.getColor(R.color.black))
        this.background = context.getDrawable(R.drawable.custom_yellow_radius10)
    } else {
        this.setTextColor(context.getColor(R.color.gray08))
        this.background = context.getDrawable(R.drawable.custom_backgroundwhite_radius10)
    }
}

