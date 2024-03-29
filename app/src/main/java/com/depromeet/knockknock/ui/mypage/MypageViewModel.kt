package com.depromeet.knockknock.ui.mypage

import com.depromeet.domain.model.UserProfile
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
//import com.dida.android.presentation.views.nav.home.HomeNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), MypageActionHandler {

    private val TAG = "MypageViewModel"

    private val _navigationHandler: MutableSharedFlow<MypageNavigationAction> = MutableSharedFlow<MypageNavigationAction>()
    val navigationHandler: SharedFlow<MypageNavigationAction> = _navigationHandler.asSharedFlow()

    val userProfile: MutableStateFlow<UserProfile?> = MutableStateFlow(null)

    init {
        baseViewModelScope.launch {
            showLoading()
            mainRepository.getUserProfile()
                .onSuccess { profile ->
                    userProfile.emit(profile) }
            dismissLoading()
        }
    }

    fun getProfile() {
        baseViewModelScope.launch {
            showLoading()
            mainRepository.getUserProfile()
                .onSuccess { profile ->
                    userProfile.emit(profile) }
            dismissLoading()
        }
    }


    override fun onProfileEditClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(MypageNavigationAction.NavigateToProfileEdit)
        }
    }

    override fun onAlarmSettingClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(MypageNavigationAction.NavigateToAlarmSetting)
        }
    }

    override fun onBookmarkClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(MypageNavigationAction.NavigateToBookmark)
        }
    }

    override fun onFriendListClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(MypageNavigationAction.NavigateToFriendList)
        }
    }

    override fun onInformationClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(MypageNavigationAction.NavigateToInformation)
        }
    }


}