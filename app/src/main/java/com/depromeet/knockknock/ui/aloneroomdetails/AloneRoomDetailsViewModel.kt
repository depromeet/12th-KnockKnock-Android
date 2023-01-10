package com.depromeet.knockknock.ui.aloneroomdetails

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.depromeet.domain.model.*
import com.depromeet.domain.onError
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository


import com.depromeet.knockknock.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class AloneRoomDetailsViewModel @Inject constructor(
    private val mainRepository : MainRepository
) : BaseViewModel(), AloneRoomDetailsActionHandler {

    private val TAG = "EditRoomDetailViewModel"

    private val _navigationHandler: MutableSharedFlow<AloneRoomDetailsNavigationAction> =
        MutableSharedFlow<AloneRoomDetailsNavigationAction>()
    val navigationHandler: SharedFlow<AloneRoomDetailsNavigationAction> =
        _navigationHandler.asSharedFlow()

    var groupId = MutableStateFlow<Int>(0)

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


    var inputRoomNameCountEvent = MutableStateFlow<Int>(0)
    var inputRoomDescriptionCountEvent = MutableStateFlow<Int>(0)

    var inputRoomName = MutableStateFlow<String>("")
    var inputRoomDescription = MutableStateFlow<String>("")
    var thumbnailImg = MutableStateFlow<String>("https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23")
    var backgroundImg = MutableStateFlow<String>("https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23")
    var group_category_id = MutableStateFlow<Int>(0)
    var isRoomUnpublic = MutableStateFlow<Boolean>(false)


    val isGalleryImage: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)

    private val _thumbnailList : MutableStateFlow<List<Thumbnail>> = MutableStateFlow(emptyList())
    val thumbnailList: StateFlow<List<Thumbnail>> = _thumbnailList.asStateFlow()

    private val _backgroundList : MutableStateFlow<List<Background>> = MutableStateFlow(emptyList())
    val backGroundList: StateFlow<List<Background>> = _backgroundList.asStateFlow()

    init{
        baseViewModelScope.launch {
            mainRepository.getThumbnails()
                .onSuccess { response ->
                    _thumbnailList.emit(response.thumbnails)
                }
        }

        baseViewModelScope.launch {
            mainRepository.getBackgrounds()
                .onSuccess { response ->
                    _backgroundList.emit(response.backgrounds)
                }
        }
    }

    fun getThumbnails(){
        baseViewModelScope.launch {
            mainRepository.getThumbnails()
                .onSuccess { response ->
                    _thumbnailList.emit(response.thumbnails)
                }
        }
    }

    fun getBackgrounds(){
        baseViewModelScope.launch {
            mainRepository.getBackgrounds()
                .onSuccess { response ->
                    _backgroundList.emit(response.backgrounds)
                }
        }
    }

    override fun onRoomUnpublicToggled(checked: Boolean) {
        baseViewModelScope.launch {
            isRoomUnpublic.emit(!isRoomUnpublic.value)
        }
    }

    override fun onBackgroundClicked(backgroundUrl: String) {
        _backgroundStored.value = true

        baseViewModelScope.launch {
            backgroundImg.emit(backgroundUrl)
            _navigationHandler.emit(AloneRoomDetailsNavigationAction.NavigateToSetBackgroundFromList(backgroundUrl = backgroundUrl))
        }

    }

    override fun onThumbnailClicked(thumbnailUrl: String) {
        _thumbnailStored.value = true

        baseViewModelScope.launch {
            thumbnailImg.emit(thumbnailUrl)
            _navigationHandler.emit(AloneRoomDetailsNavigationAction.NavigateToSetThumbnailFromList(thumbnailUrl = thumbnailUrl))
        }

    }

    override fun onBackgroundEditClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(AloneRoomDetailsNavigationAction.NavigateToEditBackground)
        }
    }

    override fun onThumbnailEditClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(AloneRoomDetailsNavigationAction.NavigateToEditThumbnail)
        }
    }

    override fun onNextClicked() {
        baseViewModelScope.launch {
            _navigationHandler.emit(AloneRoomDetailsNavigationAction.NavigateToAloneRoomInviteFriend)
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

    fun setFileToUri(file: MultipartBody.Part, isBackground : Boolean) {
        baseViewModelScope.launch {
            mainRepository.postFileToUrl(file = file)
                .onSuccess {
                    if(isBackground) {
                        backgroundImg.value = it.image_url
                    }
                    else
                        thumbnailImg.value = it.image_url
                }
        }
    }


}