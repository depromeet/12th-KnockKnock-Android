package com.depromeet.knockknock.ui.register

import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.FragmentRegisterBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    private lateinit var binding : FragmentRegisterBinding
    private lateinit var kakaoAuthViewModel: KakaoAuthViewModel
    private lateinit var googleAuthViewModel: GoogleAuthViewModel
    private lateinit var testAlarmViewModel : TestAlarmViewModel


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

        binding.testAlarmButton.setOnClickListener{
            testAlarmViewModel.createNotificationChannel(testAlarmViewModel.CHANNEL_ID, "DemoChannel", "this is a demo")
            testAlarmViewModel.displayNotification(context!!)
        }

        lifecycleScope.launchWhenCreated {
            kakaoAuthViewModel.message.collect{
                view?.findNavController()?.navigate(R.id.action_registerFragment_to_homeFragment)
            }

        }
        countEditTextMessage()
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
                view?.findNavController()?.navigate(R.id.action_registerFragment_to_homeFragment)
            } catch (e: ApiException) {
                e.localizedMessage?.let { Log.d("MYTAG", it) }
            }
        }
    }

    private fun countEditTextMessage() {
        lifecycleScope.launchWhenStarted {
            testAlarmViewModel.editTextMessageCountEvent.collectLatest {
                binding.editTextCount.text = "$it/200"

                if (it == 0) {binding.editTextCount.text = textChangeColor(binding.editTextCount, "#ff0000", 0, 1)
                    binding.testAlarmButton.setBackgroundTintList(resources.getColorStateList(R.color.background_white_mode))
                }
                else binding.testAlarmButton.setBackgroundTintList(resources.getColorStateList(R.color.main_yellow_light_mode))
            }
        }
    }

}