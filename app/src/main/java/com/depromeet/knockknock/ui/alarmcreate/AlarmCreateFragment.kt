package com.depromeet.knockknock.ui.alarmcreate

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentAlarmCreateBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import androidx.navigation.fragment.findNavController
import com.depromeet.knockknock.util.KnockKnockIntent
import com.depromeet.knockknock.util.uriToFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

@AndroidEntryPoint
class AlarmCreateFragment :
    BaseFragment<FragmentAlarmCreateBinding, AlarmCreateViewModel>(R.layout.fragment_alarm_create) {

    private val TAG = "AlarmCreateFragment"

    private lateinit var requestUpdateProfile: ActivityResultLauncher<Intent>

    override val layoutResourceId: Int
        get() = R.layout.fragment_alarm_create
    private val navController by lazy { findNavController() }

    override val viewModel: AlarmCreateViewModel by viewModels()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent

        initEditText()
        initRegisterForActivityResult()
        initToolbar()
        setupEvent()
        setOnTouchListenerEditText()
    }

    private fun setupEvent() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is AlarmCreateNavigationAction.NavigateToAddImage -> addImageBottomSheet()
                    is AlarmCreateNavigationAction.NavigateToAlarmSend -> alarmSend()
                }
            }
        }
    }

    private fun alarmSend() {
        val bottomSheet = BottomAlarmSend(callback = {
            when (it) {
                0 -> {
                    // 예약 보내가 버튼 클릭
                    alarmReservationSend()
                }
                1 -> {
                    /**
                     * 푸쉬알림을 발송했습니다!
                     * fcm API!!
                     * */

                }
            }
        })
        bottomSheet.show(requireActivity().supportFragmentManager, TAG)
    }

    private fun alarmReservationSend() {
        val bottomSheet = BottomAlarmReservationPicker(callback = {
            /**
             * 예약 푸쉬알림을 발송했습니다!
             * fcm API!!
             * */
            Log.d("ttt", it.toString())
        })
        bottomSheet.show(requireActivity().supportFragmentManager, TAG)
    }

    private fun addImageBottomSheet() {
        val dialog = BottomImageAdd {
            if(it) getGalleryImage()
            else getCaptureImage()
        }
        dialog.show(childFragmentManager, TAG)
    }

    private fun initRegisterForActivityResult() {
        requestUpdateProfile = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val isUpdateProfile = activityResult.data?.getBooleanExtra(KnockKnockIntent.RESULT_KEY_UPDATE_PROFILE, false) ?: false
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
                    Glide.with(this).load(uri).into(binding.imgLoad)
                    viewModel.onImageStateChecked()
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
        binding.editTextMessage.customOnFocusChangeListener(requireContext(), binding.linearLayoutEditText)
        binding.alarmCreateMain.setOnTouchListener { _, _ ->
            requireActivity().hideKeyboard()
            binding.editTextMessage.clearFocus()
            false
        }

//        binding.scrollView.setOnTouchListener { _, _ ->
//            requireActivity().hideKeyboard()
//            binding.editTextMessage.clearFocus()
//            false
//        }
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
            this.setNavigationIcon(R.drawable.ic_baseline_close_24)
            this.setNavigationOnClickListener { navController.popBackStack() }
            this.title = "취준생을 위한 방"
        }
    }
}

/**
 * 일단 임시적으로 만들었습니다 pr 하고 머지한 후에 삭제하겠습니다.
 *
 * */
fun Activity.hideKeyboard() {
    if (this.currentFocus != null) {
        // 프래그먼트기 때문에 getActivity() 사용
        val inputManager: InputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            this.currentFocus!!.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}