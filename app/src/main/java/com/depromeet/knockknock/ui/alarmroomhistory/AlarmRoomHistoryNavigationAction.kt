package com.depromeet.knockknock.ui.alarmroomhistory

sealed class AlarmRoomHistoryNavigationAction {
    object NavigateToBookmarkEdit: AlarmRoomHistoryNavigationAction()
    object NavigateToBookmarkFilterReset: AlarmRoomHistoryNavigationAction()
    object NavigateToBookmarkFilterRoom: AlarmRoomHistoryNavigationAction()
    object NavigateToBookmarkFilterPeriod: AlarmRoomHistoryNavigationAction()
    class NavigateToReaction(val bookmarkIdx: Int) : AlarmRoomHistoryNavigationAction()
    class NavigateToAlarmMore(val roomId: Int) : AlarmRoomHistoryNavigationAction()
}