package com.depromeet.knockknock.di

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.depromeet.data.DataApplication
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class PresentationApplication :Application(){

    // 코틀린의 전역변수 문법
    companion object {
        // 만들어져있는 SharedPreferences 를 사용해야합니다. 재생성하지 않도록 유념해주세요
        lateinit var sSharedPreferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
    }

    override fun onCreate() {
        super.onCreate()
        // 다크모드 비활성화
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        KakaoSdk.init(this, "6776355fa5aec1a02126ce3817cce784")
        val keyHash = Utility.getKeyHash(this)
        Log.d("HashKey", keyHash)

        DataApplication.sSharedPreferences = applicationContext.getSharedPreferences("KnockKnock", MODE_PRIVATE)
        DataApplication.editor = DataApplication.sSharedPreferences.edit()

        sSharedPreferences = DataApplication.sSharedPreferences
        editor = sSharedPreferences.edit()

        Log.d("access_token", sSharedPreferences.getString("access_token", "").toString())
    }
}