package com.depromeet.knockknock.ui.friendlist

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentFriendListBinding
import com.depromeet.knockknock.ui.friendlist.adapter.FriendListAdapter
import com.depromeet.knockknock.ui.friendlist.bottom.BottomFriendMore
import com.depromeet.knockknock.ui.friendlist.bottom.FriendMoreType
import com.depromeet.knockknock.util.customOnFocusChangeListener
import com.depromeet.knockknock.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class FriendListFragment : BaseFragment<FragmentFriendListBinding, FriendListViewModel>(R.layout.fragment_friend_list) {

    private val TAG = "FriendListFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_friend_list

    override val viewModel : FriendListViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val adapter by lazy { FriendListAdapter(viewModel) }

    override fun onResume() {
        super.onResume()
        viewModel.getFriends()
    }

    override fun initStartView() {
        binding.apply {
            this.viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initAdapter()
        initEditText()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when(it) {
                    is FriendListNavigationAction.NavigateToLink -> {}
                    is FriendListNavigationAction.NavigateToFriendMore -> { moreFriendPopUp(userIdx = it.userIdx) }
                    is FriendListNavigationAction.NavigateToDeleteSuccess -> viewModel.getFriends()
                    is FriendListNavigationAction.NavigateToDeclareSuccess -> viewModel.getFriends()
                    is FriendListNavigationAction.NavigateToAddFriends -> navigate(FriendListFragmentDirections.actionFriendListFragmentToAddFriendFragment())
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.friendList.collectLatest {
                adapter.submitList(it)
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.title = getString(R.string.friend_list_title)
            // 뒤로가기 버튼
            this.setNavigationIcon(R.drawable.ic_allow_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun moreFriendPopUp(userIdx: Int) {
        val dialog: BottomFriendMore = BottomFriendMore {
            when(it) {
                is FriendMoreType.Black -> viewModel.declareFriend(id = userIdx)
                is FriendMoreType.Delete -> viewModel.deleteFriend(id = userIdx)
            }
        }
        dialog.show(requireActivity().supportFragmentManager, TAG)
    }

    private fun initAdapter() {
        binding.friendRecycler.adapter = adapter
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditText() {
        binding.searchEditText.customOnFocusChangeListener(requireContext())
        binding.friendListMain.setOnTouchListener { _, _ ->
            requireActivity().hideKeyboard()
            binding.searchEditText.clearFocus()
            false
        }
    }
}
