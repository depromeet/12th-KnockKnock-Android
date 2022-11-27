package com.depromeet.knockknock.base

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.DialogDefaultAlertBinding

@BindingAdapter("textVisibleCheck")
fun TextView.setTextVisibleCheck(contents: String?) {
    if(contents == null) this.visibility = View.GONE
    else {
        this.visibility = View.VISIBLE
        this.text = contents
    }
}