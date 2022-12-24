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

    val firebaseToken: MutableStateFlow<String> = MutableStateFlow("")
    val deviceId: MutableStateFlow<String> = MutableStateFlow("")

    fun setNotification(enable: Boolean) {
        baseViewModelScope.launch {
            mainRepository.postNotificationToken(token = firebaseToken.value, device_id = deviceId.value)
                .onSuccess {
                    notificationAgreed.value = enable
                }
        }
    }

    fun deleteMessage() {
        baseViewModelScope.launch {
            messageText.value = ""
        }
    }

    fun oauthLogin(idToken: String, provider: String) {
        baseViewModelScope.launch {
            mainRepository.getTokenValidation(idToken = idToken, provider = provider)
                .onSuccess {
                    if(!it.is_registered) {
                        editor.putString("id_token", idToken)
                        editor.putString("provider", provider)
                        editor.commit()
                        _navigationHandler.emit(RegisterNavigationAction.NavigateToLoginFrist)
                    } else {
                        mainRepository.postLogin(idToken = idToken, provider = provider)
                            .onSuccess { response ->
                                editor.putString("id_token", idToken)
                                editor.putString("provider", provider)
                                editor.putString("access_token", response.access_token)
                                editor.putString("refresh_token", response.refresh_token)
                                editor.commit()
                                _navigationHandler.emit(RegisterNavigationAction.NavigateToLoginAlready)
                            }
                    }
                }
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