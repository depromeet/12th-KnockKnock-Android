package com.depromeet.knockknock.ui.alarmcreate

import android.util.Log
import com.depromeet.domain.model.RecommendMessageList
import com.depromeet.domain.onError
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmCreateViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), AlarmCreateActionHandler {

    private val TAG = "AlarmCreateViewModel"

    private val _navigationEvent: MutableSharedFlow<AlarmCreateNavigationAction> =
        MutableSharedFlow<AlarmCreateNavigationAction>()
    val navigationEvent: SharedFlow<AlarmCreateNavigationAction> = _navigationEvent.asSharedFlow()
    var editTextTitleEvent = MutableStateFlow<String>("")
    var editTextMessageEvent = MutableStateFlow<String>("")
    var editTextMessageCountEvent = MutableStateFlow<Int>(0)
    val messageImgUri: MutableStateFlow<String> = MutableStateFlow<String>("")
    private val _recommendationMessageEvent: MutableStateFlow<RecommendMessageList> =
        MutableStateFlow(RecommendMessageList(emptyList()))
    val recommendationMessageEvent: StateFlow<RecommendMessageList> = _recommendationMessageEvent

    init {
        baseViewModelScope.launch {
            editTextMessageEvent.debounce(0).collectLatest {
                onEditTextCount(it.codePointCount(0, it.length))
            }
        }

        baseViewModelScope.launch {
            editTextTitleEvent.emit("주호민")
        }
        getRecommendMessage()
    }

    private fun getRecommendMessage() {
        baseViewModelScope.launch {
            mainRepository.getRecommendMessage().onSuccess {
                Log.d("ttt success", it.toString())
                _recommendationMessageEvent.value = it
            }.onError {
                Log.d("ttt error", it.toString())
            }
        }
    }

    private fun onEditTextCount(count: Int) {
        baseViewModelScope.launch {
            editTextMessageCountEvent.value = count
        }
    }

    override fun onDeleteEditTextMessageClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(AlarmCreateNavigationAction.NavigateToDeleteMessageText)
        }
    }

    override fun onFocusEditTextTitleClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(AlarmCreateNavigationAction.NavigateToFocusTitleText)
        }
    }

    override fun onRecommendationMessageClicked(message: String) {
        baseViewModelScope.launch {
            _navigationEvent.emit(
                AlarmCreateNavigationAction.NavigateToRecommendationMessageText(
                    message
                )
            )
        }
    }

    override fun onAddImageClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(AlarmCreateNavigationAction.NavigateToAddImage)
        }
    }

    override fun onAlarmPushClicked() {
        baseViewModelScope.launch {
            if (editTextTitleEvent.value == "" && editTextMessageEvent.value == "" && messageImgUri.value == "") {
                mainRepository.postNotifications(
                    group_id = 0,
                    title = editTextTitleEvent.value,
                    content = editTextMessageEvent.value,
                    image_url = messageImgUri.value
                ).onSuccess {
                    _navigationEvent.emit(AlarmCreateNavigationAction.NavigateToPushAlarm)
                }.onError {}
            }
        }
    }

    override fun onReservationAlarmPushClicked(sendAt: String) {
        if (editTextTitleEvent.value == "" && editTextMessageEvent.value == "" && messageImgUri.value == "") {
            baseViewModelScope.launch {
                mainRepository.postNotificationReservation(
                    group_id = 0,
                    title = editTextTitleEvent.value,
                    content = editTextMessageEvent.value,
                    image_url = messageImgUri.value,
                    send_at = sendAt,
                ).onSuccess {
                    _navigationEvent.emit(AlarmCreateNavigationAction.NavigateToPushAlarm)
                }.onError {}
            }
        }
    }

    fun onImageUriChecked(uri: String) {
        baseViewModelScope.launch {
            messageImgUri.emit(uri)
        }
    }

    override fun onPreviewClicked(title: String, message: String, uri: String) {
        baseViewModelScope.launch {
            _navigationEvent.emit(
                AlarmCreateNavigationAction.NavigateToPreview(title, message, uri)
            )
        }
    }

    override fun onAlarmSendClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(AlarmCreateNavigationAction.NavigateToAlarmSend)
        }
    }
}