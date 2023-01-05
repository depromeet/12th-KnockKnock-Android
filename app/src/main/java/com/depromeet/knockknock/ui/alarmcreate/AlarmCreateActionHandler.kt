package com.depromeet.knockknock.ui.alarmcreate

interface AlarmCreateActionHandler {
    fun onAddImageClicked()
    fun onAlarmPushClicked()
    fun onReservationAlarmPushClicked(sendAt: String)
    fun onDeleteEditTextMessageClicked()
    fun onFocusEditTextTitleClicked()
    fun onRecommendationMessageClicked(message: String)
    fun onPreviewClicked(title: String, message: String, uri: String)
    fun onAlarmSendClicked()
    fun  onUpdateReservationAlarmPushClicked(reservationId : Int, sendAt: String)
}