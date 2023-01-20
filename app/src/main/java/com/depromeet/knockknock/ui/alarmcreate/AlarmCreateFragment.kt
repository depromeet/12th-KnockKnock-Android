package com.depromeet.knockknock.ui.alarmcreate

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.MotionEvent
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.AlertDialogModel
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.base.DefaultGrayAlertDialog
import com.depromeet.knockknock.base.DefaultRedAlertDialog
import com.depromeet.knockknock.databinding.FragmentAlarmCreateBinding
import com.depromeet.knockknock.ui.alarmcreate.adapter.RecommendationAdapter
import com.depromeet.knockknock.ui.alarmcreate.bottom.BottomAlarmReservationPicker
import com.depromeet.knockknock.ui.alarmcreate.bottom.BottomAlarmSend
import com.depromeet.knockknock.ui.alarmcreate.bottom.BottomImageAdd
import com.depromeet.knockknock.ui.preview.PreviewFragmentArgs
import com.depromeet.knockknock.util.KnockKnockIntent
import com.depromeet.knockknock.util.hideKeyboard
import com.depromeet.knockknock.util.showKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class AlarmCreateFragment :
    BaseFragment<FragmentAlarmCreateBinding, AlarmCreateViewModel>(R.layout.fragment_alarm_create) {

    private val TAG = "AlarmCreateFragment"

    private lateinit var requestUpdateProfile: ActivityResultLauncher<Intent>

    override val layoutResourceId: Int
        get() = R.layout.fragment_alarm_create
    private val navController by lazy { findNavController() }

    override val viewModel: AlarmCreateViewModel by viewModels()
    private val recommendationAdapter by lazy { RecommendationAdapter(viewModel) }

    init {
        setupEvent()
    }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        val args: AlarmCreateFragmentArgs by navArgs()
        viewModel.groupId.value = args.alarmId
        viewModel.groupTitle.value = args.roomTitle
        if (args.title != "") viewModel.editTextTitleEvent.value = args.title else viewModel.initEditTextTitleEvent()
        if (args.message != "") viewModel.editTextMessageEvent.value = args.message
        if (args.reservation != 0) updateReservationAlarmSend(args.reservation)

        Log.d("ttt editTextTitleEvent", viewModel.editTextTitleEvent.value)
        Log.d("ttt title", args.title)

        initEditText()
        initRegisterForActivityResult()
        initToolbar()
        initAdapter()
        setOnTouchListenerEditText()
    }

    private fun setupEvent() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is AlarmCreateNavigationAction.NavigateToAddImage -> addImageBottomSheet()
                    is AlarmCreateNavigationAction.NavigateToAlarmSend -> alarmSend()
                    is AlarmCreateNavigationAction.NavigateToFocusTitleText -> focusTitleText()
                    is AlarmCreateNavigationAction.NavigateToDeleteMessageText -> deleteMessageText()
                    is AlarmCreateNavigationAction.NavigateToRecommendationMessageText -> addRecommendationMessage(
                        it.message
                    )
                    is AlarmCreateNavigationAction.NavigateToPreview -> navigate(
                        AlarmCreateFragmentDirections.actionAlarmCreateFragmentToPreviewFragment(
                            it.title,
                            it.message,
                            it.uri
                        )
                    )
                    is AlarmCreateNavigationAction.NavigateToPushAlarm -> {
                        toastMessage("푸시알림을 발송했습니다!")
                        navController.popBackStack()
                    }
                    is AlarmCreateNavigationAction.NavigateToReservationPushAlarm -> {
                        toastMessage("푸시알림이 예약되었어요!")
                        navController.popBackStack()
                    }

                    is AlarmCreateNavigationAction.NavigateToNoReservationAlarm -> alarmNoReservationDialog()
                    is AlarmCreateNavigationAction.NavigateToBackStack -> navController.popBackStack()
                }
            }
        }
    }

    private fun addRecommendationMessage(message: String) = binding.editTextMessage.let {
        val editTextMessageStart = it.text.substring(0 until it.selectionStart)
        val editTextMessageEnd = it.text.substring(it.selectionStart until it.length())
        if (it.text.length + message.length <= 200) {
            it.setText(editTextMessageStart + message + editTextMessageEnd)
            it.setSelection(editTextMessageStart.length + message.length)
        }
    }

    private fun initAdapter() {
        binding.rvList.adapter = recommendationAdapter
    }

    private fun focusTitleText() = binding.editTextTitle.let {
        it.requestFocus()
        it.setSelection(it.text.length)
        requireActivity().showKeyboard(it)
    }

    private fun deleteMessageText() {
        viewModel.editTextMessageEvent.value = ""
        binding.editTextMessage.requestFocus()
        requireActivity().showKeyboard(binding.editTextMessage)

    }

    private fun alarmSend() {
        Log.d("ttt", "alarmSend 실행")
        val bottomSheet = BottomAlarmSend(callback = {
            when (it) {
                0 -> {
                    // 예약 보내가 버튼 클릭
                    reservationAlarmSend()
                }
                1 -> {
                    /**
                     * 푸쉬알림을 발송했습니다!
                     * fcm API!!
                     * */
                    viewModel.onAlarmPushClicked()
                }
            }
        })
        bottomSheet.show(requireActivity().supportFragmentManager, TAG)
    }

    private fun reservationAlarmSend() {
        val bottomSheet = BottomAlarmReservationPicker(callback = {
            /**
             * 예약 푸쉬알림을 발송했습니다!
             * fcm API!!
             * */
            Log.d("ttt send at", it.toString())
            viewModel.onReservationAlarmPushClicked(it.toString())
        })
        bottomSheet.show(requireActivity().supportFragmentManager, TAG)
    }


    private fun updateReservationAlarmSend(reservationId: Int) {
        val bottomSheet = BottomAlarmReservationPicker(callback = {
            /**
             * 예약 푸쉬알림을 업데이트 했습니다!
             * fcm API!!
             * */
            viewModel.onUpdateReservationAlarmPushClicked(reservationId, it.toString())
        })
        bottomSheet.show(requireActivity().supportFragmentManager, TAG)
    }

    private fun alarmNoReservationDialog() {
        val res = AlertDialogModel(
            title = "푸시알림을 예약할 수 없어요.",
            description = "현재 예약된 푸시알림 발송\n 혹은 삭제 후에 예약해주세요.",
            positiveContents = "닫기",
            negativeContents = "닫기"
        )
        val dialog: DefaultGrayAlertDialog = DefaultGrayAlertDialog(
            alertDialogModel = res,
            clickToNegative = {}
        )
        dialog.show(childFragmentManager, TAG)
    }

    private fun addImageBottomSheet() {
        binding.editTextMessage.messageTextOnFocusChangeListener(
            requireContext(),
            binding.linearLayoutEditText
        )
        requireActivity().hideKeyboard()
        binding.editTextMessage.clearFocus()
        binding.editTextTitle.clearFocus()

        val dialog = BottomImageAdd {
            if (it) getGalleryImage()
            else getCaptureImage()
        }
        dialog.show(childFragmentManager, TAG)
    }

    private fun initRegisterForActivityResult() {
        requestUpdateProfile =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                val isUpdateProfile = activityResult.data?.getBooleanExtra(
                    KnockKnockIntent.RESULT_KEY_UPDATE_PROFILE,
                    false
                ) ?: false
                Log.d("ttt", isUpdateProfile.toString())
                if (true) {
                    val intent = activityResult.data
                    if (intent != null) {
                        val uri = intent.data
//                    val file = uriToFile(uri!!,requireContext())
//                    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
//                    val requestBody = MultipartBody.Part.createFormData("file", file.name, requestFile)
//
//                    //TODO : 이후에 설명도 입력한걸로 넣기
//                    val nicknamePart: MultipartBody.Part = MultipartBody.Part.createFormData("description", "테스트 설명")

                        Log.d("ttt uri ", intent.toString())
                        // Update Profile API
                        Glide.with(this).load(uri).into(binding.alarmCreateImg)
                        viewModel.onImageUriChecked(uri.toString())
                    }
                }
            }
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
            Intent(ACTION_IMAGE_CAPTURE)
        }
        requestUpdateProfile.launch(intent)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditText() {

        binding.editTextMessage.messageTextOnFocusChangeListener(
            requireContext(),
            binding.linearLayoutEditText
        )
        binding.alarmCreateMain.setOnTouchListener { _, _ ->
            requireActivity().hideKeyboard()
            binding.editTextMessage.clearFocus()
            binding.editTextTitle.clearFocus()
            false
        }

        binding.editTextTitle.titleTextOnFocusChangeListener(binding.editTextTitleImg)
        binding.editTextTitle.setOnKeyListener { view, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KEYCODE_ENTER) {
                // 엔터 눌렀을때 행동
                requireActivity().hideKeyboard()
                binding.editTextTitle.clearFocus()
                true

            } else false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setOnTouchListenerEditText() {
        binding.editTextMessage.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.scrollView.requestDisallowInterceptTouchEvent(true)
                    binding.scrollView.requestDisallowInterceptTouchEvent(true)
                }
                MotionEvent.ACTION_UP -> binding.scrollView.requestDisallowInterceptTouchEvent(true)
                MotionEvent.ACTION_MOVE -> binding.scrollView.requestDisallowInterceptTouchEvent(
                    true
                )
            }
            false
        })
    }

    override fun initDataBinding() {}

    override fun initAfterBinding() {}

    private fun initToolbar() {
        with(binding.toolbar) {
        }
    }

    override fun onResume() {
//        viewModel.initText()
        super.onResume()
    }
}