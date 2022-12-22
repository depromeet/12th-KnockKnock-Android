package com.depromeet.knockknock.ui.setprofile

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
import com.depromeet.knockknock.ui.editprofile.EditProfileNavigationAction
import com.depromeet.knockknock.ui.editprofile.bottom.EditProfileImageBottomSheet
import com.depromeet.knockknock.ui.mypage.MypageFragmentDirections
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
class SetProfileFragment : BaseFragment<FragmentSetProfileBinding, SetProfileViewModel>(R.layout.fragment_set_profile) {

    private val TAG = "SetProfileFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_set_profile

    override val viewModel : SetProfileViewModel by viewModels()
    private val navController by lazy { findNavController() }

    private lateinit var requestUpdateProfile : ActivityResultLauncher<Intent>

    override fun initStartView() {
        binding.apply {
            this.viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initRegisterForActivityResult()
        initEditText()
        countEditTextMessage()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when(it) {
                    is SetProfileNavigationAction.NavigateToSetProfileImage -> setProfileImageBottomSheet()
                    is SetProfileNavigationAction.NavigateToHome -> navigate(SetProfileFragmentDirections.actionSetProfileFragmentToHomeFragment())
                    is SetProfileNavigationAction.NavigateToEmpty -> toastMessage("닉네임이 비어 있습니다!")
                }
            }
        }
    }

    override fun initAfterBinding() {
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
                    val nicknamePart: MultipartBody.Part = MultipartBody.Part.createFormData("description", viewModel.inputContent.value)


                }
            }
        }
    }

    private fun setProfileImageBottomSheet() {
        val dialog = EditProfileImageBottomSheet {
            if(it) getGalleryImage()
            else getCaptureImage()
        }
        dialog.show(childFragmentManager, TAG)
    }

    private fun getGalleryImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        requestUpdateProfile.launch(intent)
    }

    private fun getCaptureImage() {
        val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE)
        } else {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        }
        requestUpdateProfile.launch(intent)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditText() {
        //포커싱 시 검정 테두리 필요할 시 주석 해제
        //binding.userNameContents.customOnFocusChangeListener(requireContext())
        binding.profileSetMain.setOnTouchListener { _, _ ->
            requireActivity().hideKeyboard()
            binding.userNameContents.clearFocus()
            false
        }
    }


    private fun countEditTextMessage() {
        lifecycleScope.launchWhenStarted {
            viewModel.editTextMessageCountEvent.collectLatest {
                binding.editTextCount.text = "$it/15"

                if (it != 0) {
                    binding.editTextCount.text =
                        com.depromeet.knockknock.ui.register.textChangeColor(
                            binding.editTextCount,
                            "#000000",
                            0,
                            it.toString().length
                        )
                }
            }
        }
    }
}