package com.depromeet.knockknock.ui.editprofile

import com.depromeet.domain.model.UserProfile
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.di.PresentationApplication
import com.depromeet.knockknock.di.PresentationApplication.Companion.editor
import com.depromeet.knockknock.di.PresentationApplication.Companion.sSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val mainRepository: MainRepository,
) : BaseViewModel(), EditProfileActionHandler {

    private val TAG = "AlarmRoomViewModel"

    private val _navigationHandler: MutableSharedFlow<EditProfileNavigationAction> = MutableSharedFlow<EditProfileNavigationAction>()
    val navigationHandler: SharedFlow<EditProfileNavigationAction> = _navigationHandler.asSharedFlow()

    val userProfile: MutableStateFlow<UserProfile?> = MutableStateFlow(null)

    init {
        baseViewModelScope.launch {
            showLoading()
            mainRepository.getUserProfile()
                .onSuccess {
                    userProfile.emit(it)
                }
            dismissLoading()
        }
    }

    fun getProfile() {
        baseViewModelScope.launch {
            mainRepository.getUserProfile()
                .onSuccess {
                    userProfile.emit(it)
                }
        }
    }


    override fun onLogoutClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(EditProfileNavigationAction.NavigateToLogout)
        }
    }

    override fun onUserDeleteClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(EditProfileNavigationAction.NavigateToUserDelete)
        }
    }

    override fun onProfileEditClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(EditProfileNavigationAction.NavigateToEditProfile)
        }
    }

    fun onUserLogOut() {
        baseViewModelScope.launch {
            showLoading()
            mainRepository.postLogout()
                .onSuccess {
                    editor.remove("access_token")
                    editor.remove("refresh_token")
                    editor.commit()
                    _navigationHandler.emit(EditProfileNavigationAction.NavigateToSplash)
                }
            dismissLoading()
        }
    }

    fun onUserDelete() {
        baseViewModelScope.launch {
            showLoading()
            mainRepository.postLogout()
                .onSuccess {
                    editor.remove("access_token")
                    editor.remove("refresh_token")
                    editor.commit()
                    _navigationHandler.emit(EditProfileNavigationAction.NavigateToSplash)
                }
            dismissLoading()
        }
    }
}