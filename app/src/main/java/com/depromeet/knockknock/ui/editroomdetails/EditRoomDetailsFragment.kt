package com.depromeet.knockknock.ui.editroomdetails

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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentEditRoomDetailsBinding
import com.depromeet.knockknock.ui.editprofile.bottom.EditProfileImageBottomSheet
import com.depromeet.knockknock.ui.editroomdetails.adapter.BackgroundAdapter
import com.depromeet.knockknock.ui.editroomdetails.adapter.ThumbnailAdapter
import com.depromeet.knockknock.ui.editroomdetails.model.Background
import com.depromeet.knockknock.ui.editroomdetails.model.Thumbnail
import com.depromeet.knockknock.ui.register.textChangeColor
import com.depromeet.knockknock.util.hideKeyboard
import com.depromeet.knockknock.util.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class EditRoomDetailsFragment :
    BaseFragment<FragmentEditRoomDetailsBinding, EditRoomDetailsViewModel>(R.layout.fragment_edit_room_details) {

    private val TAG = "EditRoomDetailsFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_edit_room_details

    override val viewModel: EditRoomDetailsViewModel by viewModels()
    private val navController by lazy { findNavController() }

    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    private lateinit var backgroundList : List<Background>
    private lateinit var thumbnailList : List<Thumbnail>
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
        binding.apply {
            this.viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
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
                    is EditRoomDetailsNavigationAction.NavigateToEditBackground -> {isBackground=true
                        editProfileImageBottomSheet()
                    viewModel._backgroundStored.value=true}
                    is EditRoomDetailsNavigationAction.NavigateToEditThumbnail -> {isBackground=false
                        editProfileImageBottomSheet()
                        viewModel._thumbnailStored.value=true}
                }
            }


        }
    }

    override fun initAfterBinding() {
        val backgroundAdapter = BackgroundAdapter(viewModel)
        binding.backgroundRecycler.adapter = backgroundAdapter
        val test1 = Background(
            backgroundId = 1,
            backgroundSrc = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
        )
        val test2 = Background(
            backgroundId = 2,
            backgroundSrc = "http://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
        )
        val test3 = Background(
            backgroundId = 3,
            backgroundSrc = "http://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
        )
        val test4 = Background(
            backgroundId = 4,
            backgroundSrc = "http://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
        )

        backgroundList = listOf(test1, test2, test3,test4)

        backgroundAdapter.submitList(backgroundList)


        val thumbnailAdapter = ThumbnailAdapter(viewModel)
        binding.thumbnailRecycler.adapter = thumbnailAdapter
        val thumbnailTest1 = Thumbnail(
            thumbnailId = 1,
            thumbnailSrc = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
        )
        val thumbnailTest2 = Thumbnail(
            thumbnailId = 2,
            thumbnailSrc = "http://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
        )
        val thumbnailTest3 = Thumbnail(
            thumbnailId = 3,
            thumbnailSrc = "http://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
        )
        val thumbnailTest4 = Thumbnail(
            thumbnailId = 4,
            thumbnailSrc = "http://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
        )
        thumbnailList = listOf(thumbnailTest1, thumbnailTest2, thumbnailTest3,thumbnailTest4)
        thumbnailAdapter.submitList(thumbnailList)
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.title = getString(R.string.detail_information)
            // 뒤로가기 버튼
            this.setNavigationIcon(R.drawable.ic_allow_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }


    private fun initAdapter() {
        //binding.friendRecycler.adapter = FriendListAdapter(viewModel)
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


    //배경화면 리스트에서 선택한 값을 띄우게 함
//    private fun selectFromBackgroundList(){
//        lifecycleScope.launchWhenCreated {
//            viewModel.backgroundImgId.collectLatest {
//                val backgroundSrc = backgroundList.get(it-1).backgroundSrc
//                Glide.with(requireContext())
//            .load(backgroundSrc)
//            .transform(CenterCrop(), RoundedCorners(20))
//            .into(binding.roomBackgroundImg)
//            }
//        }
//    }

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
//        val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE)
//        } else {
//            Intent(ACTION_IMAGE_CAPTURE)
//        }
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


}






