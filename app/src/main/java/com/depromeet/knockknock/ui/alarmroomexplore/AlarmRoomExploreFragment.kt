package com.depromeet.knockknock.ui.alarmroomexplore

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.filter
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentAlarmRoomExploreBinding
import com.depromeet.knockknock.ui.alarmroomexplore.adapter.AlarmRoomAdapter
import com.depromeet.knockknock.ui.alarmroomexplore.adapter.CategoryAdapter
import com.depromeet.knockknock.ui.alarmroomexplore.adapter.PopularRoomAdapter
import com.depromeet.knockknock.ui.alarmroomtab.AlarmRoomTabFragmentDirections
import com.depromeet.knockknock.util.customOnFocusChangeListener
import com.depromeet.knockknock.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map


@AndroidEntryPoint
class AlarmRoomExploreFragment : BaseFragment<FragmentAlarmRoomExploreBinding, AlarmRoomExploreViewModel>(R.layout.fragment_alarm_room_explore) {

    private val TAG = "AlarmRoomSearchFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_alarm_room_explore

    override val viewModel : AlarmRoomExploreViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val alarmRoomAdapter by lazy { AlarmRoomAdapter(viewModel,viewModel) }
    private val categoryAdapter by lazy { CategoryAdapter(viewModel) }
    private val popularRoomAdapter by lazy { PopularRoomAdapter(viewModel) }


    override fun initStartView() {
        binding.apply {
            this.exploreviewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initAdapter()
        initEditText()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when(it) {
                    is AlarmRoomExploreNavigationAction.NavigateToRoom ->  {navController.navigate(AlarmRoomTabFragmentDirections.actionAlarmRoomTabFragmentToAlarmRoomHistoryFragment(it.roomId))}
//                    navigate(AlarmRoomTabFragmentDirections.actionAlarmRoomTabFragmentToAlarmRoomHistoryFragment(it.roomId))
                    is AlarmRoomExploreNavigationAction.NavigateToAlarmRoomSearch -> {navController.navigate(R.id.action_alarmRoomTabFragment_to_alarmRoomSearchFragment)}
                    is AlarmRoomExploreNavigationAction.NavigateToMakeRoom -> {}
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.roomList.collectLatest {
                val alarmRoomAdapter = AlarmRoomAdapter(viewModel,viewModel)
                binding.alarmRoomRecycler.adapter = alarmRoomAdapter
                alarmRoomAdapter.submitData(it)
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
//        lifecycleScope.launchWhenStarted {
//            viewModel.roomList.collectLatest {
//                val alarmRoomAdapter = AlarmRoomAdapter(viewModel,viewModel)
//                binding.alarmRoomRecycler.adapter = alarmRoomAdapter
//                alarmRoomAdapter.submitData(it)
//            }
//        }
        viewModel.getCategory()
        viewModel.getFamousGroups()
    }

    private fun initAdapter(){
        binding.alarmRoomRecycler.adapter = alarmRoomAdapter
        binding.categorySelectRecycler.adapter = categoryAdapter
        binding.popularRoomRecycler.adapter = popularRoomAdapter
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
//            val filteredAlarmRoomList = viewModel.roomList.filter {
//                it.category.id == categoryId
//            }

            val filteredAlarmRoomList = viewModel.roomList.map {
                    pagingData ->
                pagingData.filter{
                    it.category.id==categoryId
                }
            }
            lifecycleScope.launchWhenStarted {
                filteredAlarmRoomList.collectLatest {
                    val alarmRoomAdapter = AlarmRoomAdapter(viewModel,viewModel)
                    binding.alarmRoomRecycler.adapter = alarmRoomAdapter
                    alarmRoomAdapter.submitData(it)
                }
            }

        }

        else{
            lifecycleScope.launchWhenStarted {
                viewModel.roomList.collectLatest {
                    val alarmRoomAdapter = AlarmRoomAdapter(viewModel,viewModel)
                    binding.alarmRoomRecycler.adapter = alarmRoomAdapter
                    alarmRoomAdapter.submitData(it)
                }
            }
        }
    }
}
