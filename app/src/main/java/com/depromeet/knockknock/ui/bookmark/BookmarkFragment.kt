package com.depromeet.knockknock.ui.bookmark

import android.util.Log
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
                    is BookmarkNavigationAction.NavigateToBookmarkEdit -> {}
                    is BookmarkNavigationAction.NavigateToBookmarkFilterReset -> {}
                    is BookmarkNavigationAction.NavigateToBookmarkFilterRoom -> roomFilter()
                    is BookmarkNavigationAction.NavigateToBookmarkFilterPeriod -> periodFilter()
                    is BookmarkNavigationAction.NavigateToReaction -> {}
                }
            }
        }
    }

    override fun initAfterBinding() {
        val adapter = BookmarkAdapter(viewModel)
        binding.bookmarkRecycler.adapter = adapter
        val test1 = Bookmark(
            bookmarkId = 1,
            userIdx = 1,
            userImg = "http://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
            userName = "라이언",
            time = "2022-12-09",
            contents = "테스트테스트테스트",
            contentsImg = "http://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
            roomName = "테스트1호점",
            reactionContents = "",
            reactionCount = 0,
        )
        val test2 = Bookmark(
            bookmarkId = 1,
            userIdx = 1,
            userImg = "http://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
            userName = "라이언",
            time = "2022-12-09",
            contents = "테스트테스트테스트",
            contentsImg = "http://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
            roomName = "테스트1호점",
            reactionContents = "",
            reactionCount = 0,
        )
        val test3 = Bookmark(
            bookmarkId = 1,
            userIdx = 1,
            userImg = "http://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
            userName = "라이언",
            time = "2022-12-09",
            contents = "테스트테스트테스트",
            contentsImg = "http://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
            roomName = "테스트1호점",
            reactionContents = "",
            reactionCount = 0,
        )
        val testList = listOf(test1, test2, test3)
        adapter.submitList(testList)
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
        val bottomSheet = BottomRoomFilter(
            roomList = emptyList(),
            beforeClickedRoom = emptyList()
        ) { clickedRoom ->
            viewModel.setRoomFilter(clickedRoom.size)
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
