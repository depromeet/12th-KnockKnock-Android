package com.depromeet.knockknock.ui.editbookmark

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.AlertDialogModel
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.base.DefaultYellowAlertDialog
import com.depromeet.knockknock.databinding.FragmentBookmarkBinding
import com.depromeet.knockknock.databinding.FragmentEditBookmarkBinding
import com.depromeet.knockknock.ui.bookmark.BookmarkNavigationAction
import com.depromeet.knockknock.ui.bookmark.BookmarkViewModel
import com.depromeet.knockknock.ui.bookmark.adapter.BookmarkAdapter
import com.depromeet.knockknock.ui.bookmark.bottom.BottomPeriodFilter
import com.depromeet.knockknock.ui.bookmark.bottom.BottomRoomFilter
import com.depromeet.knockknock.ui.bookmark.model.Bookmark
import com.depromeet.knockknock.ui.bookmark.model.Room
import com.depromeet.knockknock.ui.editbookmark.adapter.EditBookmarkAdapter
import com.depromeet.knockknock.ui.editbookmark.model.EditBookmark
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class EditBookmarkFragment : BaseFragment<FragmentEditBookmarkBinding, EditBookmarkViewModel>(R.layout.fragment_edit_bookmark) {

    private val TAG = "BookmarkFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_edit_bookmark

    override val viewModel : EditBookmarkViewModel by viewModels()
    private val navController: NavController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initAdapter()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when(it) {
                    is EditBookmarkNavigationAction.NavigateToEditComplete -> deleteDialog()
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.title = getString(R.string.bookmark_title)
            // 뒤로가기 버튼
            this.setNavigationIcon(R.drawable.ic_allow_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun initAdapter() {
        val testList = listOf<EditBookmark>()

        val adapter = EditBookmarkAdapter(viewModel)
        adapter.submitList(testList)
        binding.bookmarkRecycler.adapter = adapter
    }

    private fun deleteDialog() {
        val res = AlertDialogModel(
            title = "",
            description = "",
            positiveContents = "",
            negativeContents = ""
        )
        val dialog = DefaultYellowAlertDialog(
            alertDialogModel = res,
            clickToPositive = {},
            clickToNegative = {}
        )
        dialog.show(childFragmentManager, TAG)
    }
}
