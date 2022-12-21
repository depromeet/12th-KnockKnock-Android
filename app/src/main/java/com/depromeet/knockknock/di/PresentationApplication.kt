package com.depromeet.knockknock.di

import android.app.Application
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

@HiltAndroidApp
class PresentationApplication :Application(){

    override fun onCreate() {
        super.onCreate()
        // 다크모드 비활성화
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        KakaoSdk.init(this, "6776355fa5aec1a02126ce3817cce784")
        val keyHash = Utility.getKeyHash(this)//onCreate 안에 입력해주자
        Log.d("HashKey", keyHash)
    }
}