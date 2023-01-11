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
import com.depromeet.knockknock.ui.alarmroomhistory.adapter.createAlarmRoomHistoryMessagePager
import com.depromeet.knockknock.ui.bookmark.BookmarkNavigationAction
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
    private val _periodClicked: MutableStateFlow<Int> = MutableStateFlow<Int>(0)
    val periodClicked: StateFlow<Int> = _periodClicked
    var alarmRoomTitleEvent = MutableStateFlow<String>("")
    var alarmRoomDescriptionEvent = MutableStateFlow<String>("")
    var alarmDateEvent = MutableStateFlow<String>("")
    val emptyMessage: String = ""
    var groupId = MutableStateFlow<Int>(30)
    var reservationId = MutableStateFlow<Int>(0)
    var sort = MutableStateFlow<String>("")
    var reservationTimeEvent = MutableStateFlow<String>("")
    var reservationTitleEvent = MutableStateFlow<String>("")
    var reservationMessageEvent = MutableStateFlow<String>("")
    val reservationMessageImgUri: MutableStateFlow<String> = MutableStateFlow<String>("")
    var pushAlarmList: Flow<PagingData<Notification>> = emptyFlow()
    var isPublicAccess = MutableStateFlow<Boolean>(true)

    init {
        getGroupAdmissions()
        getPushAlarm()
    }

    fun getPushAlarm() {
        pushAlarmList = createAlarmRoomHistoryMessagePager(
            mainRepository = mainRepository,
            groupId = groupId,
            sort = sort,
            viewModel = this
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

    fun stroageReaction(reaction_id: Int, notification_id: Int) {
        baseViewModelScope.launch {
            showLoading()
            mainRepository.postReactions(
                notification_id = notification_id,
                reaction_id = reaction_id
            )
                .onSuccess { _navigationEvent.emit(AlarmRoomHistoryNavigationAction.NavigateToBookmarkFilterReset) }
            dismissLoading()
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

    override fun onReactionClicked(notification_id: Int, reaction_id: Int) {
        baseViewModelScope.launch {
            _navigationEvent.emit(
                AlarmRoomHistoryNavigationAction.NavigateToReaction(
                    notification_id = notification_id,
                    reaction_id = reaction_id
                )
            )
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

    fun onAlarmCreateClicked(
        roomId: Int,
        title: String,
        copyMessage: String,
        reservation: Boolean
    ) {
        baseViewModelScope.launch {
            _navigationEvent.emit(
                AlarmRoomHistoryNavigationAction.NavigateToAlarmCreate(
                    roomId,
                    title,
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

    override fun onAlarmSaveClicked(alarmId: Int) {
        baseViewModelScope.launch {
            mainRepository.postStorages(alarmId)
                .onSuccess {

                    Log.d("ttt", "보관함 저장 성공")


                }.onError {
                    Log.d("ttt", "보관함 저장 실패")
            }
        }
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

    fun onReportClicked(alarmId: Int){
//        baseViewModelScope.launch {
//            mainRepository.postre(alarmId)
//                .onSuccess {
//
//                    Log.d("ttt", "보관함 저장 성공")
//
//
//                }.onError {
//                    Log.d("ttt", "보관함 저장 실패")
//                }
//        }

    }
}