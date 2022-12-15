package com.depromeet.knockknock.ui.editprofile

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.AlertDialogModel
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.base.DefaultRedAlertDialog
import com.depromeet.knockknock.base.DefaultYellowAlertDialog
import com.depromeet.knockknock.databinding.FragmentEditProfileBinding
import com.depromeet.knockknock.ui.editprofile.bottom.EditProfileImageBottomSheet
import com.depromeet.knockknock.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding, EditProfileViewModel>(R.layout.fragment_edit_profile) {

    private val TAG = "EditProfileFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_edit_profile

    override val viewModel : EditProfileViewModel by viewModels()
    private val navController by lazy { findNavController() }

    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    private var cameraUri: Uri? = null

    // 요청하고자 하는 권한들
    private val permissionList = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE)

    // 권한을 허용하도록 요청
    private val requestMultiplePermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
        results.forEach {
            if(!it.value) toastMessage("권한 허용 필요")
        }
    }

    override fun initStartView() {
        binding.apply {
            this.viewmodel  = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initRegisterForActivityResult()
        initEditText()
        initToolbar()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when(it) {
                    is EditProfileNavigationAction.NavigateToLogout -> logOutDialog()
                    is EditProfileNavigationAction.NavigateToUserDelete -> userDeleteDialog()
                    is EditProfileNavigationAction.NavigateToSplash -> Unit
                    is EditProfileNavigationAction.NavigateToEditProfileImage -> editProfileImageBottomSheet()
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.title = getString(R.string.profile_title)
            // 뒤로가기 버튼
            this.setNavigationIcon(R.drawable.ic_allow_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }

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
        dialog.show(childFragmentManager, TAG)
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
        dialog.show(childFragmentManager, TAG)
    }

    private fun editProfileImageBottomSheet() {
        requestMultiplePermission.launch(permissionList)
        val dialog = EditProfileImageBottomSheet {
            if(it) getGalleryImage()
            else getCaptureImage()
        }
        dialog.show(childFragmentManager, TAG)
    }

    private fun initRegisterForActivityResult() {
        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val intent = activityResult.data
            if (intent != null) {
                val data = intent.data
                val file = uriToFile(data!!, requireContext())
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val requestBody = MultipartBody.Part.createFormData("file", file.name, requestFile)
                // Update Profile API

                Glide.with(requireContext())
                    .load(file)
                    .transform(CenterCrop(), RoundedCorners(300))
                    .into(binding.userProfileEdit)
            }
        }

        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if(it) {
                cameraUri?.let { uri ->
                    Glide.with(requireContext())
                        .load(uri)
                        .transform(CenterCrop(), RoundedCorners(300))
                        .into(binding.userProfileEdit)
                }
            }
        }
    }

    private fun getGalleryImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        viewModel.isGalleryImage.value = true
        galleryLauncher.launch(intent)

    }

    private fun getCaptureImage() {
//        val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE)
//        } else {
//            Intent(ACTION_IMAGE_CAPTURE)
//        }
        viewModel.isGalleryImage.value = false
        cameraUri = createImageFile()
        cameraLauncher.launch(cameraUri)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditText() {
        binding.userNameContents.customOnFocusChangeListener(requireContext())
        binding.profileEditMain.setOnTouchListener { _, _ ->
            requireActivity().hideKeyboard()
            binding.userNameContents.clearFocus()
            false
        }
    }

    private fun createImageFile(): Uri? {
        val now = SimpleDateFormat("yyMMdd_HHmmss").format(Date())
        val content = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "img_$now.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        }
        return requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content)
    }
}
