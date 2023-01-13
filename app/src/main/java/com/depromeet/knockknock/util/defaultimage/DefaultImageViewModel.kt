package com.depromeet.knockknock.util.defaultimage

import com.depromeet.domain.model.Profile
import com.depromeet.domain.onError
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DefaultImageViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel(), DefaultImageActionHandler {

    private val TAG = "DefaultImageViewModel"

    private val _clickImageUrl: MutableStateFlow<Profile?> = MutableStateFlow(null)
    val clickImageUrl: StateFlow<Profile?> = _clickImageUrl.asStateFlow()

    private val _imageList: MutableStateFlow<List<Profile>> = MutableStateFlow(emptyList())
    val imageList: StateFlow<List<Profile>> = _imageList.asStateFlow()

    init {
        baseViewModelScope.launch {
            mainRepository.getProfiles()
                .onSuccess { _imageList.value = it.profiles }
        }
    }

    override fun onDefaultImageClicked(profile: Profile) {
        baseViewModelScope.launch {
            _clickImageUrl.emit(profile)
        }
    }
}