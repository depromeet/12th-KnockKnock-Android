package com.depromeet.knockknock.ui.splash

import com.depromeet.domain.onError
import com.depromeet.domain.onSuccess
import com.depromeet.domain.repository.MainRepository
import com.depromeet.knockknock.base.BaseViewModel
import com.depromeet.knockknock.di.PresentationApplication
import com.depromeet.knockknock.di.PresentationApplication.Companion.editor
import com.depromeet.knockknock.di.PresentationApplication.Companion.sSharedPreferences
import com.depromeet.knockknock.ui.setprofile.SetProfileNavigationAction
//import com.dida.android.presentation.views.nav.home.HomeNavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    private val TAG = "SplashViewModel"

    private val _navigationHandler: MutableStateFlow<Int> = MutableStateFlow(0)
    val navigationHandler: StateFlow<Int> = _navigationHandler.asStateFlow()

    private val _isVersionCheck: MutableSharedFlow<Unit> = MutableSharedFlow()
    val isVersionCheck: SharedFlow<Unit> = _isVersionCheck.asSharedFlow()

    fun checkVersion(version: String) {
        baseViewModelScope.launch {
            mainRepository.getAppVersion()
                .onSuccess {
                    if(version == it.version) {
                        _isVersionCheck.emit(Unit)
                    } }
                .onError { exception ->
                    catchError(e = exception) }
        }
    }

    fun getUserToken() {
        baseViewModelScope.launch {
            val accessToken = sSharedPreferences.getString("access_token", null)
            val refreshToken = sSharedPreferences.getString("refresh_token", null)
            if(accessToken == null) {
                _navigationHandler.value = 1
            }

            accessToken.let {
                mainRepository.postRefreshToken(refreshToken!!)
                    .onSuccess {
                        editor.putString("access_token", it.access_token)
                        editor.putString("refresh_token", it.refresh_token)
                        editor.commit()
                        _navigationHandler.value = 2 }
                    .onError { exception ->
                        catchError(e = exception) }
            }
        }
    }
}