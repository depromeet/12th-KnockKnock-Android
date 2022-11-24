package com.depromeet.knockknock.ui.alarmsetting

interface AlarmSettingsActionHandler {
    fun onNewPushToggled(checked: Boolean)
    fun onReactionPushToggled(checked: Boolean)
    fun onNotReceivedPushAtNight(checked: Boolean)
}