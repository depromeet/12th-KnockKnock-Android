package com.depromeet.knockknock.ui.home.model

data class Alarm (
    val alarmId: Int,
    val userName: String,
    val userImg: String,
    val datetime: String,
    val contents: String,
    val contentsImg: String?,
    val reaction: String,
)