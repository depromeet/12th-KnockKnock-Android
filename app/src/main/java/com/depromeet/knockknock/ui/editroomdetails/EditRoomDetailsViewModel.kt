package com.depromeet.knockknock.ui.editroomdetails

import androidx.lifecycle.viewModelScope
import com.depromeet.domain.model.*
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository


import com.depromeet.knockknock.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class EditRoomDetailsViewModel @Inject constructor(
    private val mainRepository : MainRepository
) : BaseViewModel(), EditRoomDetailsActionHandler {

    private val TAG = "EditRoomDetailsViewModel"

    private val _navigationHandler: MutableSharedFlow<EditRoomDetailsNavigationAction> =
        MutableSharedFlow<EditRoomDetailsNavigationAction>()
    val navigationHandler: SharedFlow<EditRoomDetailsNavigationAction> =
        _navigationHandler.asSharedFlow()

    //이미지 id를 사용할 경우 주석해제
//    private val _backgroundImgUrl : MutableSharedFlow<String> = MutableSharedFlow<String>()
//    val backgroundImgUrl: SharedFlow<String> = _backgroundImgUrl.asSharedFlow()
//
//    private val _thumbnailImgUrl : MutableSharedFlow<String> = MutableSharedFlow<String>()
//    val thumbnailImgUrl: SharedFlow<String> = _thumbnailImgUrl.asSharedFlow()

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

    var group_id = MutableStateFlow<Int>(1)
    var inputRoomName = MutableStateFlow<String>("")
    var inputRoomDescription = MutableStateFlow<String>("")
    var thumbnailImg = MutableStateFlow<String>("https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23")
    var backgroundImg = MutableStateFlow<String>("https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23")
    var group_category_id = MutableStateFlow<Int>(0)
    var isRoomUnpublic = MutableStateFlow<Boolean>(false)


    val isGalleryImage: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)

    private val _thumbnailList : MutableStateFlow<List<Thumbnail>> = MutableStateFlow(emptyList())
    val thumbnailList: StateFlow<List<Thumbnail>> = _thumbnailList.asStateFlow()

    private val _backgroundList : MutableStateFlow<List<Background>> = MutableStateFlow(listOf(
        Background(1, "https://user-images.githubusercontent.com/13329304/207665698-4c5b7a46-08fc-47bc-8041-a73d60d0f22e.png")
    ))
    val backGroundList: StateFlow<List<Background>> = _backgroundList.asStateFlow()

    init{
        baseViewModelScope.launch {
            mainRepository.getThumbnails()
                .onSuccess { response ->
                    _thumbnailList.emit(response.thumbnails)
                }
        }

        baseViewModelScope.launch {
//            mainRepository.getBackgrounds()
//                .onSuccess { response ->
//                    _backgroundList.emit(response.backgrounds)
//                }
            _backgroundList.emit(
                listOf(
                    Background(1, "https://user-images.githubusercontent.com/13329304/207665698-4c5b7a46-08fc-47bc-8041-a73d60d0f22e.png")
                )
            )
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
            _navigationHandler.emit(EditRoomDetailsNavigationAction.NavigateToSetBackgroundFromList(backgroundUrl = backgroundUrl))
        }

    }

    override fun onThumbnailClicked(thumbnailUrl: String) {
        _thumbnailStored.value = true

        baseViewModelScope.launch {
            thumbnailImg.emit(thumbnailUrl)
            _navigationHandler.emit(EditRoomDetailsNavigationAction.NavigateToSetThumbnailFromList(thumbnailUrl = thumbnailUrl))
        }

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
            mainRepository.putGroup(
                id = group_id.value,
                title = inputRoomName.value,
                description = inputRoomDescription.value,
                public_access = !isRoomUnpublic.value,
                thumbnail_path = thumbnailImg.value,
                background_image_path = backgroundImg.value,
                category_id = group_category_id.value
                ,)
                .onSuccess {
                    //_onSaveSuccess.emit(true)
                }
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

    fun setFileToUri(file: MultipartBody.Part, isBackground : Boolean) {
        baseViewModelScope.launch {
            mainRepository.postFileToUrl(file = file)
                .onSuccess {
                    if(isBackground)
                        backgroundImg.value = it.image_url
                    else
                        thumbnailImg.value = it.image_url
                }
        }
    }


}