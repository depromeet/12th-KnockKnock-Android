package com.depromeet.knockknock.ui.invitefriendtoroom

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentInviteFriendToRoomBinding
import com.depromeet.knockknock.ui.friendlist.model.User
import com.depromeet.knockknock.ui.invitefriendtoroom.adapter.InviteFriendToRoomAdapter
import com.depromeet.knockknock.util.customOnFocusChangeListener
import com.depromeet.knockknock.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class InviteFriendToRoomFragment :
    BaseFragment<FragmentInviteFriendToRoomBinding, InviteFriendToRoomViewModel>(R.layout.fragment_invite_friend_to_room) {

    private val TAG = "InviteFriendToRoomFragment"

    private val args: InviteFriendToRoomFragmentArgs by navArgs()


    override val layoutResourceId: Int
        get() = R.layout.fragment_invite_friend_to_room

    override val viewModel: InviteFriendToRoomViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val inviteFriendToRoomAdapter by lazy{InviteFriendToRoomAdapter(viewModel)}

    override fun initStartView() {
        viewModel.groupId.value = args.groupId
        binding.apply {
            this.myviewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initAdpater()
        initEditText()
    }

    override fun initDataBinding() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.onSaveSuccess.collectLatest {
                navController.popBackStack()
            }
        }

//        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
//            viewModel.navigationHandler.collectLatest {
//                when (it) {
//                    is InviteFriendToRoomNavigationAction.NavigateToInviteComplete ->
//                    {}                }
//            }
//        }

        lifecycleScope.launchWhenStarted {
            viewModel.friendList.collectLatest {
                inviteFriendToRoomAdapter.submitList(it)
            }
        }
    }

    override fun initAfterBinding() {

    }

    private fun initAdpater(){
        binding.friendRecycler.adapter = inviteFriendToRoomAdapter
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.title = getString(R.string.friend_search_title)
            // 뒤로가기 버튼
            this.setNavigationIcon(R.drawable.ic_allow_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
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
