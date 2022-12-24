package com.depromeet.knockknock.ui.alarmroomsearch.model

data class AlarmRoom(
    val roomType: String, //홀로외침방인지, 친구방인지
    val roomId: Int,
    val roomName: String,
    val roomThumbnail: String,
    val roomDescription: String,
    val roomIsUnpublic: Boolean,
    val roomCategoryName : String,
    val roomMemberCount : Int
)
