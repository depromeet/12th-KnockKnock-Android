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

    var groupId = MutableStateFlow<Int>(0)
    var title = MutableStateFlow<String>("")
    var username = MutableStateFlow<String>("")
    var dateTime = MutableStateFlow<String>("")
    var contents = MutableStateFlow<String>("")
    var img_url = MutableStateFlow<String>("https://user-images.githubusercontent.com/13329304/207665701-5e940985-f612-46d0-a376-1e151368d160.png")

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