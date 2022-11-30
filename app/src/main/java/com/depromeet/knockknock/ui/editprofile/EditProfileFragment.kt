package com.depromeet.knockknock.ui.editprofile

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.AlertDialogModel
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.base.DefaultRedAlertDialog
import com.depromeet.knockknock.base.DefaultYellowAlertDialog
import com.depromeet.knockknock.databinding.FragmentEditProfileBinding
import com.depromeet.knockknock.util.KnockKnockIntent
import com.depromeet.knockknock.util.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody


@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding, EditProfileViewModel>(R.layout.fragment_edit_profile) {

    private val TAG = "EditProfileFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_edit_profile

    override val viewModel : EditProfileViewModel by viewModels()

    private lateinit var requestUpdateProfile: ActivityResultLauncher<Intent>

    override fun initStartView() {
        binding.apply {
            this.viewmodel  = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initRegisterForActivityResult()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when(it) {
                    is EditProfileNavigationAction.NavigateToLogout -> logOutDialog()
                    is EditProfileNavigationAction.NavigateToUserDelete -> userDeleteDialog()
                    is EditProfileNavigationAction.NavigateToSplash -> Unit
                    is EditProfileNavigationAction.NavigateToGallery -> getImageToGallery()
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun logOutDialog() {
        val res = AlertDialogModel(
            title = getString(R.string.logout_title),
            description = getString(R.string.logout_description),
            positiveContents = getString(R.string.logout),
            negativeContents = getString(R.string.no)
        )
        val dialog = DefaultYellowAlertDialog(
            alertDialogModel = res,
            clickToPositive = {
                toastMessage("로그아웃")
                viewModel.onUserDelete()
            },
            clickToNegative = {
                toastMessage("취소")
            }
        )
        dialog.show(requireActivity().supportFragmentManager, TAG)
    }

    private fun userDeleteDialog() {
        val res = AlertDialogModel(
            title = getString(R.string.user_delete_title),
            description = getString(R.string.user_delete_description),
            positiveContents = getString(R.string.user_delete_yes),
            negativeContents = getString(R.string.no)
        )
        val dialog: DefaultRedAlertDialog = DefaultRedAlertDialog(
            alertDialogModel = res,
            clickToPositive = {
                toastMessage("회원 탈퇴")
            },
            clickToNegative = {
                toastMessage("취소")
            }
        )
        dialog.show(requireActivity().supportFragmentManager, TAG)
    }

    private fun initRegisterForActivityResult() {
        requestUpdateProfile = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val isUpdateProfile = activityResult.data?.getBooleanExtra(KnockKnockIntent.RESULT_KEY_UPDATE_PROFILE, false) ?: false
            if (isUpdateProfile) {
                val intent = activityResult.data
                if (intent != null) {
                    val uri = intent.data
                    val file = uriToFile(uri!!,requireContext())
                    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                    val requestBody = MultipartBody.Part.createFormData("file", file.name, requestFile)

                    //TODO : 이후에 설명도 입력한걸로 넣기
                    val nicknamePart: MultipartBody.Part = MultipartBody.Part.createFormData("description", "테스트 설명")

                    // Update Profile API
                }
            }
        }
    }

    private fun getImageToGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        requestUpdateProfile.launch(intent)
    }
}
