package com.depromeet.knockknock.ui.notification

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentNotificationBinding
import com.depromeet.knockknock.ui.notification.adapter.NotificationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class NotificationFragment : BaseFragment<FragmentNotificationBinding, NotificationViewModel>(R.layout.fragment_notification) {

    private val TAG = "NotificationFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_notification

    override val viewModel : NotificationViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val notificationAdapter by lazy { NotificationAdapter(viewModel) }

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
                    is NotificationNavigationAction.NavigateToNotificationDetail -> {}
                    is NotificationNavigationAction.NavigateToInviteRoomAllow -> {}
                    is NotificationNavigationAction.NavigateToInviteRoomDeclare -> toastMessage(getString(R.string.invite_room_declare_toast))
                    is NotificationNavigationAction.NavigateToInviteFriendAllow -> toastMessage(getString(R.string.invite_friend_allow_toast))
                    is NotificationNavigationAction.NavigateToInviteFriendDeclare -> toastMessage(getString(R.string.invite_friend_declare_toast))
                    is NotificationNavigationAction.NavigateToPushNotification -> {}
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.notificationList.collectLatest {
                notificationAdapter.submitList(it)
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.title = getString(R.string.notification_title)
            // 뒤로가기 버튼
            this.setNavigationIcon(R.drawable.ic_allow_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun initAdapter() {
        binding.friendRecycler.adapter = notificationAdapter
    }
}
