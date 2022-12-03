package com.depromeet.knockknock.ui.home

import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentHomeBinding
import com.depromeet.knockknock.util.KnockKnockIntent
import com.depromeet.knockknock.util.permission.PermissionManagerImpl
import com.depromeet.knockknock.util.permission.PermissionRequester
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.security.Permissions


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    private val TAG = "HomeFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_home

    override val viewModel : HomeViewModel by viewModels()

    private val permissionManager = PermissionManagerImpl(this)
//    private val notificationPermissionRequest: PermissionRequester = permissionManager.forPermission(Permissions.PostNotification)
//        .onGranted { setFragmentResult(
//            KnockKnockIntent.RESULT_KEY_POST_NOTIFICATION_PERMISSION_GRANTED,
//            bundleOf(KnockKnockIntent.RESULT_KEY_POST_NOTIFICATION_PERMISSION_GRANTED to true)
//        ) }
//        .subscribe(this)

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
//        initNotificationPermission()
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }

//    private fun initNotificationPermission() {
//        lifecycleScope.launch {
//            if(NotificationManagerCompat.from(requireContext()).areNotificationsEnabled().not()) {
//                notificationPermissionRequest.request()
//            }
//        }
//    }
}
