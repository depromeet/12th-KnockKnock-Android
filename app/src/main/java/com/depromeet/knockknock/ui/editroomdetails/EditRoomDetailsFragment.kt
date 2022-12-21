package com.depromeet.knockknock.ui.editroomdetails

import com.depromeet.knockknock.R

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentEditRoomDetailsBinding

import com.depromeet.knockknock.ui.friendlist.bottom.BottomFriendMore
import com.depromeet.knockknock.ui.friendlist.bottom.FriendMoreType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class EditRoomDetailsFragment : BaseFragment<FragmentEditRoomDetailsBinding, EditRoomDetailsViewModel>(R.layout.fragment_edit_room_details) {

    private val TAG = "FriendListFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_edit_room_details

    override val viewModel : EditRoomDetailsViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initAdapter()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when(it) {
                    //is EditRoomDetailsNavigationAction.NavigateToLink -> {}
                    //is FriendListNavigationAction.NavigateToFriendMore -> { moreFriendPopUp(userIdx = it.userIdx) }
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

//    private fun initToolbar() {
//        with(binding.toolbar) {
//            this.title = getString(R.string.friend_list_title)
//            // 뒤로가기 버튼
//            this.setNavigationIcon(R.drawable.ic_allow_back)
//            this.setNavigationOnClickListener { navController.popBackStack() }
//        }
//    }

    private fun moreFriendPopUp(userIdx: Int) {
        val dialog: BottomFriendMore = BottomFriendMore {
            when(it) {
                is FriendMoreType.Black -> {}
                is FriendMoreType.Delete -> {}
            }
        }
        dialog.show(requireActivity().supportFragmentManager, TAG)
    }

    private fun initAdapter() {
        //binding.friendRecycler.adapter = FriendListAdapter(viewModel)
    }

//    @SuppressLint("ClickableViewAccessibility")
//    private fun initEditText() {
//        binding.searchEditText.customOnFocusChangeListener(requireContext())
//        binding.friendListMain.setOnTouchListener { _, _ ->
//            requireActivity().hideKeyboard()
//            binding.searchEditText.clearFocus()
//            false
//        }
//    }
}
