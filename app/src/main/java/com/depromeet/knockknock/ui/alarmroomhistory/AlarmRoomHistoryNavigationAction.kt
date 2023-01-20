package com.depromeet.knockknock.ui.alarmroomhistory

sealed class AlarmRoomHistoryNavigationAction {
    object NavigateToBookmarkEdit : AlarmRoomHistoryNavigationAction()
    object NavigateToBookmarkFilterReset : AlarmRoomHistoryNavigationAction()
    object NavigateToBookmarkFilterRoom : AlarmRoomHistoryNavigationAction()
    object NavigateToBookmarkFilterPeriod : AlarmRoomHistoryNavigationAction()
    class NavigateToReaction(val notification_id: Int, val reaction_id: Int, val notification_reaction_id: Int) :
        AlarmRoomHistoryNavigationAction()
    class NavigateToAlarmMore(val sendUserId: Int, val alarmId: Int, val title: String, val message: String) :
        AlarmRoomHistoryNavigationAction()

    class NavigateToAlarmCreate(
        val roomId: Int,
        val roomTitle: String,
        val title: String,
        val copyMessage: String,
        val reservation: Int
    ) : AlarmRoomHistoryNavigationAction()

    class NavigateToSettingRoom(val alarmId: Int) : AlarmRoomHistoryNavigationAction()
    class NavigateToSettingRoomForUser(val alarmId: Int) : AlarmRoomHistoryNavigationAction()


}