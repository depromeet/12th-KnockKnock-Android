package com.dida.android.presentation.views.nav.home

import com.dida.android.presentation.base.BaseViewModel
import com.dida.android.presentation.base.UiState
import com.dida.android.util.NftActionHandler
import com.dida.domain.*
import com.dida.domain.model.nav.home.Home
import com.dida.domain.model.nav.home.SoldOut
import com.dida.domain.usecase.main.HomeAPI
import com.dida.domain.usecase.main.PostLikeAPI
import com.dida.domain.usecase.main.PostUserFollowAPI
import com.dida.domain.usecase.main.SoldOutAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeAPI: HomeAPI,
    private val soldOutAPI: SoldOutAPI,
    private val postLikeAPI: PostLikeAPI,
    private val postUserFollowAPI: PostUserFollowAPI,
    ) : BaseViewModel(), HomeActionHandler, NftActionHandler {

    private val TAG = "HomeViewModel"

    private val _navigationEvent: MutableSharedFlow<HomeNavigationAction> = MutableSharedFlow<HomeNavigationAction>()
    val navigationEvent: SharedFlow<HomeNavigationAction> = _navigationEvent.asSharedFlow()

    private val _homeState: MutableStateFlow<UiState<Home>> = MutableStateFlow(UiState.Loading)
    val homeState: StateFlow<UiState<Home>> = _homeState.asStateFlow()

    private val _soldoutState: MutableStateFlow<UiState<List<SoldOut>>> = MutableStateFlow(UiState.Loading)
    val soldoutState: StateFlow<UiState<List<SoldOut>>> = _soldoutState.asStateFlow()

    private val _termState: MutableStateFlow<Int> = MutableStateFlow(7)
    val termState: StateFlow<Int> = _termState.asStateFlow()

    init {
        baseViewModelScope.launch {
            soldOutAPI.invoke(7)
                .onSuccess { _soldoutState.value = UiState.Success(it) }
                .flatMap { homeAPI() }
                .onSuccess { _homeState.value = UiState.Success(it) }
                .onError { e -> catchError(e) }
        }
    }

    private fun getHome() {
        baseViewModelScope.launch {
            homeAPI()
                .onSuccess {
                    _homeState.value = UiState.Success(it)
                    dismissLoading() }
                .onError { e -> catchError(e) }
        }
    }

    override fun onSoldOutDayClicked(term: Int) {
        baseViewModelScope.launch {
            soldOutAPI(term)
                .onSuccess {
                    _soldoutState.value = UiState.Success(it)
                    _termState.value = term }
                .onError { e -> catchError(e) }
        }
    }

    override fun onUserFollowClicked(userId: Int) {
        baseViewModelScope.launch {
            showLoading()
            postUserFollowAPI(userId.toLong())
                .onSuccess { getHome() }
                .onError { e -> catchError(e) }
        }
    }

    override fun onHotItemClicked(cardId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(HomeNavigationAction.NavigateToHotItem(cardId))
        }
    }

    override fun onHotSellerItemClicked(userId: Int) {
        baseViewModelScope.launch {
            _navigationEvent.emit(HomeNavigationAction.NavigateToHotSeller(userId))
        }
    }

    override fun onSoldOutItemClicked(cardId: Long) {
        baseViewModelScope.launch {
            _navigationEvent.emit(HomeNavigationAction.NavigateToSoldOut(cardId))
        }
    }

    override fun onCollectionItemClicked(userId: Int) {
        baseViewModelScope.launch {
            _navigationEvent.emit(HomeNavigationAction.NavigateToCollection(userId))
        }
    }

    override fun onNftItemClicked(nftId: Int) {
        baseViewModelScope.launch {
            _navigationEvent.emit(HomeNavigationAction.NavigateToRecentNftItem(nftId))
        }
    }

    override fun onLikeBtnClicked(nftId: Int) {
        baseViewModelScope.launch {
            showLoading()
            postLikeAPI(nftId.toLong())
                .onSuccess { getHome() }
                .onError { e -> catchError(e) }
        }
    }
}