package com.depromeet.knockknock.ui.setprofile

import androidx.lifecycle.viewModelScope
import com.depromeet.data.DataApplication
import com.depromeet.domain.model.ImageUrl
import com.depromeet.domain.model.Profile
import com.depromeet.domain.model.UserProfile
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.di.PresentationApplication.Companion.editor
import com.depromeet.knockknock.di.PresentationApplication.Companion.sSharedPreferences
import com.depromeet.knockknock.ui.editprofile.EditProfileNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.depromeet.knockknock.ui.setprofile.SetProfileNavigationAction
import kotlinx.coroutines.flow.*

@HiltViewModel
class SetProfileViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), SetProfileActionHandler {

    private val TAG = "SetProfileViewModel"

    private val _navigationHandler: MutableSharedFlow<SetProfileNavigationAction> = MutableSharedFlow<SetProfileNavigationAction>()
    val navigationHandler: SharedFlow<SetProfileNavigationAction> = _navigationHandler.asSharedFlow()

    var inputContent = MutableStateFlow<String>("")
    var editTextMessageCountEvent = MutableStateFlow<Int>(0)

    val setBtnState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val setPossibleState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)

    val profileImg: MutableStateFlow<Profile?> = MutableStateFlow(null)

    init {
        baseViewModelScope.launch {
            mainRepository.getProfilesRandom()
                .onSuccess { profile ->
                    profileImg.emit(profile)
                }
        }

        viewModelScope.launch {
            inputContent.debounce(0).collectLatest {
                onEditTextCount(it.length)
            }
        }
    }

    private fun onEditTextCount(count: Int) {
        viewModelScope.launch {
            editTextMessageCountEvent.value = count
        }
    }


    override fun onProfileImageSetClicked() {
        baseViewModelScope.launch {
            profileImg.value?.let {
                _navigationHandler.emit(SetProfileNavigationAction.NavigateToSetProfileImage(profile = it))
            }
        }
    }

    override fun onSelectionDoneClicked() {
        baseViewModelScope.launch {
            val idToken = sSharedPreferences.getString("id_token", null)
            val provider = sSharedPreferences.getString("provider", null)
            if(inputContent.value == "") {
                _navigationHandler.emit(SetProfileNavigationAction.NavigateToEmpty)
            } else {
                if(idToken != null && provider != null) {
                    mainRepository.postRegister(
                        idToken = idToken,
                        provider = provider,
                        profile_path = profileImg.value!!.url,
                        nickname = inputContent.value
                    ).onSuccess {
                        editor.putString("access_token", it.access_token)
                        editor.putString("refresh_token", it.refresh_token)
                        editor.commit()
                        val deviceId = sSharedPreferences.getString("device_id", null)
                        val fcmToken = sSharedPreferences.getString("fcm_token", null)
                        mainRepository.postNotificationToken(device_id = deviceId!!, token = fcmToken!!)
                            .onSuccess {
                                _navigationHandler.emit(SetProfileNavigationAction.NavigateToHome)
                            }
                    }
                }
            }
        }
    }





}