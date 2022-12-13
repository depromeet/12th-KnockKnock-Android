package com.depromeet.knockknock.ui.setprofile

import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.editprofile.EditProfileNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.depromeet.knockknock.ui.setprofile.SetProfileNavigationAction

@HiltViewModel
class SetProfileViewModel @Inject constructor(
) : BaseViewModel(), SetProfileActionHandler {

    private val TAG = "SetProfileViewModel"

    private val _navigationHandler: MutableSharedFlow<SetProfileNavigationAction> = MutableSharedFlow<SetProfileNavigationAction>()
    val navigationHandler: SharedFlow<SetProfileNavigationAction> = _navigationHandler.asSharedFlow()

    val setBtnState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val setPossibleState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)


    override fun onProfileImageSetClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(SetProfileNavigationAction.NavigateToSetProfileImage)
        }
    }

    override fun onSelectionDoneClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(SetProfileNavigationAction.NavigateToHome)
        }
    }





}