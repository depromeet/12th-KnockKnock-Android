package com.depromeet.knockknock.ui.bookmark

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentBookmarkBinding
import com.depromeet.knockknock.ui.bookmark.adapter.BookmarkAdapter
import com.depromeet.knockknock.ui.bookmark.bottom.BottomPeriodFilter
import com.depromeet.knockknock.ui.bookmark.bottom.BottomRoomFilter
import com.depromeet.knockknock.ui.bookmark.model.Bookmark
import com.depromeet.knockknock.ui.bookmark.model.Room
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class BookmarkFragment : BaseFragment<FragmentBookmarkBinding, BookmarkViewModel>(R.layout.fragment_bookmark) {

    private val TAG = "BookmarkFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_bookmark

    override val viewModel : BookmarkViewModel by viewModels()
    private val navController: NavController by lazy { findNavController() }

    private val adapter by lazy { BookmarkAdapter(viewModel) }

    override fun onResume() {
        super.onResume()
        viewModel.getStroage()
    }

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
                when(it) {
                    is BookmarkNavigationAction.NavigateToBookmarkEdit ->
                        navigate(BookmarkFragmentDirections.actionBookmarkFragmentToEditBookmarkFragment(
                            viewModel.roomClicked.value.toIntArray(),
                            viewModel.periodClicked.value
                        ))
                    is BookmarkNavigationAction.NavigateToBookmarkFilterReset -> {}
                    is BookmarkNavigationAction.NavigateToBookmarkFilterRoom -> roomFilter()
                    is BookmarkNavigationAction.NavigateToBookmarkFilterPeriod -> periodFilter()
                    is BookmarkNavigationAction.NavigateToReaction -> {}
                    is BookmarkNavigationAction.NavigateToNotificationDetail -> {}
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.bookmarkList.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun initAfterBinding() {
        binding.bookmarkRecycler.adapter = adapter
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.title = getString(R.string.bookmark_title)
            // 뒤로가기 버튼
            this.setNavigationIcon(R.drawable.ic_allow_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun roomFilter() {
        val bottomSheet = BottomRoomFilter { clickedRoom ->
            viewModel.setRoomFilter(clickedRoom)
        }
        bottomSheet.show(requireActivity().supportFragmentManager, TAG)
    }

    private fun periodFilter() {
        val bottomSheet = BottomPeriodFilter(
            period = viewModel.periodClicked.value
        ) { clickedPeriod ->
            viewModel.setPeriodFilter(clickedPeriod)
        }
        bottomSheet.show(requireActivity().supportFragmentManager, TAG)
    }
}
