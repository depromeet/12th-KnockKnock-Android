package com.depromeet.knockknock.util.defaultreaction

import com.depromeet.domain.model.MyReactionInfo
import com.depromeet.domain.model.Profile
import com.depromeet.domain.model.Reaction
import com.depromeet.domain.model.ReactionCountInfo
import com.depromeet.domain.onError
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.util.defaultimage.DefaultImageActionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DefaultReactionViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), DefaultReactionActionHandler {

    private val TAG = "DefaultReactionViewModel"

    private val _clickImageUrl: MutableStateFlow<Int?> = MutableStateFlow(null)
    val clickImageUrl: StateFlow<Int?> = _clickImageUrl.asStateFlow()

    private val _imageList: MutableStateFlow<List<Reaction>> = MutableStateFlow(emptyList())
    val imageList: StateFlow<List<Reaction>> = _imageList.asStateFlow()

    init {
        baseViewModelScope.launch {
            mainRepository.getReactions()
                .onSuccess { _imageList.value = it.reactions }
        }
    }

    override fun onDefaultReactionClicked(reaction_id: Int) {
        baseViewModelScope.launch {
            _clickImageUrl.emit(reaction_id)
        }
    }
}