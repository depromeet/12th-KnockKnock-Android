package com.depromeet.knockknock.ui.settingroom

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentSettingRoomBinding
import com.depromeet.knockknock.ui.category.CategoryFragmentArgs
import com.depromeet.knockknock.ui.settingroom.adapter.ExportMemberAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class SettingRoomFragment :
    BaseFragment<FragmentSettingRoomBinding, SettingRoomViewModel>(R.layout.fragment_setting_room) {

    private val TAG = "SettingRoomFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_setting_room

    override val viewModel: SettingRoomViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val memberAdapter by lazy { ExportMemberAdapter(viewModel) }


    private val args: SettingRoomFragmentArgs by navArgs()

    override fun initStartView() {
        viewModel.receivedRoomId.value = args.groupId

        binding.apply {
            this.settingroomviewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initAdapter()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when (it) {
                    is SettingRoomNavigationAction.NavigateToCategory -> navigate(
                        SettingRoomFragmentDirections.actionSettingRoomFragmentToCategoryFragment(
                            it.id,
                            it.title,
                            it.description,
                            it.thumbnailPath,
                            it.backgroundPath,
                            it.publicAccess,
                            it.categoryId
                        )
                    )
                    is SettingRoomNavigationAction.NavigateToLink -> {}
                    is SettingRoomNavigationAction.NavigateToAddMember -> navigate(
                        SettingRoomFragmentDirections.actionSettingRoomFragmentToInviteFriendToRoomFragment(
                            it.roomId
                        )
                    )
                    is SettingRoomNavigationAction.NavigateToExportMember -> {}
                    is SettingRoomNavigationAction.NavigateToRemove -> {
                        toastMessage("알림방을 제거했어요")
                        navController.popBackStack()}
                    is SettingRoomNavigationAction.NavigateToEditDetail -> {
                        navigate(
                            SettingRoomFragmentDirections.actionSettingRoomFragmentToEditRoomDetailsFragment(
                                it.id,
                                it.title,
                                it.description,
                                it.thumbnailPath,
                                it.backgroundPath,
                                it.publicAccess,
                                it.categoryId
                            )
                        )
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.roomMemberList.collectLatest {
                memberAdapter.submitList(it)
            }
        }
    }

    override fun initAfterBinding() {
    }

    override fun onResume() {
        super.onResume()
        viewModel.getGroupInfo()
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
        binding.friendRecycler.adapter = memberAdapter
    }
}