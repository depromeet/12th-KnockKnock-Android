package com.depromeet.knockknock.ui.makers

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentMakersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MakersFragment :
    BaseFragment<FragmentMakersBinding, MakersViewModel>(R.layout.fragment_makers) {

    private val TAG = "MakersFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_makers
    private val navController by lazy { findNavController() }

    override val viewModel: MakersViewModel by viewModels()

    override fun initStartView() {
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
            this.title = "만든 사람들"

            // 뒤로가기 버튼
            this.setNavigationIcon(com.depromeet.knockknock.R.drawable.ic_allow_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    override fun initAfterBinding() {
    }
}