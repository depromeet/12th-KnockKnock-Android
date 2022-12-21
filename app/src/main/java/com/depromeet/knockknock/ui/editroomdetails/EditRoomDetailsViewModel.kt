package com.depromeet.knockknock.ui.editroomdetails

import androidx.lifecycle.viewModelScope
import com.depromeet.knockknock.ui.friendlist.FriendListActionHandler
import com.depromeet.knockknock.ui.friendlist.FriendListNavigationAction


import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.bookmark.BookmarkNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditRoomDetailsViewModel @Inject constructor(
) : BaseViewModel(), EditRoomDetailsActionHandler {

    private val TAG = "EditRoomDetailsViewModel"

    private val _navigationHandler: MutableSharedFlow<EditRoomDetailsNavigationAction> = MutableSharedFlow<EditRoomDetailsNavigationAction>()
    val navigationHandler: SharedFlow<EditRoomDetailsNavigationAction> = _navigationHandler.asSharedFlow()

    var inputRoomName = MutableStateFlow<String>("")
    var inputRoomNameCountEvent = MutableStateFlow<Int>(0)
    var inputRoomDescription = MutableStateFlow<String>("")
    var inputRoomDescriptionCountEvent = MutableStateFlow<Int>(0)

    private val _roomPublicPermitted: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
    val roomPublicPermitted: StateFlow<Boolean> = _roomPublicPermitted

    override fun onRoomPublicToggled(checked: Boolean) {
    }

    override fun onBackgroundClicked(backgroundId: Int) {
    }

    override fun onThumbnailClicked(thumbnailId: Int) {
        TODO("Not yet implemented")
    }

    override fun onSaveClicked() {
        TODO("Not yet implemented")
    }

    init {
        viewModelScope.launch {
            inputRoomDescription.debounce(0).collectLatest {
                onRoomDescriptionCount(it.length)
            }
        }

        viewModelScope.launch {
            inputRoomName.debounce(0).collectLatest {
                onRoomNameCount(it.length)
            }
        }
    }

    private fun onRoomNameCount(count: Int) {
        viewModelScope.launch {
            inputRoomNameCountEvent.value = count
        }
    }

    private fun onRoomDescriptionCount(count : Int){
        viewModelScope.launch {
            inputRoomDescriptionCountEvent.value = count
        }
    }


}