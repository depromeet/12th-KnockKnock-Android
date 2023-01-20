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
    var alarmInviteRoomSizeEvent = MutableStateFlow<String>("입장 요청")
    private val _periodClicked: MutableStateFlow<Int> = MutableStateFlow<Int>(0)
    val periodClicked: StateFlow<Int> = _periodClicked
    var alarmRoomTitleEvent = MutableStateFlow<String>("")
    var alarmRoomDescriptionEvent = MutableStateFlow<String>("")
    var alarmDateEvent = MutableStateFlow<String>("")
    var alarmInviteDateEvent = MutableStateFlow<String>("")
    val emptyMessage: String = ""
    var groupId = MutableStateFlow<Int>(0)
    var reservationId = MutableStateFlow<Int>(0)
    var sort = MutableStateFlow<String>("")
    var reservationTimeEvent = MutableStateFlow<String>("")
    var reservationTitleEvent = MutableStateFlow<String>("")
    var reservationMessageEvent = MutableStateFlow<String>("")
    val reservationMessageImgUri: MutableStateFlow<String> = MutableStateFlow<String>("")
    var pushAlarmList: Flow<PagingData<Notification>> = emptyFlow()
    var isPublicAccess = MutableStateFlow<Boolean>(true)
    var membersEvent = MutableStateFlow<String>("")
    var isHost = MutableStateFlow<Boolean>(true)
    var isMessage = MutableStateFlow<Boolean>(true)
    var editTextReportEvent = MutableStateFlow<String>("")
    var userId = MutableStateFlow<Int>(0)
    var participation = MutableStateFlow<Boolean>(false)
    var roomImgUri = MutableStateFlow<String>("")


    init {
        getProfile()
//        getGroups()
    }

    fun getGroups() {
        baseViewModelScope.launch {
            mainRepository.getGroup(groupId.value).onSuccess {
                alarmRoomTitleEvent.value = it.title
                alarmRoomDescriptionEvent.value = it.description
                isPublicAccess.value = it.public_access
                membersEvent.value = it.members.size.toString()
                isHost.value = it.ihost
                roomImgUri.value = it.background_image_path
                Log.d("ttt bg img path",it.background_image_path )
                for (i in 0..it.members.size) {
                    if (userId.value == it.members[i].user_id) {
                        participation.value = true
                    }
                }
                Log.d("ttt", "그룹 확인 성공")


            }.onError {
                Log.d("ttt", "그룹 확인 실패")
            }
        }
    }

    fun getProfile() {
        baseViewModelScope.launch {
            mainRepository.getUserProfile().onSuccess {
                userId.value = it.id


                Log.d("ttt", "사용자 정보 가져오기 성공")

            }.onError {
                Log.d("ttt", "사용자 정보 가져오기 실패")
            }
        }
    }

    fun onSettingClicked() {
        if (isHost.value) onSettingRoomClicked(groupId.value)
        else onSettingRoomForUserClicked(groupId.value)
    }

    fun getPushAlarm() {
        pushAlarmList = createAlarmRoomHistoryMessagePager(
            mainRepository = mainRepository,
            groupId = groupId,
            sort = sort,
            viewModel = this
        ).flow.cachedIn(baseViewModelScope)
    }

    fun onSettingRoomClicked(alarmId: Int) {
        baseViewModelScope.launch {
            _navigationEvent.emit(
                AlarmRoomHistoryNavigationAction.NavigateToSettingRoom(alarmId)
            )
        }
    }

    fun onSettingRoomForUserClicked(alarmId: Int) {
        baseViewModelScope.launch {
            _navigationEvent.emit(
                AlarmRoomHistoryNavigationAction.NavigateToSettingRoomForUser(alarmId)
            )
        }
    }

    fun postGroupAdmissions() {
        baseViewModelScope.launch {
            mainRepository.postGroupAdmissions(groupId.value).onSuccess {
                Log.d("ttt", "입장하기 성공")

            }.onError {
                Log.d("ttt", "입장하기 실패")
            }
        }

    }

    // 방장 권한이 있어야 함
    fun getGroupAdmissions() {
        baseViewModelScope.launch {
            mainRepository.getGroupAdmissions(groupId.value).onSuccess {
                _alarmInviteRoomEvent.value = it.admissions
                alarmInviteRoomSizeEvent.value = "입장 요청 ${it.admissions.size}"
                Log.d("ttt", "초대 확인 성공")

            }.onError {
                Log.d("ttt", "초대 확인 실패")
            }
        }
    }

    override fun postGroupAdmissionsAllow(admissionId: Int) {
        baseViewModelScope.launch {
            mainRepository.postGroupAdmissionsAllow(groupId.value, admissionId).onSuccess {
            }.onError {
                Log.d("ttt", "초대 승인 실패")
            }
        }
    }

    override fun onGroupAdmissionsRefuse(admissionId: Int) {
        baseViewModelScope.launch {
            mainRepository.postGroupAdmissionsRefuse(groupId.value, admissionId).onSuccess {

            }.onError {
                Log.d("ttt", "초대 거절 실패")
            }
        }
    }

    fun postReaction(reaction_id: Int, notification_id: Int) {
        baseViewModelScope.launch {
            showLoading()
            mainRepository.postReactions(
                notification_id = notification_id,
                reaction_id = reaction_id
            )
                .onSuccess { _navigationEvent.emit(AlarmRoomHistoryNavigationAction.NavigateToBookmarkFilterReset) }
                .onError {
                    Log.d("ttt", "$it 리액션 등록 실패")
                }
            dismissLoading()
        }
    }

    fun deleteReaction(notification_reaction_id: Int) {
        baseViewModelScope.launch {
            showLoading()
            mainRepository.deleteReaction(
                notification_reaction_id = notification_reaction_id
            )
                .onSuccess { _navigationEvent.emit(AlarmRoomHistoryNavigationAction.NavigateToBookmarkFilterReset) }
                .onError {
                    Log.d("ttt", "$it 리액션 삭제 실패")
                }
            dismissLoading()
        }
    }

    fun patchReaction(notification_reaction_id: Int, reaction_id: Int, notification_id: Int) {
        baseViewModelScope.launch {
            showLoading()
            mainRepository.patchReaction(
                notification_reaction_id = notification_reaction_id,
                notification_id = notification_id,
                reaction_id = reaction_id
            )
                .onSuccess { _navigationEvent.emit(AlarmRoomHistoryNavigationAction.NavigateToBookmarkFilterReset) }
                .onError {
                    Log.d("ttt", "$it 리액션 변경 실패")
                }
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

    override fun onReactionClicked(notification_id: Int, reaction_id: Int, notification_reaction_id: Int) {
        baseViewModelScope.launch {
            _navigationEvent.emit(
                AlarmRoomHistoryNavigationAction.NavigateToReaction(
                    notification_id = notification_id,
                    reaction_id = reaction_id,
                    notification_reaction_id = notification_reaction_id

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
        reservation: Int
    ) {
        baseViewModelScope.launch {
            _navigationEvent.emit(
                AlarmRoomHistoryNavigationAction.NavigateToAlarmCreate(
                    groupId.value,
                    alarmRoomTitleEvent.value,
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

    override fun onRecentAlarmMoreClicked(sendUserId: Int, alarmId: Int, title: String, message: String) {
        baseViewModelScope.launch {
            _navigationEvent.emit(
                AlarmRoomHistoryNavigationAction.NavigateToAlarmMore(
                    sendUserId,
                    alarmId,
                    title,
                    message
                )
            )
        }
    }

    fun onReportClicked(alarmId: Int, reportReason: String) {
        baseViewModelScope.launch {
            mainRepository.postReportNotification(alarmId, editTextReportEvent.value, reportReason)
                .onSuccess {

                    Log.d("ttt", "알림 신고 성공")


                }.onError {
                    Log.d("ttt", "$it 알림 신고 실패")
                }
        }
    }

    fun onUserReportClicked(sendUserId: Int) {
        baseViewModelScope.launch {
            mainRepository.deleteRelations(sendUserId)
                .onSuccess {

                    Log.d("ttt", "친구 삭제 성공")


                }.onError {
                    Log.d("ttt", "$it 친구 삭제 실패")
                }
        }
    }
}