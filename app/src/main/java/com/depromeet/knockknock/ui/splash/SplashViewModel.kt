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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    private val TAG = "SplashViewModel"

    private val _navigationHandler: MutableSharedFlow<SplashNavigationAction> = MutableSharedFlow<SplashNavigationAction>()
    val navigationHandler: SharedFlow<SplashNavigationAction> = _navigationHandler.asSharedFlow()

    fun loginCheck() {
        baseViewModelScope.launch {
            val accessToken = sSharedPreferences.getString("access_token", null)
            val refreshToken = sSharedPreferences.getString("refresh_token", null)
            accessToken?.let {
                mainRepository.postRefreshToken(refreshToken!!)
                    .onSuccess {
                        editor.putString("access_token", it.access_token)
                        editor.putString("refresh_token", it.refresh_token)
                        editor.commit()
                        _navigationHandler.emit(SplashNavigationAction.NavigateToAlreadyLogin) }
                    .onError {
                        editor.remove("access_token")
                        editor.remove("refresh_token")
                        editor.commit()
                        _navigationHandler.emit(SplashNavigationAction.NavigateToFirstLogin) }
            }

            if(accessToken == null) {
                _navigationHandler.emit(SplashNavigationAction.NavigateToFirstLogin)
            }
        }
    }

}