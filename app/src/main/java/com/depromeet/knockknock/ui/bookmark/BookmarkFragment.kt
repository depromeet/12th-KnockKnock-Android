package com.depromeet.knockknock.ui.bookmark

import androidx.fragment.app.viewModels
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentBookmarkBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BookmarkFragment : BaseFragment<FragmentBookmarkBinding, BookmarkViewModel>(R.layout.fragment_bookmark) {

    private val TAG = "BookmarkFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_bookmark

    override val viewModel : BookmarkViewModel by viewModels()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}
