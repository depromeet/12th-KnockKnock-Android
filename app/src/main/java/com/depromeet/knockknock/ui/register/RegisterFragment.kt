package com.depromeet.knockknock.ui.register

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.provider.Settings.Secure
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentRegisterBinding
import com.depromeet.knockknock.util.hideKeyboard
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>(R.layout.fragment_register) {

    private val TAG = "RegisterFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_register

    override val viewModel : RegisterViewModel by viewModels()
    private val navController by lazy { findNavController() }

    private var googleLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == 1) {
            val data: Intent? = result.data
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initStartView() {
        binding.apply {
            this.viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initEditText()
        countEditTextMessage()
        createNotification()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when(it) {
                    is RegisterNavigationAction.NavigateToPushSetting -> pushSettingDialog()
                    is RegisterNavigationAction.NavigateToNotificationAlarm -> createNotification()
                    is RegisterNavigationAction.NavigateToKakaoLogin -> kakaoLogin()
                    is RegisterNavigationAction.NavigateToGoogleLogin -> googleLogin()
                    is RegisterNavigationAction.NavigateToLoginAlready -> navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
                    is RegisterNavigationAction.NavigateToLoginFrist -> navigate(RegisterFragmentDirections.actionRegisterFragmentToSetProfileFragment())
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun pushSettingDialog() {
        val res = RegisterAlertDialogModel(
            title = getString(R.string.alarm_permission_title),
            description = getString(R.string.alarm_permission_description),
            image = R.drawable.onboard_after_write,
            positiveContents = getString(R.string.alarm_permission_yes),
            negativeContents = getString(R.string.alarm_permission_no)
        )
        val dialog = RegisterDefaultYellowAlertDialog(
            alertDialogModel = res,
            clickToPositive = {
                toastMessage("푸쉬 알림 적용 완료")
                viewModel.setNotification(true)
            },
            clickToNegative = {
                toastMessage("푸쉬 알림 적용 해제")
                pushSettingNoDialog()
            }
        )
        dialog.show(childFragmentManager, TAG)
    }

    private fun pushSettingNoDialog() {
        val res = RegisterAlertDialogModel(
            title = getString(R.string.alarm_permission_no_title),
            description = getString(R.string.alarm_permission_no_description),
            image = R.drawable.onboard_after_write,
            positiveContents = getString(R.string.alarm_permission_no_confirm),
            negativeContents = null
        )
        val dialog = RegisterDefaultYellowAlertSingleButtonDialog(
            alertDialogModel = res,
        ) {

        }
        dialog.show(childFragmentManager, TAG)
    }

    @SuppressLint("ClickableViewAccessibility", "UseCompatLoadingForDrawables")
    private fun initEditText() {
        binding.editTextMessage.onFocusChangeListener = View.OnFocusChangeListener { view, gainFocus ->
            //포커스가 주어졌을 때
            if (gainFocus)
                view.background = requireContext().getDrawable(R.drawable.custom_backgroundgray03_radius10_line_gray08)
            else
                view.background = requireContext().getDrawable(R.drawable.custom_backgroundgray03_radius10)
        }
        binding.registerMain.setOnTouchListener { _, _ ->
            requireActivity().hideKeyboard()
            binding.editTextMessage.clearFocus()
            false
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("UseCompatLoadingForColorStateLists", "UseCompatLoadingForDrawables")
    private fun countEditTextMessage() {
        lifecycleScope.launchWhenStarted {
            viewModel.messageText.collectLatest {
                binding.editTextCount.text = "${it.count()}/200"

                if (it.isEmpty()) {
                    binding.editTextCount.text =
                        textChangeColor(binding.editTextCount, "#ff0000", 0, 1)
                    binding.testAlarmButton.backgroundTintList =
                        requireContext().getColorStateList(R.color.background_white_mode)
                    binding.onboardBeforeWrite.setImageDrawable(requireContext().getDrawable(R.drawable.onboard_before_write))
                } else {
                    binding.testAlarmButton.backgroundTintList =
                        requireContext().getColorStateList(R.color.main_yellow_light_mode)
                    binding.onboardBeforeWrite.setImageDrawable(requireContext().getDrawable(R.drawable.onboard_after_write))
                }
            }
        }
    }

    @SuppressLint("HardwareIds")
    private fun createNotification() {
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            viewModel.firebaseToken.value = it
            viewModel.deviceId.value = Secure.getString(requireContext().contentResolver, Secure.ANDROID_ID)
        }
    }

    private fun kakaoLogin() {
        val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            // 로그인 실패
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> toastMessage("접근이 거부 됨(동의 취소)")
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> toastMessage("유효하지 않은 앱")
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> toastMessage("인증 수단이 유효하지 않아 인증할 수 없는 상태")
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> toastMessage("요청 파라미터 오류")
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> toastMessage("유효하지 않은 scope ID")
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> toastMessage("설정이 올바르지 않음(android key hash)")
                    error.toString() == AuthErrorCause.ServerError.toString() -> toastMessage("서버 내부 에러")
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> toastMessage("앱이 요청 권한이 없음")
                    else -> toastMessage("카카오톡의 미로그인")
                }
            } else if (token != null) {
                token.idToken?.let {
                    viewModel.oauthLogin(idToken = it, provider = "KAKAO")
                }
            }
        }

        // 카카오톡 설치여부 확인
        UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = kakaoCallback)
    }

    private fun googleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())

        val signInIntent = mGoogleSignInClient.signInIntent
        googleLauncher.launch(signInIntent)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val idToken = completedTask.getResult(ApiException::class.java).idToken
            idToken?.let { token ->
                viewModel.oauthLogin(idToken = token, provider = "GOOGLE")
            }
        } catch (e: ApiException){
            toastMessage("구글 로그인에 실패 하였습니다.")
        }
    }
}