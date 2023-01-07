package com.depromeet.knockknock.ui.aloneroominvitefriend

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentAloneRoomInviteFriendBinding
import com.depromeet.knockknock.databinding.FragmentInviteFriendToRoomBinding
import com.depromeet.knockknock.ui.aloneroominvitefriend.adapter.AloneRoomInviteFriendAdapter
import com.depromeet.knockknock.ui.invitefriendtoroom.adapter.InviteFriendToRoomAdapter
import com.depromeet.knockknock.util.customOnFocusChangeListener
import com.depromeet.knockknock.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class AloneRoomInviteFriendFragment :
    BaseFragment<FragmentAloneRoomInviteFriendBinding, AloneRoomInviteFriendViewModel>(R.layout.fragment_alone_room_invite_friend) {

    private val TAG = "InviteFriendToRoomFragment"

    private val args: AloneRoomInviteFriendFragmentArgs by navArgs()


    override val layoutResourceId: Int
        get() = R.layout.fragment_alone_room_invite_friend

    override val viewModel: AloneRoomInviteFriendViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val inviteFriendToRoomAdapter by lazy{AloneRoomInviteFriendAdapter(viewModel)}

    override fun initStartView() {

        viewModel.group_category_id.value = args.groupCategoryId
        viewModel.backgroundImg.value =args.groupBackgroundPath
        viewModel.inputRoomDescription.value =args.groupDescription
        viewModel.inputRoomName.value =args.groupTitle
        viewModel.isRoomUnpublic.value = !args.groupPublicAccess
        viewModel.thumbnailImg.value = args.groupThumbnailPath

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
                    is AloneRoomInviteFriendNavigationAction.NavigateToGeneratedRoom ->
                    {}                }
            }
        }

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
