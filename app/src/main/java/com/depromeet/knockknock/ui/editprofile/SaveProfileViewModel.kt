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
class SaveProfileViewModel @Inject constructor(
    private val mainRepository: MainRepository,
) : BaseViewModel(), SaveProfileActionHandler {

    private val TAG = "SaveProfileViewModel"

    private val _navigationHandler: MutableSharedFlow<SaveProfileNavigationAction> = MutableSharedFlow<SaveProfileNavigationAction>()
    val navigationHandler: SharedFlow<SaveProfileNavigationAction> = _navigationHandler.asSharedFlow()

    val editPossibleState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)

    val isGalleryImage: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)

    val beforeProfile: MutableStateFlow<UserProfile?> = MutableStateFlow(null)
    val profileImg: MutableStateFlow<String> = MutableStateFlow("")
    val profileName: MutableStateFlow<String> = MutableStateFlow("")

    init {
        baseViewModelScope.launch {
            mainRepository.getUserProfile()
                .onSuccess {
                    beforeProfile.emit(it)
                    profileImg.emit(it.profile_path)
                    profileName.emit(it.nickname)
                }
        }
    }

    override fun onProfileImageClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(SaveProfileNavigationAction.NavigateToEditProfileImg)
        }
    }

    override fun onProfileEditClicked() {
        TODO("Not yet implemented")
    }

    override fun onProfileSaveClicked() {
        baseViewModelScope.launch {
            if(beforeProfile.value!!.profile_path != profileImg.value) {
                mainRepository.putUserProfile(nickname = profileName.value, profile_path = profileImg.value)
                    .onSuccess {
                        _navigationHandler.emit(SaveProfileNavigationAction.NavigateToSuccess)
                        return@launch
                    }
            }

            if(beforeProfile.value!!.nickname != profileName.value) {
                mainRepository.putUserNickname(nickname = profileName.value)
                    .onSuccess {
                        _navigationHandler.emit(SaveProfileNavigationAction.NavigateToSuccess)
                        return@launch
                    }
            }
        }
    }
}