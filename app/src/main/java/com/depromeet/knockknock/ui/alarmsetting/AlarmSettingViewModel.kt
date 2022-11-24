package com.depromeet.knockknock.ui.alarmsetting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.depromeet.knockknock.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AlarmSettingViewModel @Inject constructor(
) : BaseViewModel(), AlarmSettingsActionHandler {

    private val TAG = "AlarmSettingViewModel"

    private val _alarmPushPermitted: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val alarmPushPermitted: StateFlow<Boolean> = _alarmPushPermitted

    private val _reactionPushPermitted: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val reactionPushPermitted: StateFlow<Boolean> = _reactionPushPermitted

    private val _notReceivedPushAtNightPermitted: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val notReceivedPushAtNightPermitted: StateFlow<Boolean> = _notReceivedPushAtNightPermitted

    override fun onNewPushToggled(checked: Boolean) {
    }

    override fun onReactionPushToggled(checked: Boolean) {
    }

    override fun onNotReceivedPushAtNight(checked: Boolean) {
    }

}