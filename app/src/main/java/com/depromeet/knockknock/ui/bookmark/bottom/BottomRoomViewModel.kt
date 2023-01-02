package com.depromeet.knockknock.ui.bookmark.bottom

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.depromeet.domain.model.GroupContent
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.ui.bookmark.adapter.createFilterRoomPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomRoomViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), BottomRoomActionHandler {

    private val TAG = "BottomRoomViewModel"

    val clickRoomList = mutableListOf<Int>()

    val roomList: Flow<PagingData<GroupContent>> = createFilterRoomPagingSource(mainRepository = mainRepository).flow.cachedIn(baseViewModelScope)

    private val _isSelected: MutableSharedFlow<Unit> = MutableSharedFlow()
    val isSelected: SharedFlow<Unit> = _isSelected.asSharedFlow()

    override fun onRoomClicked(group_id: Int) {
        val findList = clickRoomList.filter {
            it == group_id
        }
        if(findList.isEmpty()) {
            clickRoomList.add(group_id)
        } else {
            clickRoomList.remove(group_id)
        }
    }

    override fun onSelectClicked() {
        baseViewModelScope.launch {
            _isSelected.emit(Unit)
        }
    }
}