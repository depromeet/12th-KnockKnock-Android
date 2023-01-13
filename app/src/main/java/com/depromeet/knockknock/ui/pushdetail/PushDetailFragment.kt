package com.depromeet.knockknock.ui.pushdetail

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentPushDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class PushDetailFragment :
    BaseFragment<FragmentPushDetailBinding, PushDetailViewModel>(R.layout.fragment_push_detail) {

    private val TAG = "PushDetailFragment"

    private val args: PushDetailFragmentArgs by navArgs()

    override val layoutResourceId: Int
        get() = R.layout.fragment_push_detail
    private val navController by lazy { findNavController() }

    override val viewModel: PushDetailViewModel by viewModels()

    override fun initStartView() {
        viewModel.groupId.value = args.groupId
        Log.d(TAG, "initStartView: ${viewModel.groupId.value}")

        binding.apply {
            this.viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when (it) {
                }
            }
        }
    }

    private fun initToolbar() {
        with(binding.toolbarMakers) {
            this.title = viewModel.title.value

            // 뒤로가기 버튼
            this.setNavigationIcon(com.depromeet.knockknock.R.drawable.ic_allow_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    override fun initAfterBinding() {
    }
}