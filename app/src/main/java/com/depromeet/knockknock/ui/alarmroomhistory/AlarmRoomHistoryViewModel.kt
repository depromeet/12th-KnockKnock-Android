package com.depromeet.knockknock.ui.alarmroomhistory

import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.alarmcreate.AlarmCreateNavigationAction
import com.depromeet.knockknock.ui.alarmroomhistory.model.HistoryBundle
import com.depromeet.knockknock.ui.alarmroomhistory.model.HistoryMessage
//import com.dida.android.presentation.views.nav.home.HomeNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmRoomHistoryViewModel @Inject constructor(
) : BaseViewModel(), AlarmRoomHistoryActionHandler {

    private val TAG = "AlarmRoomHistoryViewModel"
    private val _navigationEvent: MutableSharedFlow<AlarmCreateNavigationAction> =
        MutableSharedFlow<AlarmCreateNavigationAction>()
    val navigationEvent: SharedFlow<AlarmCreateNavigationAction> = _navigationEvent.asSharedFlow()
    private val _alarmRoomHistoryBundleEvent: MutableStateFlow<List<HistoryBundle>> = MutableStateFlow(emptyList())
    val alarmRoomHistoryBundleEvent: StateFlow<List<HistoryBundle>> = _alarmRoomHistoryBundleEvent
    private val _alarmRoomHistoryMessageEvent: MutableStateFlow<List<HistoryMessage>> = MutableStateFlow(emptyList())
    val alarmRoomHistoryMessageEvent: StateFlow<List<HistoryMessage>> = _alarmRoomHistoryMessageEvent

    init {
        getTempList()
        getTempList2()
    }

    private fun getTempList() {
        val test1 = HistoryBundle("오늘")
        val test2 = HistoryBundle("어제")
        val test3 = HistoryBundle("2022년 12월 21일")
        val test4 = HistoryBundle("2022년 12월 20일")
        val test5 = HistoryBundle("2022년 12월 19일")
        val test6 = HistoryBundle("2022년 12월 18일")
        val test7 = HistoryBundle("2022년 12월 17일")

        val testList = listOf(test1, test2, test3, test4, test5, test6, test7)

        baseViewModelScope.launch {
            _alarmRoomHistoryBundleEvent.value = testList
        }
    }

    private fun getTempList2() {
        val test1 = HistoryMessage(1)
        val test2 = HistoryMessage(1)
        val test3 = HistoryMessage(1)
        val test4 = HistoryMessage(1)
        val test5 = HistoryMessage(1)
        val test6 = HistoryMessage(1)
        val test7 = HistoryMessage(1)

        val testList = listOf(test1, test2, test3, test4, test5, test6, test7)

        baseViewModelScope.launch {
            _alarmRoomHistoryMessageEvent.value = testList
        }
    }

    override fun onCreatePushClicked() {
        TODO("Not yet implemented")
    }

    override fun onRoomClicked(roomId: Int) {
        TODO("Not yet implemented")
    }

    override fun onRecentAlarmClicked(alarmId: Int) {
        TODO("Not yet implemented")
    }

    override fun onReactionClicked(alarmId: Int) {
        TODO("Not yet implemented")
    }

    override fun onNotificationClicked() {
        TODO("Not yet implemented")
    }

    override fun onSearchRoomClicked() {
        TODO("Not yet implemented")
    }

    override fun onCreateRoomClicked() {
        TODO("Not yet implemented")
    }

    override fun onRecentAlarmMoreClicked(alarmId: Int) {
        TODO("Not yet implemented")
    }
}