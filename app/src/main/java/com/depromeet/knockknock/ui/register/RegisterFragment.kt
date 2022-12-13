package com.depromeet.knockknock.ui.register

import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationManager
import android.app.ProgressDialog.show
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.AlertDialogModel
import com.depromeet.knockknock.base.DefaultRedAlertDialog
import com.depromeet.knockknock.base.DefaultYellowAlertDialog
import com.depromeet.knockknock.databinding.FragmentRegisterBinding
import com.depromeet.knockknock.ui.setprofile.SetProfileFragmentDirections
import com.depromeet.knockknock.ui.setprofile.SetProfileNavigationAction
import com.depromeet.knockknock.util.customOnFocusChangeListener
import com.depromeet.knockknock.util.hideKeyboard
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val TAG = "RegisterFragment"
    private var toast : Toast? = null

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    private lateinit var binding : FragmentRegisterBinding
    private lateinit var kakaoAuthViewModel: KakaoAuthViewModel
    private lateinit var googleAuthViewModel: GoogleAuthViewModel
    private lateinit var testAlarmViewModel : TestAlarmViewModel


    fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            testAlarmViewModel.navigationHandler.collectLatest {
                when(it) {
                    is RegisterNavigationAction.NavigateToPushSetting -> pushSettingDialog()
                    is RegisterNavigationAction.NavigateToNotificationAlarm -> testAlarm()
                }
            }
        }
    }

    private fun pushSettingDialog() {
        val res = AlertDialogModel(
            title = getString(R.string.alarm_permission_title),
            description = getString(R.string.alarm_permission_description),
            positiveContents = getString(R.string.alarm_permission_yes),
            negativeContents = getString(R.string.alarm_permission_no)
        )
        val dialog: DefaultYellowAlertDialog = DefaultYellowAlertDialog(
            alertDialogModel = res,
            clickToPositive = {
                testAlarmViewModel.pushAlarmAgreed.value = true
                toast?.cancel()
                toast = Toast.makeText(activity, "푸쉬 알림 적용 완료", Toast.LENGTH_SHORT)?.apply { show() }
            },
            clickToNegative = {
                toast?.cancel()
                toast = Toast.makeText(activity, "푸쉬 알림 적용 해제", Toast.LENGTH_SHORT)?.apply { show() }
            }
        )
        dialog.show(childFragmentManager, TAG)
    }

    private fun testAlarm(){
        testAlarmViewModel.createNotificationChannel(testAlarmViewModel.CHANNEL_ID, "DemoChannel", "this is a demo")
        testAlarmViewModel.displayNotification(context!!)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register,container,false)
        binding.lifecycleOwner = this


        val googleAuthViewModelfactory = GoogleAuthViewModelFactory(activity!!.application, object : OnSignInStartedListener {
            override fun onSignInStarted(client: GoogleSignInClient?) {
                startActivityForResult(client?.signInIntent, RC_SIGN_IN)
            }
        })

        val notificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val testAlarmViewModelFactory = TestAlarmViewModelFactory(activity!!.application, notificationManager)

        kakaoAuthViewModel = ViewModelProvider(this).get(KakaoAuthViewModel::class.java)
        googleAuthViewModel = ViewModelProvider(this,googleAuthViewModelfactory).get(GoogleAuthViewModel::class.java)
        testAlarmViewModel = ViewModelProvider(this,testAlarmViewModelFactory).get(TestAlarmViewModel::class.java)

        binding.kakaoRegisterViewModel = kakaoAuthViewModel
        binding.googleRegisterViewModel = googleAuthViewModel
        binding.testAlarmViewModel = testAlarmViewModel

        binding.kakaoRegisterButton.setOnClickListener{
            kakaoAuthViewModel.handleKakaoLogin()
        }

        binding.googleRegisterButton.setOnClickListener {
            googleAuthViewModel.signIn()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            kakaoAuthViewModel.message.collect{
                view?.findNavController()?.navigate(R.id.action_registerFragment_to_setProfileFragment)
            }

        }
        initDataBinding()
        countEditTextMessage()
        initEditText()
        return binding.root
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK && data != null) {
            // this task is responsible for getting ACCOUNT SELECTED
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                googleAuthViewModel.firebaseAuthWithGoogle(account.idToken!!)
                Log.d("MYTAG", account.id.toString())
                Log.d("MYTAG", account.idToken.toString())
                Log.d("MYTAG", account.account.toString())
                Log.d("MYTAG", account.email.toString())
                view?.findNavController()?.navigate(R.id.action_registerFragment_to_setProfileFragment)
            } catch (e: ApiException) {
                e.localizedMessage?.let { Log.d("MYTAG", it) }
            }
        }
    }

    private fun countEditTextMessage() {
        lifecycleScope.launchWhenStarted {
            testAlarmViewModel.editTextMessageCountEvent.collectLatest {
                binding.editTextCount.text = "$it/200"

                if (it == 0) {
                    binding.editTextCount.text =
                        textChangeColor(binding.editTextCount, "#ff0000", 0, 1)
                    binding.testAlarmButton.setBackgroundTintList(resources.getColorStateList(R.color.background_white_mode))
                    binding.onboardBeforeWrite.setImageDrawable(resources.getDrawable(R.drawable.onboard_before_write))
                } else {
                    binding.testAlarmButton.setBackgroundTintList(resources.getColorStateList(R.color.main_yellow_light_mode))
                    binding.onboardBeforeWrite.setImageDrawable(resources.getDrawable(R.drawable.onboard_after_write))
                }
            }
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun initEditText() {

        binding.editTextMessage.onFocusChangeListener = View.OnFocusChangeListener { view, gainFocus ->
            //포커스가 주어졌을 때
            if (gainFocus) {binding.editTextMessageLayout.background = context!!.getDrawable(R.drawable.custom_backgroundgray03_radius10_line_gray08)
            }
            
            else binding.editTextMessageLayout.background = context!!.getDrawable(R.drawable.custom_backgroundgray03_radius10)
        }
        binding.registerMain.setOnTouchListener { _, _ ->
            requireActivity().hideKeyboard()
            binding.editTextMessage.clearFocus()
            false
        }
    }

}