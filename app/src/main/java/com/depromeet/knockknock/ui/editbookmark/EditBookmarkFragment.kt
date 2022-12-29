package com.depromeet.knockknock.ui.editbookmark

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.AlertDialogModel
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.base.DefaultYellowAlertDialog
import com.depromeet.knockknock.databinding.FragmentEditBookmarkBinding
import com.depromeet.knockknock.ui.editbookmark.adapter.EditBookmarkAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class EditBookmarkFragment : BaseFragment<FragmentEditBookmarkBinding, EditBookmarkViewModel>(R.layout.fragment_edit_bookmark) {

    private val TAG = "BookmarkFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_edit_bookmark

    override val viewModel : EditBookmarkViewModel by viewModels()
    private val navController: NavController by lazy { findNavController() }
    private val args: EditBookmarkFragmentArgs by navArgs()
    private val adapter by lazy { EditBookmarkAdapter(viewModel) }

    override fun initStartView() {
        binding.apply {
            this.viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent

        viewModel.periodClicked.value = args.periodClicked
        viewModel.roomClicked.value = args.roomClicked.asList()
        viewModel.getStroage()

        initToolbar()
        initAdapter()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when(it) {
                    is EditBookmarkNavigationAction.NavigateToEditDialog -> deleteDialog()
                    is EditBookmarkNavigationAction.NavigateToDeleteComplete -> navController.popBackStack()
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
        binding.bookmarkRecycler.adapter = adapter
    }

    private fun deleteDialog() {
        val res = AlertDialogModel(
            title = requireContext().getString(R.string.delete_dialog_title),
            description = requireContext().getString(R.string.delete_dialog_description),
            positiveContents = requireContext().getString(R.string.delete_short),
            negativeContents = requireContext().getString(R.string.no)
        )
        val dialog = DefaultYellowAlertDialog(
            alertDialogModel = res,
            clickToPositive = { viewModel.deleteStroage() },
            clickToNegative = {}
        )
        dialog.show(childFragmentManager, TAG)
    }
}
