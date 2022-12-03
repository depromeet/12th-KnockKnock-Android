package com.depromeet.knockknock.ui.information

import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.mypage.MypageNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor(
) : BaseViewModel(), InformationActionHandler {

    private val TAG = "AlarmRoomViewModel"

    private val _navigationHandler: MutableSharedFlow<InformationNavigationAction> = MutableSharedFlow<InformationNavigationAction>()
    val navigationHandler: SharedFlow<InformationNavigationAction> = _navigationHandler.asSharedFlow()


    override fun onUserConsentsClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(InformationNavigationAction.NavigateToUserConsents)
        }
    }

    override fun onUserPrivacyClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(InformationNavigationAction.NavigateToUserPrivacy)
        }
    }

    override fun onAppMakersClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(InformationNavigationAction.NavigateToAppMakers)
        }
    }

}