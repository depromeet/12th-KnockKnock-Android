package com.depromeet.knockknock.ui.alarmcreate

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
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
import kotlinx.coroutines.launch

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
                }
            }
        }
    }

    private val readImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->

        Log.d("ttt", uri.toString())
        if (uri != null) {
            Glide.with(this)
                .load(uri)
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
            this.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            this.inflateMenu(R.menu.alarm_create_toolbar_menu)
            this.title = "취준생을 위한 방"

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_completion -> {
                Log.d("ttt", "완료 버튼 누르기 완료")
                true
            }
            R.id.action_preview -> {
                Log.d("ttt", "미리보기 버튼 누르기 완료")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}