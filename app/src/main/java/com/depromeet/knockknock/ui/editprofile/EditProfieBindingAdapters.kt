package com.depromeet.knockknock.ui.editprofile

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.depromeet.knockknock.R

@BindingAdapter("editBtnVisibie")
fun TextView.bindEditBtnVisibie(enable: Boolean) {
    if(enable) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

@SuppressLint("UseCompatLoadingForDrawables")
@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter("editBtnBackgroundTextColor")
fun TextView.bindEditBtnBackground(possible: Boolean) {
    if(possible) {
        this.background = context.getDrawable(R.drawable.custom_yellow_radius16)
        this.setTextColor(context.getColor(R.color.black))
    } else {
        this.background = context.getDrawable(R.drawable.custom_backgroundwhite_radius16)
        this.setTextColor(context.getColor(R.color.gray08))
    }
}

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("onFocusBackground")
fun EditText.bindOnFocusBackground(required: Boolean? = false) {
    if(this.hasFocus()) this.background = context.getDrawable(R.drawable.custom_backgroundwhite_radius16_line_gray06)
    else this.background = context.getDrawable(R.drawable.custom_backgroundwhite_radius16)
}