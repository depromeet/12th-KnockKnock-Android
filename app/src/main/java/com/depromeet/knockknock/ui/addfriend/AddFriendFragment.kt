package com.depromeet.knockknock.ui.addfriend

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.AlertDialogModel
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.base.DefaultYellowAlertDialog
import com.depromeet.knockknock.databinding.FragmentAddFriendBinding
import com.depromeet.knockknock.databinding.FragmentFriendListBinding
import com.depromeet.knockknock.ui.addfriend.adapter.FriendAddAdapter
import com.depromeet.knockknock.ui.friendlist.adapter.FriendListAdapter
import com.depromeet.knockknock.ui.friendlist.bottom.BottomFriendMore
import com.depromeet.knockknock.ui.friendlist.bottom.FriendMoreType
import com.depromeet.knockknock.util.customOnFocusChangeListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class AddFriendFragment : BaseFragment<FragmentAddFriendBinding, AddFriendViewModel>(R.layout.fragment_add_friend) {

    private val TAG = "FriendListFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_add_friend

    override val viewModel : AddFriendViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initAdapter()
        binding.searchEditText.customOnFocusChangeListener(requireContext())
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when(it) {
                    is AddFriendNavigationAction.NavigateToAddFriend -> { addFriendPopUp(it.userIdx) }
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.title = getString(R.string.friend_search_title)
            // 뒤로가기 버튼
            this.setNavigationIcon(R.drawable.ic_allow_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun initAdapter() {
        binding.friendRecycler.adapter = FriendAddAdapter(viewModel)
    }

    private fun addFriendPopUp(userIdx: Int) {
        val res = AlertDialogModel(
            title = getString(R.string.add_friend_popup_title),
            description = null,
            positiveContents = getString(R.string.add_friend_popup_positive),
            negativeContents = getString(R.string.no)
        )
        val dialog: DefaultYellowAlertDialog = DefaultYellowAlertDialog(
            alertDialogModel = res,
            clickToPositive = {
                // 친구 추가 API
                userIdx},
            clickToNegative = {}
        )
        dialog.show(requireActivity().supportFragmentManager, TAG)
    }
}
