package com.depromeet.knockknock.ui.editbookmark

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.depromeet.knockknock.R
import com.depromeet.knockknock.ui.editprofile.bindEditBtnBackground

@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter("editBtnEnable")
fun TextView.bindEditBtnEnable(enable: List<Int>) {
    if(enable.isEmpty()) this.setTextColor(context.getColor(R.color.gray07))
    else this.setTextColor(context.getColor(R.color.main_yellow_light_mode))
}