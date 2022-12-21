package com.depromeet.knockknock.ui.register

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import com.depromeet.domain.onError
import com.depromeet.domain.onSuccess
import com.depromeet.domain.usecase.PostOauthLoginUsecase
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.bookmark.model.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.*

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val oauthLoginUsecase: PostOauthLoginUsecase
) : BaseViewModel(), RegisterActionHandler {

    private val TAG = "RegisterViewModel"

    private val _navigationHandler: MutableSharedFlow<RegisterNavigationAction> = MutableSharedFlow<RegisterNavigationAction>()
    val navigationHandler: SharedFlow<RegisterNavigationAction> = _navigationHandler.asSharedFlow()

    val messageText: MutableStateFlow<String> = MutableStateFlow("")
    val notificationAgreed: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)

    fun setNotification(enable: Boolean) {
        baseViewModelScope.launch {
            notificationAgreed.value = enable
        }
    }

    fun deleteMessage() {
        baseViewModelScope.launch {
            messageText.value = ""
        }
    }

    fun oauthLogin(idToken: String, provider: String) {
        baseViewModelScope.launch {
            Log.d("response!!!!", idToken)
            oauthLoginUsecase.invoke(idToken = idToken, provider = provider)
                .onSuccess {
                    _navigationHandler.emit(RegisterNavigationAction.NavigateToLoginSuccess) }
        }
    }

    override fun onSendTestPushAlarmClicked() {
        baseViewModelScope.launch {
            if (!notificationAgreed.value)
                _navigationHandler.emit(RegisterNavigationAction.NavigateToPushSetting)
            else
                _navigationHandler.emit(RegisterNavigationAction.NavigateToNotificationAlarm)
        }
    }

    override fun onKakaoLoginClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(RegisterNavigationAction.NavigateToKakaoLogin)
        }
    }

    override fun onGoogleLoginClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(RegisterNavigationAction.NavigateToGoogleLogin)
        }
    }
}