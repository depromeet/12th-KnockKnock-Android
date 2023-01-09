package com.depromeet.knockknock.ui.alarmroomhistory

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.depromeet.domain.model.Admission
import com.depromeet.domain.model.Notification
import com.depromeet.domain.onError
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.alarmroomhistory.adapter.createAlarmRoomHistoryBundlePager
import com.depromeet.knockknock.ui.alarmroomhistory.model.HistoryBundle
import com.depromeet.knockknock.ui.alarmroomhistory.model.HistoryMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmRoomHistoryViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), AlarmRoomHistoryActionHandler {

    private val TAG = "AlarmRoomHistoryViewModel"
    private val _navigationEvent: MutableSharedFlow<AlarmRoomHistoryNavigationAction> =
        MutableSharedFlow<AlarmRoomHistoryNavigationAction>()
    val navigationEvent: SharedFlow<AlarmRoomHistoryNavigationAction> =
        _navigationEvent.asSharedFlow()
    private val _alarmInviteRoomEvent: MutableStateFlow<List<Admission>> =
        MutableStateFlow(emptyList())
    val alarmInviteRoomEvent: StateFlow<List<Admission>> = _alarmInviteRoomEvent
    private val _alarmRoomHistoryBundleEvent: MutableStateFlow<List<HistoryBundle>> =
        MutableStateFlow(emptyList())
    val alarmRoomHistoryBundleEvent: StateFlow<List<HistoryBundle>> = _alarmRoomHistoryBundleEvent
    private val _alarmRoomHistoryMessageEvent: MutableStateFlow<List<HistoryMessage>> =
        MutableStateFlow(emptyList())
    val alarmRoomHistoryMessageEvent: StateFlow<List<HistoryMessage>> =
        _alarmRoomHistoryMessageEvent
    private val _periodClicked: MutableStateFlow<Int> = MutableStateFlow<Int>(0)
    val periodClicked: StateFlow<Int> = _periodClicked
    val emptyMessage: String = ""
    var groupId = MutableStateFlow<Int>(30)
    var reservationId = MutableStateFlow<Int>(0)
    var sort = MutableStateFlow<String>("")
    var reservationTimeEvent = MutableStateFlow<String>("")
    var reservationTitleEvent = MutableStateFlow<String>("")
    var reservationMessageEvent = MutableStateFlow<String>("")
    val reservationMessageImgUri: MutableStateFlow<String> = MutableStateFlow<String>("")
    var pushAlarmList: Flow<PagingData<Notification>> = emptyFlow()

    init {
        getGroupAdmissions()
        reservationTimeEvent.value = "오늘 19:00 발송 예정"
        reservationTitleEvent.value = ""
        reservationMessageEvent.value =
            "푸시알림 텍스트는 2줄까지만 보여주세요. 2줄 이상 넘어갈 시에는 2줄 이상 넘어갈 시에는 2줄 이상 넘어갈 시에는 2줄 이상 넘어갈 시에는"

        getPushAlarm()
    }

    fun getPushAlarm() {
        pushAlarmList = createAlarmRoomHistoryBundlePager(
            mainRepository = mainRepository,
            groupId = groupId,
            sort = sort
        ).flow.cachedIn(baseViewModelScope)
    }

    // 방장 권한이 있어야 함
    private fun getGroupAdmissions() {

        baseViewModelScope.launch {
            mainRepository.getGroupAdmissions(groupId.value).onSuccess {
                _alarmInviteRoomEvent.value = it.admissions

            }.onSuccess {
                Log.d("ttt", "초대 확인 성공")
            }.onError {
                Log.d("ttt", "초대 확인 실패")
            }
        }
    }

    override fun postGroupAdmissionsAllow(admissionId: Int) {
        baseViewModelScope.launch {
            mainRepository.postGroupAdmissionsAllow(groupId.value, admissionId).onSuccess {

            }.onSuccess {
                Log.d("ttt", "초대 승인 성공")
            }.onError {
                Log.d("ttt", "초대 승인 실패")
            }
        }
    }

    override fun onGroupAdmissionsRefuse(admissionId: Int) {
        baseViewModelScope.launch {
            mainRepository.postGroupAdmissionsRefuse(groupId.value, admissionId).onSuccess {

            }.onSuccess {
                Log.d("ttt", "초대 거절 성공")
            }.onError {
                Log.d("ttt", "초대 거절 실패")
            }
        }
    }

    override fun onCreatePushClicked() {
        TODO("Not yet implemented")
    }

    override fun onRoomClicked(roomId: Int) {
        TODO("Not yet implemented")
    }

    override fun onRecentAlarmClicked(alarmId: Int) {

    }

    override fun onReactionClicked(bookmarkIdx: Int) {
        baseViewModelScope.launch {
            _navigationEvent.emit(AlarmRoomHistoryNavigationAction.NavigateToReaction(bookmarkIdx))
        }
    }

    override fun onDeleteReservationAlarmClicked(reservationId: Int) {
        baseViewModelScope.launch {
            mainRepository.deleteNotificationReservation(
                reservation_id = reservationId,
            ).onSuccess {

            }.onError {}
        }
    }

    fun onDeleteAlarmClicked(notificationId: Int) {
        baseViewModelScope.launch {
            mainRepository.deleteNotification(
                notification_id = notificationId,
            ).onSuccess {
                Log.d("ttt", "알림 삭제 성공")
            }.onError {
                Log.d("ttt", "알림 삭제 실패")
            }
        }
    }

    fun onAlarmCreateClicked(roomId: Int, copyMessage: String, reservation: Boolean) {
        baseViewModelScope.launch {
            _navigationEvent.emit(
                AlarmRoomHistoryNavigationAction.NavigateToAlarmCreate(
                    roomId,
                    copyMessage,
                    reservation
                )
            )
        }
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

    override fun onRecentAlarmMoreClicked(alarmId: Int, message: String) {
        baseViewModelScope.launch {
            _navigationEvent.emit(
                AlarmRoomHistoryNavigationAction.NavigateToAlarmMore(
                    alarmId,
                    message
                )
            )
        }
    }
}