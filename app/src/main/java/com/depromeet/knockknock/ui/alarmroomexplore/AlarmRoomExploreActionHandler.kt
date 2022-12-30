package com.depromeet.knockknock.ui.alarmroomexplore

interface AlarmRoomExploreActionHandler {
    fun onAlarmRoomEditTextClicked()
    fun onCategoryClicked(categoryId : Int)
    fun onPopularRoomClicked(roomId: Int)
    fun onRoomClicked(roomId : Int)
}