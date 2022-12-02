package com.depromeet.knockknock.ui.friendlist

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.depromeet.knockknock.R

@BindingAdapter("viewVisible")
fun ConstraintLayout.bindViewVisible(query: String) {
    if(query != "") this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}