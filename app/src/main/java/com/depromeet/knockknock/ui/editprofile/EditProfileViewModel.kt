package com.depromeet.knockknock.ui.editprofile

import com.depromeet.knockknock.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
) : BaseViewModel(), EditProfileActionHandler {

    private val TAG = "AlarmRoomViewModel"

    private val _navigationHandler: MutableSharedFlow<EditProfileNavigationAction> = MutableSharedFlow<EditProfileNavigationAction>()
    val navigationHandler: SharedFlow<EditProfileNavigationAction> = _navigationHandler.asSharedFlow()

    val editBtnState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val editPossibleState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)

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
            _navigationHandler.emit(EditProfileNavigationAction.NavigateToGallery)
        }
    }

    fun onUserDelete() {
        baseViewModelScope.launch {
            // API Call
            _navigationHandler.emit(EditProfileNavigationAction.NavigateToSplash)
        }
    }

}