package com.depromeet.knockknock.ui.setprofile

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.depromeet.knockknock.R

@BindingAdapter("setBtnVisibie")
fun TextView.bindSetBtnVisibie(enable: Boolean) {
    if(enable) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

@SuppressLint("UseCompatLoadingForDrawables")
@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter("setBtnBackgroundTextColor")
fun TextView.bindSetBtnBackground(possible: Boolean) {
    if(possible) {
        this.background = context.getDrawable(R.drawable.custom_yellow_radius16)
        this.setTextColor(context.getColor(R.color.black))
    } else {
        this.background = context.getDrawable(R.drawable.custom_backgroundwhite_radius16)
        this.setTextColor(context.getColor(R.color.gray08))
    }
}