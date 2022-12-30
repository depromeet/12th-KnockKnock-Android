package com.depromeet.knockknock.ui.home.bottom.select

interface BottomRoomSelectActionHandler {
    fun onRoomClicked(roomId: Int)
    fun onRoomSearchClicked()
    fun onCreateRoomClicked()
}