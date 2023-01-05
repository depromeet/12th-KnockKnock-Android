package com.depromeet.knockknock.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentHomeBinding
import com.depromeet.knockknock.ui.home.adapter.HomeRecentAdapter
import com.depromeet.knockknock.ui.home.bottom.AlarmMoreType
import com.depromeet.knockknock.ui.home.bottom.BottomAlarmMore
import com.depromeet.knockknock.ui.home.bottom.BottomHomeMakeRoom
import com.depromeet.knockknock.ui.home.bottom.select.BottomHomeSelectRoom
import com.depromeet.knockknock.util.permission.PermissionManagerImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    private val TAG = "HomeFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_home

    override val viewModel : HomeViewModel by viewModels()
    private val adapter by lazy { HomeRecentAdapter(viewModel) }

    private val permissionManager = PermissionManagerImpl(this)
//    private val notificationPermissionRequest: PermissionRequester = permissionManager.forPermission(Permissions.PostNotification)
//        .onGranted { setFragmentResult(
//            KnockKnockIntent.RESULT_KEY_POST_NOTIFICATION_PERMISSION_GRANTED,
//            bundleOf(KnockKnockIntent.RESULT_KEY_POST_NOTIFICATION_PERMISSION_GRANTED to true)
//        ) }
//        .subscribe(this)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initStartView() {
        binding.apply {
            this.viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
//        initNotificationPermission()
        viewModel.getRecentNotifications()
        requireActivity().setStatusBarColor(viewModel.homeRandomNumber)
        initAdapter()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when(it) {
                    is HomeNavigationAction.NavigateToNotification -> navigate(HomeFragmentDirections.actionHomeFragmentToNotificationFragment())
                    is HomeNavigationAction.NavigateToCreatePush -> initRoomBottomSheet()
                    is HomeNavigationAction.NavigateToRoom -> {}
                    is HomeNavigationAction.NavigateToRecentAlarm -> {}
                    is HomeNavigationAction.NavigateToAlarmReaction -> {}
                    is HomeNavigationAction.NavigateToRecentAlarmMore -> {
                        initAlarmMoreBottomSheet(roomId = it.alarmId)
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.notifications.collectLatest {
                adapter.submitList(it)
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initAdapter() {
        binding.recentRecycle.adapter = adapter
    }

    private fun initRoomBottomSheet() {
        // -1: 알림방 탐색, -2: 알림방 생성하기, another: 방으로 이동
        val dialog = BottomHomeSelectRoom { selectRoom ->
            // 이동로작
            when(selectRoom) {
                -2 -> initCreateRoomBottomSheet()
                -1 -> {}
                else -> {}
            }
        }
        dialog.show(childFragmentManager, TAG)
    }

    private fun initCreateRoomBottomSheet() {
        val dialog = BottomHomeMakeRoom { isOpenRoom ->
            // 알림방 생성관련 로직
            if(isOpenRoom) {}
            else {}
        }
        dialog.show(childFragmentManager, TAG)
    }

    private fun initAlarmMoreBottomSheet(roomId: Int) {
        val dialog = BottomAlarmMore {
            when(it) {
                is AlarmMoreType.Copy -> {}
                is AlarmMoreType.Save -> {}
                is AlarmMoreType.Delete -> {}
                is AlarmMoreType.Declare -> {}
                is AlarmMoreType.Report -> {}
            }
        }
        dialog.show(childFragmentManager, TAG)
    }

//    private fun initNotificationPermission() {
//        lifecycleScope.launch {
//            if(NotificationManagerCompat.from(requireContext()).areNotificationsEnabled().not()) {
//                notificationPermissionRequest.request()
//            }
//        }
//    }
}
