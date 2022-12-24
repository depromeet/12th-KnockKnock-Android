package com.depromeet.knockknock.ui.alarmroomhistory

sealed class AlarmNavigationAction {
    object NavigateToBookmarkEdit: AlarmNavigationAction()
    object NavigateToBookmarkFilterReset: AlarmNavigationAction()
    object NavigateToBookmarkFilterRoom: AlarmNavigationAction()
    object NavigateToBookmarkFilterPeriod: AlarmNavigationAction()
    class NavigateToReaction(val bookmarkIdx: Int) : AlarmNavigationAction()
}