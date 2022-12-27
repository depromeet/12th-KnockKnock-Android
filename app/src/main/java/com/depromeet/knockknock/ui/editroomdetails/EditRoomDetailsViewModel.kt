package com.depromeet.knockknock.ui.editroomdetails

import androidx.lifecycle.viewModelScope
import com.depromeet.knockknock.ui.friendlist.FriendListActionHandler
import com.depromeet.knockknock.ui.friendlist.FriendListNavigationAction


import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.bookmark.BookmarkNavigationAction
import com.depromeet.knockknock.ui.editprofile.EditProfileNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditRoomDetailsViewModel @Inject constructor(
) : BaseViewModel(), EditRoomDetailsActionHandler {

    private val TAG = "EditRoomDetailsViewModel"

    private val _navigationHandler: MutableSharedFlow<EditRoomDetailsNavigationAction> =
        MutableSharedFlow<EditRoomDetailsNavigationAction>()
    val navigationHandler: SharedFlow<EditRoomDetailsNavigationAction> =
        _navigationHandler.asSharedFlow()

    //이미지 id를 사용할 경우 주석해제
//    private val _backgroundImgId : MutableSharedFlow<Int> = MutableSharedFlow<Int>()
//    val backgroundImgId: SharedFlow<Int> = _backgroundImgId.asSharedFlow()
//
//    private val _thumbnailImgId : MutableSharedFlow<Int> = MutableSharedFlow<Int>()
//    val thumbnailImgId: SharedFlow<Int> = _thumbnailImgId.asSharedFlow()

    private val _onSaveSuccess: MutableSharedFlow<Boolean> = MutableSharedFlow<Boolean>()
    val onSaveSuccess: SharedFlow<Boolean> = _onSaveSuccess.asSharedFlow()

    var _backgroundStored: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val backgroundStored: StateFlow<Boolean> = _backgroundStored.asStateFlow()

    var _thumbnailStored: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val thumbnailStored: StateFlow<Boolean> = _thumbnailStored.asStateFlow()

    private val _roomNameWritten: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val roomNameWritten: StateFlow<Boolean> = _roomNameWritten.asStateFlow()

    private val _roomDesWritten: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val roomDesWritten: StateFlow<Boolean> = _roomDesWritten.asStateFlow()

    private val _saveBtnEnable: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val saveBtnEnable: StateFlow<Boolean> = _saveBtnEnable.asStateFlow()

    var inputRoomName = MutableStateFlow<String>("")
    var inputRoomNameCountEvent = MutableStateFlow<Int>(0)
    var inputRoomDescription = MutableStateFlow<String>("")
    var inputRoomDescriptionCountEvent = MutableStateFlow<Int>(0)


    private val _isRoomUnpublic: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val isRoomUnpublic: StateFlow<Boolean> = _isRoomUnpublic

    val isGalleryImage: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)

    override fun onRoomUnpublicToggled(checked: Boolean) {
        baseViewModelScope.launch {
            _isRoomUnpublic.emit(!_isRoomUnpublic.value)
        }
    }

    override fun onBackgroundClicked(backgroundId: Int) {
        _backgroundStored.value = true

        //배경화면 imgid 사용할 경우 주석해제
//        baseViewModelScope.launch {
//            _backgroundImgId.emit(backgroundId)
//            _navigationHandler.emit(EditRoomDetailsNavigationAction.NavigateToStoreBackground)
//        }

    }

    override fun onThumbnailClicked(thumbnailId: Int) {
        _thumbnailStored.value = true
//        baseViewModelScope.launch {
//            _thumbnailImgId.emit(thumbnailId)
//            _navigationHandler.emit(EditRoomDetailsNavigationAction.NavigateToStoreThumbnail)
//        }

    }

    override fun onBackgroundEditClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(EditRoomDetailsNavigationAction.NavigateToEditBackground)
        }
    }

    override fun onThumbnailEditClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(EditRoomDetailsNavigationAction.NavigateToEditThumbnail)
        }
    }

    override fun onSaveClicked() {
        baseViewModelScope.launch {
            _onSaveSuccess.emit(true)
        }
    }

    init {
        viewModelScope.launch {
            inputRoomDescription.debounce(0).collectLatest {
                onRoomDescriptionCount(it.length)
                _roomDesWritten.value = it.length > 0
            }
        }

        viewModelScope.launch {
            inputRoomName.debounce(0).collectLatest {
                onRoomNameCount(it.length)
                _roomNameWritten.value = it.length > 0
            }
        }
        viewModelScope.launch {
            (_roomNameWritten).debounce(0).collectLatest {
                if(_roomDesWritten.value && _backgroundStored.value && _thumbnailStored.value)
                    _saveBtnEnable.emit(_roomNameWritten.value)
            }
        }

        viewModelScope.launch {
            (_roomDesWritten).debounce(0).collectLatest {
                if(_roomNameWritten.value && _backgroundStored.value && _thumbnailStored.value)
                    _saveBtnEnable.emit(_roomDesWritten.value)
            }
        }

        viewModelScope.launch {
            (_backgroundStored).debounce(0).collectLatest {
                if(_roomDesWritten.value && _roomNameWritten.value && _thumbnailStored.value)
                    _saveBtnEnable.emit(_backgroundStored.value)
            }
        }

        viewModelScope.launch {
            (_thumbnailStored).debounce(0).collectLatest {
                if(_roomDesWritten.value && _roomNameWritten.value && _backgroundStored.value)
                    _saveBtnEnable.emit(_thumbnailStored.value)
            }
        }
    }

    private fun onRoomNameCount(count: Int) {
        viewModelScope.launch {
            inputRoomNameCountEvent.value = count
        }
    }

    private fun onRoomDescriptionCount(count: Int) {
        viewModelScope.launch {
            inputRoomDescriptionCountEvent.value = count
        }
    }


}