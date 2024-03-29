package com.depromeet.knockknock.ui.register

import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.di.PresentationApplication.Companion.editor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.*

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), RegisterActionHandler {

    private val TAG = "RegisterViewModel"

    private val _navigationHandler: MutableSharedFlow<RegisterNavigationAction> = MutableSharedFlow<RegisterNavigationAction>()
    val navigationHandler: SharedFlow<RegisterNavigationAction> = _navigationHandler.asSharedFlow()

    val messageText: MutableStateFlow<String> = MutableStateFlow("")
    val notificationAgreed: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)

    val sendEnable: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val firebaseToken: MutableStateFlow<String> = MutableStateFlow("")
    val deviceId: MutableStateFlow<String> = MutableStateFlow("")

    fun sendNotification() {
        baseViewModelScope.launch {
            showLoading()
            notificationAgreed.value = true
            mainRepository.postNotificationExperience(token = firebaseToken.value, content = messageText.value)
            dismissLoading()
        }
    }

    fun deleteMessage() {
        baseViewModelScope.launch {
            messageText.value = ""
        }
    }

    fun oauthLogin(idToken: String, provider: String) {
        baseViewModelScope.launch {
            showLoading()
            mainRepository.getTokenValidation(idToken = idToken, provider = provider)
                .onSuccess {
                    editor.putString("id_token", idToken)
                    editor.putString("provider", provider)
                    editor.putString("fcm_token", firebaseToken.value)
                    editor.putString("device_id", deviceId.value)

                    if(!it.is_registered) {
                        editor.commit()
                        _navigationHandler.emit(RegisterNavigationAction.NavigateToLoginFrist)
                    } else {
                        mainRepository.postLogin(idToken = idToken, provider = provider)
                            .onSuccess { response ->
                                editor.putString("access_token", response.access_token)
                                editor.putString("refresh_token", response.refresh_token)
                                editor.commit()

                                mainRepository.postNotificationToken(token = firebaseToken.value, device_id = deviceId.value)
                                    .onSuccess {
                                        _navigationHandler.emit(RegisterNavigationAction.NavigateToLoginAlready)
                                    }
                            }
                    }
                }
            dismissLoading()
        }
    }

    override fun onSendTestPushAlarmClicked() {
        baseViewModelScope.launch {
            if(!sendEnable.value) {
                return@launch
            }

            if (!notificationAgreed.value) {
                _navigationHandler.emit(RegisterNavigationAction.NavigateToPushSetting)
            } else {
                _navigationHandler.emit(RegisterNavigationAction.NavigateToNotificationAlarm)
            }
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