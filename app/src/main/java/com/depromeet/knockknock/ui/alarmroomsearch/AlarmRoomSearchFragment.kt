package com.depromeet.knockknock.ui.alarmroomsearch

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentAlarmRoomSearchBinding
import com.depromeet.knockknock.ui.alarmroomsearch.adapter.AlarmRoomSearchAdapter
import com.depromeet.knockknock.ui.alarmroomsearch.adapter.PopularCategoryAdapter
import com.depromeet.knockknock.ui.alarmroomtab.AlarmRoomTabFragmentDirections
import com.depromeet.knockknock.ui.alarmroomtab.bottom.BottomMakeRoom
import com.depromeet.knockknock.ui.alarmroomtab.bottom.MakeRoomType
import com.depromeet.knockknock.util.customOnFocusChangeListener
import com.depromeet.knockknock.util.hideKeyboard
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class AlarmRoomSearchFragment : BaseFragment<FragmentAlarmRoomSearchBinding, AlarmRoomSearchViewModel>(R.layout.fragment_alarm_room_search) {

    private val TAG = "AlarmRoomSearchFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_alarm_room_search

    override val viewModel : AlarmRoomSearchViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val alarmRoomAdapter by lazy {AlarmRoomSearchAdapter(viewModel)}
    private val popularCategoryAdapter by lazy { PopularCategoryAdapter(viewModel) }

    override fun initStartView() {
        binding.apply {
            this.viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initEditText()
        initAdapter()
        countEditTextMessage()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when(it) {
                    is AlarmRoomSearchNavigationAction.NavigateToRoom -> { navigate(AlarmRoomSearchFragmentDirections.actionAlarmRoomSearchFragmentToAlarmRoomHistoryFragment(it.roomId)) }
                    is AlarmRoomSearchNavigationAction.NavigateToHidePopularCategory -> {}
                    is AlarmRoomSearchNavigationAction.NavigateToMakeRoom -> {makeRoomPopUp()}
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.roomList.collectLatest {
                alarmRoomAdapter.submitList(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.popularCategoryList.collectLatest {
                popularCategoryAdapter.submitList(it)
            }
        }
    }

    override fun initAfterBinding() {

        //editText 자동 포커스 및 키보드 띄우기
//        binding.searchEditText.requestFocus()
//        val imm = requireActivity().getSystemService(
//            Context.INPUT_METHOD_SERVICE
//        ) as InputMethodManager
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

    }

//    override fun onResume() {
//        super.onResume()
//        binding.searchEditText.requestFocus()
//        val imm = requireActivity().getSystemService(
//            Context.INPUT_METHOD_SERVICE
//        ) as InputMethodManager
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
//    }

    private fun initAdapter(){
        binding.alarmRoomRecycler.adapter = alarmRoomAdapter


        val flexLayoutManager = FlexboxLayoutManager(requireContext()).apply {
            this.flexDirection = FlexDirection.ROW
            this.justifyContent = JustifyContent.FLEX_START
            this.alignItems = AlignItems.FLEX_START
        }

        binding.popularCategoryRecycler.apply {
            this.adapter = popularCategoryAdapter
            this.layoutManager = flexLayoutManager
        }
    }


    private fun makeRoomPopUp() {
        val dialog: BottomMakeRoom = BottomMakeRoom {
            when(it) {
                is MakeRoomType.RoomWithFriend -> {}
                is MakeRoomType.RoomAlone -> {navigate(AlarmRoomSearchFragmentDirections.actionAlarmRoomSearchFragmentToAloneRoomMakeCategoryFragment())}
            }
        }
        dialog.show(requireActivity().supportFragmentManager, TAG)
    }




    @SuppressLint("ClickableViewAccessibility")
    private fun initEditText() {
        binding.searchEditText.customOnFocusChangeListener(requireContext())
        binding.layoutMain.setOnTouchListener { _, _ ->
            requireActivity().hideKeyboard()
            binding.searchEditText.clearFocus()
            false
        }
    }


    private fun countEditTextMessage() {
        lifecycleScope.launchWhenStarted {
            viewModel.editTextMessageCountEvent.collectLatest {
                if (it != 0) {
                    binding.popularCategoryFrame.visibility = View.GONE
                    binding.makeRoomLayout.visibility = View.VISIBLE
                    binding.alarmRoomRecycler.visibility = View.VISIBLE
                }
                else{
                    binding.popularCategoryFrame.visibility = View.VISIBLE
                    binding.makeRoomLayout.visibility = View.GONE
                    binding.alarmRoomRecycler.visibility = View.GONE
                }
            }
        }
    }
}
