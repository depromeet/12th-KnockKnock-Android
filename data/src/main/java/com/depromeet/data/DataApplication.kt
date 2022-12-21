package com.depromeet.data

import android.app.Application
import android.content.SharedPreferences

class DataApplication :Application(){
    // 코틀린의 전역변수 문법
    companion object {
        // 만들어져있는 SharedPreferences 를 사용해야합니다. 재생성하지 않도록 유념해주세요
        lateinit var sSharedPreferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor

    }

    override fun onCreate() {
        super.onCreate()
        sSharedPreferences = applicationContext.getSharedPreferences("KnockKnock", MODE_PRIVATE)
        editor = sSharedPreferences.edit()
    }
}