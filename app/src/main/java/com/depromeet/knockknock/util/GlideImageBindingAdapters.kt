package com.depromeet.knockknock.util

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

@BindingAdapter("userProfile")
fun ImageView.bindUserProfile(profileUri: String?) {
    profileUri?.let {
        Glide.with(context)
            .load(it)
            .transform(CenterCrop(), RoundedCorners(1000))
            .into(this)
    }
}

@BindingAdapter("bookmarkContentsImage")
fun ImageView.bindBookmarkContentsImage(contentsImageUri: String?) {
    contentsImageUri?.let {
        Glide.with(context)
            .load(it)
            .transform(CenterCrop(), RoundedCorners(50))
            .into(this)
    }
}

@BindingAdapter("reservationContentsImage")
fun ImageView.bindReservationContentsImage(contentsImageUri: String?) {
    if (contentsImageUri!!.isNotEmpty()){
        contentsImageUri.let {
            Glide.with(context)
                .load(it)
                .transform(CenterCrop(), RoundedCorners(50))
                .into(this)
        }
    }else{
        Log.d("ttt", "hi")

        this.visibility = View.GONE

    }
    Log.d("ttt reservation image", contentsImageUri.toString())
    Log.d("ttt reservation image", contentsImageUri!!.length.toString())
}

@BindingAdapter("alarmContentsImage")
fun ImageView.bindAlarmContentsImage(contentsImageUri: String?) {
    if (contentsImageUri!!.isNotEmpty()){
        contentsImageUri.let {
            Glide.with(context)
                .load(it)
                .transform(CenterCrop(), RoundedCorners(50))
                .into(this)
        }
    }else{
        Log.d("ttt", "hi")

        this.visibility = View.GONE

    }
    Log.d("ttt reservation image", contentsImageUri.toString())
    Log.d("ttt reservation image", contentsImageUri!!.length.toString())


}

@BindingAdapter("roomImageImage")
fun ImageView.bindRoomImageImage(roomImgUri: String) {
    Glide.with(context)
        .load(roomImgUri)
        .transform(CenterCrop(), RoundedCorners(300))
        .into(this)
}

@BindingAdapter("roomBackgroundImage")
fun ImageView.bindroomBackgroundImage(backgroundImageUri: String) {
    Glide.with(context)
        .load(backgroundImageUri)
        .transform(CenterCrop(), RoundedCorners(20))
        .into(this)
}

@BindingAdapter("roomThumbnailImage")
fun ImageView.bindroomThumbnailImage(thumbnailImageUri: String) {
    Glide.with(context)
        .load(thumbnailImageUri)
        .transform(CenterCrop(), RoundedCorners(20))
        .into(this)
}

@BindingAdapter("roomBackgroundImage10")
fun ImageView.bindroomBackgroundImage10(backgroundImageUri: String) {
    Glide.with(context)
        .load(backgroundImageUri)
        .transform(CenterCrop(), RoundedCorners(10))
        .into(this)
}

@BindingAdapter("glide10000")
fun ImageView.bindGlide10000(uri: String?) {
    uri?.let {
        Glide.with(context)
            .load(it)
            .transform(CenterCrop(), RoundedCorners(10000))
            .into(this)
    }
}

@BindingAdapter("glide1000")
fun ImageView.bindGlide1000(uri: String?) {
    uri?.let {
        Glide.with(context)
            .load(it)
            .transform(CenterCrop(), RoundedCorners(1000))
            .into(this)
    }
}

@BindingAdapter("glide300")
fun ImageView.bindGlide300(uri: String?) {
    uri?.let {
        Glide.with(context)
            .load(it)
            .transform(CenterCrop(), RoundedCorners(300))
            .into(this)
    }
}

@BindingAdapter("glide100")
fun ImageView.bindGlide100(uri: String?) {
    uri?.let {
        Glide.with(context)
            .load(it)
            .transform(CenterCrop(), RoundedCorners(100))
            .into(this)
    }
}

@BindingAdapter("glide50")
fun ImageView.bindGlide50(uri: String?) {
    uri?.let {
        Glide.with(context)
            .load(it)
            .transform(CenterCrop(), RoundedCorners(50))
            .into(this)
    }
}


@BindingAdapter("imageNoRadius")
fun ImageView.bindImageNoRadius(roomImgUri: String) {
    Glide.with(context)
        .load(roomImgUri)
        .into(this)
}