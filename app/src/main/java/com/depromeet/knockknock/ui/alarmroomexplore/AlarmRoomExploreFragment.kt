package com.depromeet.knockknock.ui.alarmroomexplore

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.domain.model.Category
import com.depromeet.domain.model.GroupBriefInfo
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentAlarmRoomExploreBinding
import com.depromeet.knockknock.ui.alarmroomexplore.adapter.CategoryAdapter
import com.depromeet.knockknock.ui.alarmroomexplore.adapter.PopularRoomAdapter
import com.depromeet.knockknock.ui.alarmroomjoined.adapter.AlarmRoomJoinedAdapter
import com.depromeet.knockknock.ui.alarmroomsearch.adapter.AlarmRoomAdapter
import com.depromeet.knockknock.ui.alarmroomsearch.adapter.AlarmRoomSearchAdapter
import com.depromeet.knockknock.ui.alarmroomtab.AlarmRoomTabFragmentDirections
import com.depromeet.knockknock.util.customOnFocusChangeListener
import com.depromeet.knockknock.util.hideKeyboard
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter


@AndroidEntryPoint
class AlarmRoomExploreFragment : BaseFragment<FragmentAlarmRoomExploreBinding, AlarmRoomExploreViewModel>(R.layout.fragment_alarm_room_explore) {

    private val TAG = "AlarmRoomSearchFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_alarm_room_explore

    override val viewModel : AlarmRoomExploreViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val alarmRoomAdapter by lazy { AlarmRoomAdapter(viewModel) }
    private val categoryAdapter by lazy { CategoryAdapter(viewModel) }
    private val popularRoomAdapter by lazy { PopularRoomAdapter(viewModel) }


    override fun initStartView() {
        binding.apply {
            this.exploreviewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initEditText()
        initAdapter()

        binding.alarmRoomRecycler.adapter = alarmRoomAdapter
        alarmRoomAdapter.submitList(viewModel.roomList.value)
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when(it) {
                    is AlarmRoomExploreNavigationAction.NavigateToRoom -> { moveToRoom(roomId = it.roomId) }
                    is AlarmRoomExploreNavigationAction.NavigateToAlarmRoomSearch -> {navController.navigate(R.id.action_alarmRoomTabFragment_to_alarmRoomSearchFragment)}
                    is AlarmRoomExploreNavigationAction.NavigateToMakeRoom -> {}
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.roomList.collectLatest {
                alarmRoomAdapter.submitList(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.categoryList.collectLatest {
                categoryAdapter.submitList(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.clickedCategoryId.collectLatest {
                roomFilterByCategory(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.popularRoomList.collectLatest {
                popularRoomAdapter.submitList(it)
            }
        }
    }

    override fun initAfterBinding() {
    }

    override fun onResume() {
        super.onResume()
        viewModel.getGroups()
        viewModel.getCategory()
        viewModel.getFamousGroups()
    }

    private fun initAdapter(){
        binding.alarmRoomRecycler.adapter = alarmRoomAdapter
        binding.categorySelectRecycler.adapter = categoryAdapter
        binding.popularRoomRecycler.adapter = popularRoomAdapter
    }



//  해당하는 방으로 이동하는 로직
    private fun moveToRoom(roomId: Int) {

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

    private fun roomFilterByCategory(categoryId : Int) {
        if (categoryId != 1) {
            val filteredAlarmRoomList = viewModel.roomList.value.filter {
                it.category.id == categoryId
            }
            val alarmRoomAdapter = AlarmRoomAdapter(viewModel)
            binding.alarmRoomRecycler.adapter = alarmRoomAdapter
            alarmRoomAdapter.submitList(filteredAlarmRoomList)
        }

        else{
            val alarmRoomAdapter = AlarmRoomAdapter(viewModel)
            binding.alarmRoomRecycler.adapter = alarmRoomAdapter
            alarmRoomAdapter.submitList(viewModel.roomList.value)
        }
    }



}
