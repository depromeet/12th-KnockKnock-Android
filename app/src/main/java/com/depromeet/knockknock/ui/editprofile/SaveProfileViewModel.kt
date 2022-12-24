package com.depromeet.knockknock.ui.editprofile

import android.net.Uri
import android.util.Log
import com.depromeet.domain.model.UserProfile
import com.depromeet.domain.onError
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
import okhttp3.MultipartBody
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

    var beforeProfile: UserProfile? = null
    val profileImg: MutableStateFlow<String> = MutableStateFlow("")
    val profileName: MutableStateFlow<String> = MutableStateFlow("")

    init {
        baseViewModelScope.launch {
            mainRepository.getUserProfile()
                .onSuccess {
                    beforeProfile = it
                    profileImg.emit(it.profile_path)
                    profileName.emit(it.nickname)
                }
        }
    }

    fun setFileToUri(file: MultipartBody.Part) {
        baseViewModelScope.launch {
            mainRepository.postFileToUrl(file = file)
                .onSuccess {
                    profileImg.value = it.image_url
                }
        }
    }

    override fun onProfileImageClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(SaveProfileNavigationAction.NavigateToEditProfileImg)
        }
    }

    override fun onProfileSaveClicked() {
        baseViewModelScope.launch {
            if(beforeProfile!!.profile_path != profileImg.value) {
                mainRepository.putUserProfile(nickname = profileName.value, profile_path = profileImg.value)
                    .onSuccess {
                        _navigationHandler.emit(SaveProfileNavigationAction.NavigateToSuccess)
                        return@launch
                    }
            }

            if(beforeProfile!!.nickname != profileName.value) {
                mainRepository.putUserNickname(nickname = profileName.value)
                    .onSuccess {
                        _navigationHandler.emit(SaveProfileNavigationAction.NavigateToSuccess)
                        return@launch
                    }
            }
        }
    }
}