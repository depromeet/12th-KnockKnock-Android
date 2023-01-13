package com.depromeet.knockknock.ui.aloneroommakecategory

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentAloneRoomMakeCategoryBinding
import com.depromeet.knockknock.ui.aloneroommakecategory.AloneRoomMakeCategoryFragmentDirections.ActionAloneRoomMakeCategoryFragmentToAloneRoomDetailsFragment
import com.depromeet.knockknock.ui.aloneroommakecategory.adapter.AloneRoomMakeCategoryAdapter
import com.depromeet.knockknock.ui.aloneroommakecategory.adapter.beforeClicked
import com.depromeet.knockknock.ui.category.adapter.CategoryAdapter
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AloneRoomMakeCategoryFragment : BaseFragment<FragmentAloneRoomMakeCategoryBinding, AloneRoomMakeCategoryViewModel>(R.layout.fragment_alone_room_make_category) {

    private val TAG = "AloneRoomMakeCategoryFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_alone_room_make_category

    override val viewModel : AloneRoomMakeCategoryViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val categoryAdapter by lazy { AloneRoomMakeCategoryAdapter(viewModel) }

    override fun initStartView() {
        binding.apply {
            this.viewmodel  = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initAdapter()
    }

    override fun initDataBinding() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when(it){
                    is AloneRoomMakeCategoryNavigationAction.NavigateToAloneRoomDetails -> {navigate(
                        AloneRoomMakeCategoryFragmentDirections.actionAloneRoomMakeCategoryFragmentToAloneRoomDetailsFragment(it.categoryId!!)
                    )}
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.categoryList.collectLatest {
                categoryAdapter.submitList(it)
            }
        }
    }

    override fun initAfterBinding() {
    }

    override fun onResume() {
        super.onResume()
        beforeClicked = 0
        val flexLayoutManager = FlexboxLayoutManager(requireContext()).apply {
            this.flexDirection = FlexDirection.ROW
            this.justifyContent = JustifyContent.FLEX_START
            this.alignItems = AlignItems.FLEX_START
        }
        binding.categoryRecycler.apply {
            this.adapter = categoryAdapter
            this.layoutManager = flexLayoutManager
        }
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.setNavigationIcon(R.drawable.ic_allow_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun initAdapter() {
        binding.categoryRecycler.adapter = categoryAdapter

        val flexLayoutManager = FlexboxLayoutManager(requireContext()).apply {
            this.flexDirection = FlexDirection.ROW
            this.justifyContent = JustifyContent.FLEX_START
            this.alignItems = AlignItems.FLEX_START
        }

        binding.categoryRecycler.apply {
            this.adapter = categoryAdapter
            this.layoutManager = flexLayoutManager
        }
    }
}
