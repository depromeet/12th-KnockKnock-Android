package com.depromeet.knockknock.ui.bookmark

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentBookmarkBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class BookmarkFragment : BaseFragment<FragmentBookmarkBinding, BookmarkViewModel>(R.layout.fragment_bookmark) {

    private val TAG = "BookmarkFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_bookmark

    override val viewModel : BookmarkViewModel by viewModels()
    private val navController: NavController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when(it) {
                    is BookmarkNavigationAction.NavigateToBookmarkFilter -> {}
                    is BookmarkNavigationAction.NavigateToBookmarkEdit -> {}
                }
            }
        }
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.title = getString(R.string.bookmark_title)
            // 뒤로가기 버튼
//            this.setNavigationIcon(R.drawable.ic_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }
}
