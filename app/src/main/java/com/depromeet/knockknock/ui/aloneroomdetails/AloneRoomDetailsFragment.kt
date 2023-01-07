package com.depromeet.knockknock.ui.aloneroomdetails

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.depromeet.knockknock.R

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentAloneRoomDetailsBinding
import com.depromeet.knockknock.ui.aloneroomdetails.adapter.AloneRoomBackgroundAdapter
import com.depromeet.knockknock.ui.aloneroomdetails.adapter.AloneRoomThumbnailAdapter
import com.depromeet.knockknock.ui.editprofile.bottom.EditProfileImageBottomSheet
import com.depromeet.knockknock.ui.register.textChangeColor
import com.depromeet.knockknock.util.hideKeyboard
import com.depromeet.knockknock.util.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class AloneRoomDetailsFragment :
    BaseFragment<FragmentAloneRoomDetailsBinding, AloneRoomDetailsViewModel>(R.layout.fragment_alone_room_details) {

    private val TAG = "EditRoomDetailsFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_alone_room_details

    private val args: AloneRoomDetailsFragmentArgs by navArgs()

    override val viewModel: AloneRoomDetailsViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val thumbnailAdapter by lazy { AloneRoomThumbnailAdapter(viewModel) }
    private val backgroundAdapter by lazy { AloneRoomBackgroundAdapter(viewModel) }

    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    private var cameraUri: Uri? = null
    private var isBackground: Boolean = true

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

        viewModel.group_category_id.value = args.categoryId

        binding.apply {
            this.viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initAdapter()
        initToolbar()
        initEditText()
        initRegisterForActivityResult()
        countRoomDescription()
        countRoomName()
        //selectFromBackgroundList()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.onSaveSuccess.collectLatest {
                toastMessage("변경사항이 저장되었어요!")
                navController.popBackStack()
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when (it) {
                    is AloneRoomDetailsNavigationAction.NavigateToEditBackground -> {isBackground=true
                        editProfileImageBottomSheet()
                    viewModel._backgroundStored.value=true}
                    is AloneRoomDetailsNavigationAction.NavigateToEditThumbnail -> {isBackground=false
                        editProfileImageBottomSheet()
                        viewModel._thumbnailStored.value=true}
                    is AloneRoomDetailsNavigationAction.NavigateToSetBackgroundFromList -> {setBackgroundFromList(it.backgroundUrl)}
                    is AloneRoomDetailsNavigationAction.NavigateToSetThumbnailFromList -> {setThumbnailFromList(it.thumbnailUrl)}
                    is AloneRoomDetailsNavigationAction.NavigateToAloneRoomInviteFriend -> navigate(AloneRoomDetailsFragmentDirections.actionAloneRoomDetailsFragmentToAloneRoomInviteFriendFragment(
                        viewModel.inputRoomName.value,
                        viewModel.inputRoomDescription.value,
                        viewModel.thumbnailImg.value,
                        viewModel.backgroundImg.value,
                        !viewModel.isRoomUnpublic.value,
                        viewModel.group_category_id.value
                    ))
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.thumbnailList.collectLatest {
                thumbnailAdapter.submitList(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.backGroundList.collectLatest {
                backgroundAdapter.submitList(it)
            }
        }
    }

    override fun initAfterBinding() {
    }

    override fun onResume() {
        super.onResume()
        viewModel.getThumbnails()
        viewModel.getBackgrounds()
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.title = "방 만들기"
            // 뒤로가기 버튼
            this.setNavigationIcon(R.drawable.ic_allow_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }


    private fun initAdapter() {
        binding.thumbnailRecycler.adapter = thumbnailAdapter
        binding.backgroundRecycler.adapter = backgroundAdapter

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditText() {
        //포커싱 시 검정 테두리 필요할 시 주석 해제
        //binding.userNameContents.customOnFocusChangeListener(requireContext())
        binding.editRoomDetailsMain.setOnTouchListener { _, _ ->
            requireActivity().hideKeyboard()
            binding.roomNameEditLayout.clearFocus()
            binding.roomDescriptionEditLayout.clearFocus()
            false
        }
    }

    private fun countRoomName() {
        lifecycleScope.launchWhenStarted {
            viewModel.inputRoomNameCountEvent.collectLatest {
                binding.roomNameCount.text = "$it/18"
                if (it != 0) {
                    binding.roomNameCount.text =
                        com.depromeet.knockknock.ui.register.textChangeColor(
                            binding.roomNameCount,
                            "#000000",
                            0,
                            it.toString().length
                        )
                }
                else{
                    binding.roomNameCount.text = textChangeColor(binding.roomNameCount, "#ff0000", 0, 1)
                }
            }
        }
    }

    private fun countRoomDescription() {
        lifecycleScope.launchWhenStarted {
            viewModel.inputRoomDescriptionCountEvent.collectLatest {
                binding.roomDescriptionCount.text = "$it/80"
                if (it != 0) {
                    binding.roomDescriptionCount.text =
                        com.depromeet.knockknock.ui.register.textChangeColor(
                            binding.roomDescriptionCount,
                            "#000000",
                            0,
                            it.toString().length
                        )
                }
                else{
                    binding.roomDescriptionCount.text =textChangeColor(binding.roomDescriptionCount, "#ff0000", 0, 1)
                }
            }
        }
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
            activityResult.data?.let {
                createFile(it.data!!)
            }
        }

        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if(it) { cameraUri?.let { uri ->
                createFile(uri)
            } }
        }
    }

    private fun getGalleryImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        viewModel.isGalleryImage.value = true
        galleryLauncher.launch(intent)

    }

    private fun getCaptureImage() {
        viewModel.isGalleryImage.value = false
        cameraUri = createImageFile()
        cameraLauncher.launch(cameraUri)
    }

    private fun createImageFile(): Uri? {
        val now = SimpleDateFormat("yyMMdd_HHmmss").format(Date())
        val content = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "img_$now.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        }
        return requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content)
    }

    private fun createFile(uri: Uri) {
        val file = uriToFile(uri, requireContext())
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val requestBody = MultipartBody.Part.createFormData("file", file.name, requestFile)
        // Update Profile API
        val creatingView = when{
            isBackground -> binding.roomBackgroundImg
            else -> {binding.roomThumbnailImg}
        }
        viewModel.setFileToUri(file = requestBody, isBackground = isBackground)

        val invisibleDescriptionView = when{
            isBackground->binding.roomBackgroundAddDescription
            else->{binding.roomThumbnailAddDescription}
        }

        Glide.with(requireContext())
            .load(uri)
            .transform(CenterCrop(), RoundedCorners(20))
            .into(creatingView)

        invisibleDescriptionView.visibility = View.INVISIBLE
    }

    private fun setBackgroundFromList(url : String){
        Glide.with(requireContext())
            .load(url)
            .transform(CenterCrop(), RoundedCorners(20))
            .into(binding.roomBackgroundImg)

        binding.roomBackgroundAddDescription.visibility = View.INVISIBLE
    }

    private fun setThumbnailFromList(url : String){
        Glide.with(requireContext())
            .load(url)
            .transform(CenterCrop(), RoundedCorners(20))
            .into(binding.roomThumbnailImg)

        binding.roomThumbnailAddDescription.visibility = View.INVISIBLE
    }

}






