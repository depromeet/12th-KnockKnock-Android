package com.depromeet.knockknock.ui.register

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class KakaoAuthViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext

    //    val accessToken = MutableLiveData<String?>()
//    val userEmail = MutableLiveData<String>()
    private val _flowAccessToken = MutableStateFlow<String>("")
    val flowAccessToken: StateFlow<String> = _flowAccessToken
    private val _flowUserEmail = MutableStateFlow<String?>("")
    val flowUserEmail: StateFlow<String?> = _flowUserEmail

    private val _message = MutableSharedFlow<String>()
    val message: SharedFlow<String> = _message

    fun handleKakaoLogin() {
        // 로그인 조합 예제

// 카카오계정으로 로그인 공통 callback 구성
// 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("MYTAG", "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i("MYTAG", "카카오계정으로 로그인 성공 ${token.accessToken}")
                _flowAccessToken.value = token.accessToken
                Log.i("MYTAG", "${token.idToken}")
                viewModelScope.launch {
                    _message.emit("logged in successfully")
                }



                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e(TAG, "사용자 정보 요청 실패", error)
                    } else if (user != null) {
                        var scopes = mutableListOf<String>()

//                        if (user.kakaoAccount?.emailNeedsAgreement == true) {
//                            scopes.add("account_email")
//                        }
//                        if (user.kakaoAccount?.birthdayNeedsAgreement == true) {
//                            scopes.add("birthday")
//                        }
//                        if (user.kakaoAccount?.birthyearNeedsAgreement == true) {
//                            scopes.add("birthyear")
//                        }
//                        if (user.kakaoAccount?.genderNeedsAgreement == true) {
//                            scopes.add("gender")
//                        }
//                        if (user.kakaoAccount?.phoneNumberNeedsAgreement == true) {
//                            scopes.add("phone_number")
//                        }
//                        if (user.kakaoAccount?.profileNeedsAgreement == true) {
//                            scopes.add("profile")
//                        }
//                        if (user.kakaoAccount?.ageRangeNeedsAgreement == true) {
//                            scopes.add("age_range")
//                        }
//                        if (user.kakaoAccount?.ciNeedsAgreement == true) {
//                            scopes.add("account_ci")
//                        }
//                        Log.d("MYTAG", "${scopes}")

                        user.kakaoAccount?.let {
                            when(true) {
                                (it.emailNeedsAgreement == true) -> scopes.add("account_email")
                                (it.birthdayNeedsAgreement == true) -> scopes.add("birthday")
                                (it.birthyearNeedsAgreement == true) -> scopes.add("birthyear")
                                (it.genderNeedsAgreement == true) -> scopes.add("gender")
                                (it.phoneNumberNeedsAgreement == true) -> scopes.add("profile")
                                (it.profileNeedsAgreement == true) -> scopes.add("age_range")
                                (it.ageRangeNeedsAgreement == true) -> scopes.add("age_range")
                                (it.ciNeedsAgreement == true) -> scopes.add("account_ci")
                                else -> Unit
                            }
                        }

                        if (scopes.isNotEmpty()) {
                            Log.d(TAG, "사용자에게 추가 동의를 받아야 합니다.")

                            // OpenID Connect 사용 시
                            // scope 목록에 "openid" 문자열을 추가하고 요청해야 함
                            // 해당 문자열을 포함하지 않은 경우, ID 토큰이 재발급되지 않음
                            // scopes.add("openid")

                            //scope 목록을 전달하여 카카오 로그인 요청
                            UserApiClient.instance.loginWithNewScopes(
                                context,
                                scopes
                            ) { token, error ->
                                if (error != null) {
                                    Log.e(TAG, "사용자 추가 동의 실패", error)
                                } else {
                                    Log.d(TAG, "allowed scopes: ${token!!.scopes}")

                                    // 사용자 정보 재요청
                                    UserApiClient.instance.me { user, error ->
                                        if (error != null) {
                                            Log.e(TAG, "사용자 정보 요청 실패", error)
                                        } else if (user != null) {
                                            Log.i(TAG, "사용자 정보 요청 성공")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e(TAG, "사용자 정보 요청 실패", error)
                    } else if (user != null) {
                        Log.i(
                            TAG, "사용자 정보 요청 성공" +
                                    "\n회원번호: ${user.id}" +
                                    "\n이메일: ${user.kakaoAccount?.email}" +
                                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                    "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                        )
                        _flowUserEmail.value = user.kakaoAccount?.email
                    }
                }
            }
        }

        // 1. 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인 -> 카카오 웹으로 무조건 이동
        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)

        // 2. 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
//        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
//            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
//                if (error != null) {
//                    Log.e(TAG, "카카오톡으로 로그인 실패", error)
//
//                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
//                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
//                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
//                        return@loginWithKakaoTalk
//                    }
//
//                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
//                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
//                } else if (token != null) {
//                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
//                }
//            }
//        } else {
//            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
//        }

    }
}

