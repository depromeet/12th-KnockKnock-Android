package com.depromeet.knockknock.util

import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.time.format.DateTimeFormatter

//    yyyy-MM-dd	“2019-07-04”
//    dd-MMM-yyyy	“04-July-2019”
//    dd/MM/yyyy	“04/07/2019”
//    yyyy-MM-dd'T'HH:mm:ssZ	“2019-07-04T12:30:30+0530”
//    h:mm a	“12:00 PM”
//    yyyy년 MM월 dd일	"2019년 01월 10일"

@RequiresApi(Build.VERSION_CODES.O)
fun String.toDay(): String {
    val date = this
    return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("dateToDay")
fun TextView.bindDateToDay(str: String) {
    this.text = str.toDay()
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.toDayAndTime(): String {
    val date = this
    val day = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    val time = date.format(DateTimeFormatter.ofPattern("h:mm a"))
    return "$day $time"
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("dateToDayAndTime")
fun TextView.bindDateToDayAndTime(str: String) {
    this.text = str.toDayAndTime()
}