package com.depromeet.knockknock.ui.alarmcreate

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentAlarmCreateBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import androidx.navigation.fragment.findNavController

@AndroidEntryPoint
class AlarmCreateFragment :
    BaseFragment<FragmentAlarmCreateBinding, AlarmCreateViewModel>(R.layout.fragment_alarm_create) {

    private val TAG = "AlarmCreateFragment"

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

        initToolbar()
        setupEvent()
        setOnTouchListenerEditText()
        countEditTextMessage()
    }

    private fun setupEvent() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is AlarmCreateNavigationAction.NavigateToGallery -> readImage.launch("image/*")
                    is AlarmCreateNavigationAction.NavigateToAlarmSend -> alarmSend()
                }
            }
        }
    }

    private fun alarmSend() {
        val bottomSheet = BottomAlarmSend(
            callback = {
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
            }
        )
        bottomSheet.show(requireActivity().supportFragmentManager, TAG)
    }

    private fun alarmReservationSend() {
        val bottomSheet = BottomAlarmReservationPicker(
            callback = {
                /**
                 * 예약 푸쉬알림을 발송했습니다!
                 * fcm API!!
                 * */
                Log.d("ttt", it.toString())
            }
        )
        bottomSheet.show(requireActivity().supportFragmentManager, TAG)
    }


    private val readImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            Glide.with(this)
                .load(it)
                .into(binding.imgLoad)

            lifecycleScope.launchWhenStarted {
                viewModel.imgState.emit(true)
            }
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

    private fun countEditTextMessage() {
        lifecycleScope.launchWhenStarted {
            viewModel.editTextMessageCountEvent.collectLatest {
                binding.editTextCount.text = "$it/200"

                if (it == 0) binding.editTextCount.text =
                    textChangeColor(binding.editTextCount, "#ff0000", 0, 1)
            }
        }
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.setNavigationIcon(R.drawable.ic_baseline_close_24)
            this.setNavigationOnClickListener { navController.popBackStack() }
            this.title = "취준생을 위한 방"
        }
    }
}