package com.depromeet.knockknock.ui.invitefriendtoroom

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.AlertDialogModel
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.base.DefaultYellowAlertDialog
import com.depromeet.knockknock.databinding.FragmentInviteFriendBinding
import com.depromeet.knockknock.ui.editroomdetails.adapter.BackgroundAdapter
import com.depromeet.knockknock.ui.editroomdetails.model.Background
import com.depromeet.knockknock.ui.friendlist.adapter.FriendListAdapter
import com.depromeet.knockknock.ui.friendlist.model.User
import com.depromeet.knockknock.ui.invitefriendtoroom.adapter.InviteFriendToRoomAdapter
import com.depromeet.knockknock.util.customOnFocusChangeListener
import com.depromeet.knockknock.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class InviteFriendToRoomFragment :
    BaseFragment<FragmentInviteFriendBinding, InviteFriendToRoomViewModel>(R.layout.fragment_invite_friend) {

    private val TAG = "InviteFriendToRoomFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_invite_friend

    override val viewModel: InviteFriendToRoomViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.viewmodel = viewmodel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initEditText()
    }

    override fun initDataBinding() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.onSaveSuccess.collectLatest {
                navController.popBackStack()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when (it) {
//                    is InviteFriendToRoomNavigationAction.NavigateToCheckFriend -> {
//                        addFriendsToInvite(it.userIdx)
//                    }
                    is InviteFriendToRoomNavigationAction.NavigateToInviteComplete ->
                    {}                }
            }
        }
    }

    override fun initAfterBinding() {
        val inviteFriendToRoomAdapter = InviteFriendToRoomAdapter(viewModel)
        binding.friendRecycler.adapter = inviteFriendToRoomAdapter
        val test1 = User(
            userIdx = 1,
            userName = "이영준",
            userImg = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23"
        )

        val test2 = User(
            userIdx = 2,
            userName = "이영준",
            userImg = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23"
        )

        val test3 = User(
            userIdx = 3,
            userName = "이영준",
            userImg = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23"
        )

            val friendList = listOf(test1, test2, test3)

        inviteFriendToRoomAdapter.submitList(friendList)

    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.title = getString(R.string.friend_search_title)
            // 뒤로가기 버튼
            this.setNavigationIcon(R.drawable.ic_allow_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun addFriendsToInvite(userIdx: Int) {

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditText() {
        binding.searchEditText.customOnFocusChangeListener(requireContext())
        binding.friendListInviteMain.setOnTouchListener { _, _ ->
            requireActivity().hideKeyboard()
            binding.searchEditText.clearFocus()
            false
        }
    }

}
