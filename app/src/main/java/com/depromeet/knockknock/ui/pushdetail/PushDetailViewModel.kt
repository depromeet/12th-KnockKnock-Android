package com.depromeet.knockknock.ui.pushdetail

import com.depromeet.domain.model.UserProfile
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PushDetailViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), PushDetailActionHandler {

    private val TAG = "PushDetailViewModel"

    private val _navigationHandler: MutableSharedFlow<PushDetailNavigationAction> =
        MutableSharedFlow<PushDetailNavigationAction>()
    val navigationHandler: SharedFlow<PushDetailNavigationAction> =
        _navigationHandler.asSharedFlow()

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