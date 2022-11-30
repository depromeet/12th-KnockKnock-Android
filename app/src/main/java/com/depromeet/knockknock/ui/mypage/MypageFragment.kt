package com.depromeet.knockknock.ui.mypage

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentMypageBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MypageFragment : BaseFragment<FragmentMypageBinding, MypageViewModel>(R.layout.fragment_mypage) {

    private val TAG = "MypageFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_mypage

    override val viewModel : MypageViewModel by viewModels()

    override fun initStartView() {
        binding.apply {
            this.viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
    }

    override fun initDataBinding() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collect {
                when(it) {
                    is MypageNavigationAction.NavigateToProfileEdit -> navigate(MypageFragmentDirections.actionMyPageFragmentToEditProfileFragment())
                    is MypageNavigationAction.NavigateToAlarmSetting -> navigate(MypageFragmentDirections.actionMyPageFragmentToAlarmSettingFragment())
                    is MypageNavigationAction.NavigateToBookmark -> navigate(MypageFragmentDirections.actionMyPageFragmentToBookmarkFragment())
                    is MypageNavigationAction.NavigateToFriendList -> navigate(MypageFragmentDirections.actionMyPageFragmentToFriendListFragment())
                    is MypageNavigationAction.NavigateToInformation -> navigate(MypageFragmentDirections.actionMyPageFragmentToInformationFragment())
                }
            }
        }
    }

    override fun initAfterBinding() {
    }
}
