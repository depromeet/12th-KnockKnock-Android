package com.depromeet.knockknock.ui.alarmroomsearch.model

import com.depromeet.knockknock.ui.editroomdetails.model.Background

data class AlarmRoom(
    val roomType: String = "OPEN", //홀로외침방인지, 친구방인지
    val roomId: Int,
    val roomName: String,
    val roomBackground: String? = null,
    val roomThumbnail: String,
    val roomDescription: String,
    val roomIsUnpublic: Boolean = true,
    val roomCategoryName : String,
    val roomMemberCount : Int
)
