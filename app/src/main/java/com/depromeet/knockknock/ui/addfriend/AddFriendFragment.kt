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
import com.depromeet.knockknock.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class AddFriendFragment : BaseFragment<FragmentAddFriendBinding, AddFriendViewModel>(R.layout.fragment_add_friend) {

    private val TAG = "FriendListFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_add_friend

    override val viewModel : AddFriendViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val adapter by lazy { FriendAddAdapter(viewModel) }

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
                    is AddFriendNavigationAction.NavigateToAddFriend -> { addFriendPopUp(it.userIdx, nickname = it.nickname) }
                    is AddFriendNavigationAction.NavigateToAddSuccess -> toastMessage(it.nickname+"님께 친구 요청이 완료 되었습니다!")
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.userList.collectLatest {
                adapter.submitList(it)
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
        binding.friendRecycler.adapter = adapter
    }

    private fun addFriendPopUp(userIdx: Int, nickname: String) {
        val res = AlertDialogModel(
            title = nickname+getString(R.string.add_friend_popup_title),
            description = null,
            positiveContents = getString(R.string.add_friend_popup_positive),
            negativeContents = getString(R.string.no)
        )
        val dialog: DefaultYellowAlertDialog = DefaultYellowAlertDialog(
            alertDialogModel = res,
            clickToPositive = { viewModel.addFriend(userIdx = userIdx, nickname = nickname) },
            clickToNegative = {}
        )
        dialog.show(requireActivity().supportFragmentManager, TAG)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditText() {
        binding.searchEditText.customOnFocusChangeListener(requireContext())
        binding.addFriendMain.setOnTouchListener { _, _ ->
            requireActivity().hideKeyboard()
            binding.searchEditText.clearFocus()
            false
        }
    }
}
