package com.depromeet.knockknock.ui.alarmroomhistory

interface AlarmRoomHistoryActionHandler {
    fun onCreatePushClicked()
    fun onRoomClicked(roomId: Int)
    fun onRecentAlarmClicked(alarmId: Int)
    fun onReactionClicked(alarmId: Int)
    fun onNotificationClicked()
    fun onSearchRoomClicked()
    fun onCreateRoomClicked()
    fun onRecentAlarmMoreClicked(alarmId: Int, message: String)
    fun onDeleteReservationAlarmClicked(reservationId: Int)
    fun postGroupAdmissionsAllow(admissionId: Int)
    fun onGroupAdmissionsRefuse(admissionId: Int)
}