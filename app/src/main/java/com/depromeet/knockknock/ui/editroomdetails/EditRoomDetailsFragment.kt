package com.depromeet.knockknock.ui.editroomdetails

import android.annotation.SuppressLint
import com.depromeet.knockknock.R

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentEditRoomDetailsBinding
import com.depromeet.knockknock.ui.editroomdetails.adapter.BackgroundAdapter
import com.depromeet.knockknock.ui.editroomdetails.model.Background
import com.depromeet.knockknock.ui.register.textChangeColor
import com.depromeet.knockknock.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class EditRoomDetailsFragment :
    BaseFragment<FragmentEditRoomDetailsBinding, EditRoomDetailsViewModel>(R.layout.fragment_edit_room_details) {

    private val TAG = "FriendListFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_edit_room_details

    override val viewModel: EditRoomDetailsViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initEditText()
        countRoomDescription()
        countRoomName()

    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when (it) {
                    //is EditRoomDetailsNavigationAction.NavigateToLink -> {}
                    //is FriendListNavigationAction.NavigateToFriendMore -> { moreFriendPopUp(userIdx = it.userIdx) }
                }
            }
        }
    }

    override fun initAfterBinding() {
        val adapter = BackgroundAdapter(viewModel)
        binding.backgroundRecycler.adapter = adapter
        val test1 = Background(
            backgroundId = 1,
            backgroundSrc = "https://t1.daumcdn.net/cfile/tistory/996333405A8280FC23",
        )
        val test2 = Background(
            backgroundId = 2,
            backgroundSrc = "http://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
        )
        val test3 = Background(
            backgroundId = 3,
            backgroundSrc = "http://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
        )
        val test4 = Background(
            backgroundId = 4,
            backgroundSrc = "http://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg",
        )
        val testList = listOf(test1, test2, test3,test4)
        adapter.submitList(testList)
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.title = getString(R.string.detail_information)
            // 뒤로가기 버튼
            this.setNavigationIcon(R.drawable.ic_allow_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }


    private fun initAdapter() {
        //binding.friendRecycler.adapter = FriendListAdapter(viewModel)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditText() {
        //포커싱 시 검정 테두리 필요할 시 주석 해제
        //binding.userNameContents.customOnFocusChangeListener(requireContext())
        binding.editRoomDetailsMain.setOnTouchListener { _, _ ->
            requireActivity().hideKeyboard()
            binding.roomNameEditLayout.clearFocus()
            binding.roomDescriptionEditLayout.clearFocus()
            false
        }
    }


    private fun countRoomName() {
        lifecycleScope.launchWhenStarted {
            viewModel.inputRoomNameCountEvent.collectLatest {
                binding.roomNameCount.text = "$it/18"
                if (it != 0) {
                    binding.roomNameCount.text =
                        com.depromeet.knockknock.ui.register.textChangeColor(
                            binding.roomNameCount,
                            "#000000",
                            0,
                            it.toString().length
                        )
                }
                else{
                    binding.roomNameCount.text = textChangeColor(binding.roomNameCount, "#ff0000", 0, 1)
                }
            }
        }
    }

    private fun countRoomDescription() {
        lifecycleScope.launchWhenStarted {
            viewModel.inputRoomDescriptionCountEvent.collectLatest {
                binding.roomDescriptionCount.text = "$it/80"
                if (it != 0) {
                    binding.roomDescriptionCount.text =
                        com.depromeet.knockknock.ui.register.textChangeColor(
                            binding.roomDescriptionCount,
                            "#000000",
                            0,
                            it.toString().length
                        )
                }
                else{
                    binding.roomDescriptionCount.text =textChangeColor(binding.roomDescriptionCount, "#ff0000", 0, 1)
                }
            }
        }
    }
}






