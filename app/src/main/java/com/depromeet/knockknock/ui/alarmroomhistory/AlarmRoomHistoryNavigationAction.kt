package com.depromeet.knockknock.ui.alarmroomhistory

sealed class AlarmRoomHistoryNavigationAction {
    object NavigateToBookmarkEdit : AlarmRoomHistoryNavigationAction()
    object NavigateToBookmarkFilterReset : AlarmRoomHistoryNavigationAction()
    object NavigateToBookmarkFilterRoom : AlarmRoomHistoryNavigationAction()
    object NavigateToBookmarkFilterPeriod : AlarmRoomHistoryNavigationAction()
    class NavigateToReaction(val notification_id: Int, val reaction_id: Int) :
        AlarmRoomHistoryNavigationAction()
    class NavigateToAlarmMore(val alarmId: Int, val message: String) :
        AlarmRoomHistoryNavigationAction()
    class NavigateToAlarmCreate(
        val roomId: Int,
        val roomTitle: String,
        val title: String,
        val copyMessage: String,
        val reservation: Int
    ) : AlarmRoomHistoryNavigationAction()
    class NavigateToSettingRoom(val alarmId: Int) : AlarmRoomHistoryNavigationAction()
    object NavigateToSettingRoomForUser : AlarmRoomHistoryNavigationAction()



}