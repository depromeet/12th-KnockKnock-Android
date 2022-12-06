package com.depromeet.knockknock.di

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PresentationApplication :Application(){

    override fun onCreate() {
        super.onCreate()
        // 다크모드 비활성화
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        KakaoSdk.init(this, "30f2e9f0a932561533c47548a6cc4b52")

////        mySharedPreferences = MySharedPreferences(applicationContext)
//        dataStorePreferences = DataStorePreferences(applicationContext)
//        // Kakao SDK 초기화
//        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
//        // kakao hash key 추출
//        Log.d("getKeyHash", "" + getKeyHash(this))
    }
}