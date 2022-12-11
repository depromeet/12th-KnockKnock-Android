package com.depromeet.knockknock.ui.alarmcreate

import com.depromeet.knockknock.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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

    init {
        baseViewModelScope.launch {
            editTextMessageEvent.debounce(0).collectLatest {
                onEditTextCount(it.length)
            }
        }

        baseViewModelScope.launch {
            editTextTitleEvent.emit("주호민")
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

    override fun onAddImageClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(AlarmCreateNavigationAction.NavigateToAddImage)
        }
    }

    override fun onAlarmPushClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(AlarmCreateNavigationAction.NavigateToAlarmSend)
        }
    }

    fun onImageStateChecked() {
        baseViewModelScope.launch {
            messageImgState.emit(true)
        }
    }
}