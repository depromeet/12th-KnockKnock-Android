package com.depromeet.knockknock.ui.register

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.depromeet.knockknock.R

fun textChangeColor(
    text: TextView,
    color: String,
    start: Int,
    end: Int
): SpannableStringBuilder {
    val builder = SpannableStringBuilder(text.text.toString())

    builder.setSpan(
        ForegroundColorSpan(Color.parseColor(color)),
        start,
        end,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    return builder
}

@BindingAdapter("deleteBtnTextVisible")
fun TextView.bindDeleteBtnTextVisible(text: String) {
    if (text.isEmpty()) {
        this.setTextColor(Color.parseColor("#BDBDBD"))
        this.isClickable = false
    } else {
        this.setTextColor(Color.parseColor("#212121"))
        this.isClickable = true
    }
}
