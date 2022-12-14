package com.depromeet.knockknock.ui.setting_room

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.AlertDialogModel
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.base.DefaultRedAlertDialog
import com.depromeet.knockknock.databinding.FragmentSetProfileBinding
import com.depromeet.knockknock.databinding.FragmentSettingRoomBinding
import com.depromeet.knockknock.ui.editprofile.EditProfileNavigationAction
import com.depromeet.knockknock.ui.editprofile.bottom.EditProfileImageBottomSheet
import com.depromeet.knockknock.ui.mypage.MypageFragmentDirections
import com.depromeet.knockknock.ui.setting_room.adapter.ExportMemberAdapter
import com.depromeet.knockknock.util.KnockKnockIntent
import com.depromeet.knockknock.util.customOnFocusChangeListener
import com.depromeet.knockknock.util.hideKeyboard
import com.depromeet.knockknock.util.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody


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
                    is SettingRoomNavigationAction.NavigateToCategory -> {}
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