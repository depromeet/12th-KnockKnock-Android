package com.depromeet.knockknock.ui.createroomwithfriend

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentCreateRoomWithFriendBinding
import com.depromeet.knockknock.databinding.FragmentInviteFriendToRoomBinding
import com.depromeet.knockknock.ui.invitefriendtoroom.adapter.InviteFriendToRoomAdapter
import com.depromeet.knockknock.util.customOnFocusChangeListener
import com.depromeet.knockknock.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CreateRoomWithFriendFragment :
    BaseFragment<FragmentCreateRoomWithFriendBinding, CreateRoomWithFriendViewModel>(R.layout.fragment_create_room_with_friend) {

    private val TAG = "CreateRoomWithFriendFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_create_room_with_friend

    override val viewModel: CreateRoomWithFriendViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val createRoomWithFriendAdapter by lazy{ CreateRoomWithFriendAdapter(viewModel) }

    override fun initStartView() {
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

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when (it) {
                    is CreateRoomWithFriendNavigationAction.NavigateToCreateComplete -> {  }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.friendList.collectLatest {
                createRoomWithFriendAdapter.submitList(it)
            }
        }
    }

    override fun initAfterBinding() {

    }

    private fun initAdpater(){
        binding.friendRecycler.adapter = createRoomWithFriendAdapter
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.title = getString(R.string.invite_friend)
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
