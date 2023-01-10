package com.depromeet.knockknock.ui.makers

import com.depromeet.knockknock.ui.mypage.MypageNavigationAction
import com.depromeet.domain.model.UserProfile
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MakersViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), MakersActionHandler {

    private val TAG = "MakersViewModel"

    private val _navigationHandler: MutableSharedFlow<MakersNavigationAction> =
        MutableSharedFlow<MakersNavigationAction>()
    val navigationHandler: SharedFlow<MakersNavigationAction> = _navigationHandler.asSharedFlow()

    val userProfile: MutableStateFlow<UserProfile?> = MutableStateFlow(null)

    init {
        baseViewModelScope.launch {
            showLoading()
            mainRepository.getUserProfile()
                .onSuccess { profile ->
                    userProfile.emit(profile)
                }
            dismissLoading()
        }
    }








}