package com.depromeet.knockknock.ui.preview

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.alarmcreate.AlarmCreateNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class PreviewViewModel @Inject constructor() : BaseViewModel(), PreviewActionHandler {

    private val TAG = "PreviewViewModel"

    private val _navigationEvent: MutableSharedFlow<PreviewNavigationAction> =
        MutableSharedFlow<PreviewNavigationAction>()
    val navigationEvent: SharedFlow<PreviewNavigationAction> = _navigationEvent.asSharedFlow()
    var previewTitleEvent = MutableStateFlow<String>("")
    var previewMessageEvent = MutableStateFlow<String>("")
    var messageImgUri: MutableStateFlow<String> = MutableStateFlow<String>("")

    override fun onCloseClicked() {
        baseViewModelScope.launch {
            _navigationEvent.emit(PreviewNavigationAction.NavigateToBackStack)
        }
    }

    fun onImageUriChecked(uri: String) {
        baseViewModelScope.launch {
            messageImgUri.emit(uri)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun previewTimeEvent(): String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("a hh:mm"))
}