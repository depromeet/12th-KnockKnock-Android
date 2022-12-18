package com.depromeet.knockknock.ui.alarmcreate

import android.os.Build
import androidx.annotation.RequiresApi
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.alarmcreate.model.RecommendationMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AlarmCreateViewModel @Inject constructor(
) : BaseViewModel(), AlarmCreateActionHandler {

    private val TAG = "AlarmCreateViewModel"

    private val _navigationEvent: MutableSharedFlow<AlarmCreateNavigationAction> =
        MutableSharedFlow<AlarmCreateNavigationAction>()
    val navigationEvent: SharedFlow<AlarmCreateNavigationAction> = _navigationEvent.asSharedFlow()
    var editTextTitleEvent = MutableStateFlow<String>("")
    var editTextMessageEvent = MutableStateFlow<String>("")
    var editTextMessageCountEvent = MutableStateFlow<Int>(0)
    val messageImgState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    private val _recommendationMessageEvent: MutableStateFlow<List<RecommendationMessage>> = MutableStateFlow(emptyList())
    val recommendationMessageEvent: StateFlow<List<RecommendationMessage>> = _recommendationMessageEvent

    init {
        baseViewModelScope.launch {
            editTextMessageEvent.debounce(0).collectLatest {
                onEditTextCount(it.codePointCount(0, it.length))
            }
        }

        baseViewModelScope.launch {
            editTextTitleEvent.emit("주호민")
        }

        getTempList()
    }

    private fun getTempList() {
        val test1 = RecommendationMessage("\uD83D\uDCAA\uD83D\uDCAA\uD83D\uDCAA")
        val test2 = RecommendationMessage("탈락?오히려좋아")
        val test3 = RecommendationMessage("꿈은 없고요 그냥 놀고 싶습니다.")
        val test4 = RecommendationMessage("서류 접수 하셨나요")
        val test5 = RecommendationMessage(String(Character.toChars(0x1F971)))
        val testList = listOf(test1, test2, test3, test4, test5)

        baseViewModelScope.launch {
            _recommendationMessageEvent.value = testList
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
            _navigationEvent.emit(AlarmCreateNavigationAction.NavigateToRecommendationMessageText(message))
        }
    }

    override fun onAddImageClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(AlarmCreateNavigationAction.NavigateToAddImage)
        }
    }

    override fun onAlarmPushClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(AlarmCreateNavigationAction.NavigateToPushAlarm)
        }
    }

    fun onImageStateChecked() {
        baseViewModelScope.launch {
            messageImgState.emit(true)
        }
    }

    override fun onPreviewClicked(title: String, message: String) {
        baseViewModelScope.launch {
            _navigationEvent.emit(
                AlarmCreateNavigationAction.NavigateToPreview(title, message)
            )
        }
    }

    override fun onAlarmSendClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(AlarmCreateNavigationAction.NavigateToAlarmSend)
        }
    }
}