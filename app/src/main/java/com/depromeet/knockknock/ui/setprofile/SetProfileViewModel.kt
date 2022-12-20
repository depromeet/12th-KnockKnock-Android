package com.depromeet.knockknock.ui.setprofile

import androidx.lifecycle.viewModelScope
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.editprofile.EditProfileNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.depromeet.knockknock.ui.setprofile.SetProfileNavigationAction
import kotlinx.coroutines.flow.*

@HiltViewModel
class SetProfileViewModel @Inject constructor(
) : BaseViewModel(), SetProfileActionHandler {

    private val TAG = "SetProfileViewModel"

    private val _navigationHandler: MutableSharedFlow<SetProfileNavigationAction> = MutableSharedFlow<SetProfileNavigationAction>()
    val navigationHandler: SharedFlow<SetProfileNavigationAction> = _navigationHandler.asSharedFlow()


    var inputContent = MutableStateFlow<String>("")
    var editTextMessageCountEvent = MutableStateFlow<Int>(0)

    val setBtnState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val setPossibleState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)


    init {
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
            _navigationHandler.emit(SetProfileNavigationAction.NavigateToSetProfileImage)
        }
    }

    override fun onSelectionDoneClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(SetProfileNavigationAction.NavigateToHome)
        }
    }





}