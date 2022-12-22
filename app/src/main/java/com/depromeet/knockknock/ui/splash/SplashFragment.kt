package com.depromeet.knockknock.ui.splash

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentMypageBinding
import com.depromeet.knockknock.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(R.layout.fragment_splash) {

    private val TAG = "MypageFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_splash

    override val viewModel : SplashViewModel by viewModels()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when(it) {
                    is SplashNavigationAction.NavigateToFirstLogin -> navigate(SplashFragmentDirections.actionSplashFragmentToOnboardFragment())
                    is SplashNavigationAction.NavigateToAlreadyLogin -> navigate(SplashFragmentDirections.actionMainFragment())
                }
            }
        }
    }

    override fun initAfterBinding() {
    }


}
