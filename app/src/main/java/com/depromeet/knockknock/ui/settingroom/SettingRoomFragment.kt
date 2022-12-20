package com.depromeet.knockknock.ui.settingroom

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentSettingRoomBinding
import com.depromeet.knockknock.ui.settingroom.adapter.ExportMemberAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class SettingRoomFragment : BaseFragment<FragmentSettingRoomBinding, SettingRoomViewModel>(R.layout.fragment_setting_room) {

    private val TAG = "SettingRoomFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_setting_room

    override val viewModel : SettingRoomViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val adapter by lazy { ExportMemberAdapter(viewModel) }

    override fun initStartView() {
        binding.apply {
            this.viewmodel = viewModel
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
                    is SettingRoomNavigationAction.NavigateToCategory -> navigate(SettingRoomFragmentDirections.actionSettingRoomFragmentToCategoryFragment())
                    is SettingRoomNavigationAction.NavigateToLink -> {}
                    is SettingRoomNavigationAction.NavigateToAddMember -> {}
                    is SettingRoomNavigationAction.NavigateToExportMember -> {}
                    is SettingRoomNavigationAction.NavigateToRemove -> {}
                    is SettingRoomNavigationAction.NavigateToEditDetail -> {}
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
        binding.friendRecycler.adapter = adapter
    }
}