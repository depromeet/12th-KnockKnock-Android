package com.depromeet.knockknock.ui.alarmroomhistory

interface AlarmRoomHistoryActionHandler {
    fun onCreatePushClicked()
    fun onRoomClicked(roomId: Int)
    fun onRecentAlarmClicked(alarmId: Int)
    fun onReactionClicked(notification_id: Int, reaction_id: Int, notification_reaction_id: Int)
    fun onNotificationClicked()
    fun onSearchRoomClicked()
    fun onCreateRoomClicked()
    fun onRecentAlarmMoreClicked(sendUserId: Int, alarmId: Int, title: String, message: String)
    fun onDeleteReservationAlarmClicked(reservationId: Int)
    fun postGroupAdmissionsAllow(admissionId: Int)
    fun onGroupAdmissionsRefuse(admissionId: Int)
    fun onAlarmSaveClicked(alarmId: Int)
}