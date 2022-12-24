package com.depromeet.knockknock.ui.alarmroomhistory.model

data class HistoryMessage(
    val alarmId: Int,
    val userName: String,
    val userImg: String,
    val datetime: String,
    val contents: String,
    val contentsImg: String? = null,
    val roomName: String,
    val reactionContents: String,
    val reactionCount: Int,
    var isExpanded: Boolean = false
)
