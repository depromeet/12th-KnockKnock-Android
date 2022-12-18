package com.depromeet.knockknock.ui.preview

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentPreviewBinding
import com.depromeet.knockknock.ui.alarmcreate.AlarmCreateFragmentDirections
import com.depromeet.knockknock.ui.alarmcreate.AlarmCreateNavigationAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class PreviewFragment :
    BaseFragment<FragmentPreviewBinding, PreviewViewModel>(R.layout.fragment_preview) {

    private val TAG = "PreviewFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_preview
    private val navController by lazy { findNavController() }

    override val viewModel: PreviewViewModel by viewModels()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        val args: PreviewFragmentArgs by navArgs()
        viewModel.previewTitleEvent.value = args.title
        viewModel.previewMessageEvent.value = args.message
        setupEvent()
    }

    private fun setupEvent() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is PreviewNavigationAction.NavigateToBackStack -> navController.popBackStack()
                }
            }
        }
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }

}