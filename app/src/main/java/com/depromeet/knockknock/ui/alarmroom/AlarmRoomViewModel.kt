package com.depromeet.knockknock.ui.alarmroom

import com.depromeet.knockknock.base.BaseViewModel
import com.dida.android.presentation.views.nav.home.HomeNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmRoomViewModel @Inject constructor(
) : BaseViewModel() {

    private val TAG = "AlarmRoomViewModel"

}