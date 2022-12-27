package com.depromeet.knockknock.ui.settingroomforuser

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentSettingRoomBinding
import com.depromeet.knockknock.databinding.FragmentSettingRoomForUserBinding
import com.depromeet.knockknock.ui.settingroomforuser.adapter.RoomMemberAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class SettingRoomForUserFragment : BaseFragment<FragmentSettingRoomForUserBinding, SettingRoomForUserViewModel>(R.layout.fragment_setting_room_for_user) {

    private val TAG = "SettingRoomForUserFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_setting_room_for_user

    override val viewModel : SettingRoomForUserViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val adapter by lazy { RoomMemberAdapter(viewModel) }

    override fun initStartView() {
        binding.apply {
            this.settingroomforuserviewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initAdapter()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when(it) {
                    //is SettingRoomForUserNavigationAction.NavigateToCategory -> navigate(SettingRoomFragmentDirections.actionSettingRoomFragmentToCategoryFragment())
                    is SettingRoomForUserNavigationAction.NavigateToLink -> {}
                    is SettingRoomForUserNavigationAction.NavigateToAddMember -> {}
                    //is SettingRoomForUserNavigationAction.NavigateToEditDetail -> {navigate(SettingRoomFragmentDirections.actionSettingRoomFragmentToEditRoomDetailsFragment())}
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.title = getString(R.string.setting_alarm_title)
            // 뒤로가기 버튼
            this.setNavigationIcon(R.drawable.ic_allow_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun initAdapter() {
        binding.memberRecycler.adapter = adapter
    }
}