package com.depromeet.knockknock.ui.category

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.depromeet.domain.model.Category
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentCategoryBinding
import com.depromeet.knockknock.ui.category.adapter.CategoryAdapter
import com.depromeet.knockknock.ui.category.adapter.beforeClicked
import com.depromeet.knockknock.ui.invitefriendtoroom.InviteFriendToRoomFragmentArgs
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding, CategoryViewModel>(R.layout.fragment_category) {

    private val TAG = "CategoryFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_category

    private val args: CategoryFragmentArgs by navArgs()

    override val viewModel : CategoryViewModel by viewModels()
    private val navController by lazy { findNavController() }
    private val categoryAdapter by lazy { CategoryAdapter(viewModel) }

    override fun initStartView() {

        viewModel.group_id.value= args.groupId
        viewModel.group_category_id.value = args.groupCategoryId
        viewModel.group_description.value = args.groupDescription
        viewModel.group_title.value = args.groupTitle
        viewModel.group_public_access.value = args.groupPublicAccess
        viewModel.group_background_path.value = args.groupBackgroundPath
        viewModel.group_thumbnail_path.value = args.groupThumbnailPath

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
            viewModel.onSaveSuccess.collectLatest {
                navController.popBackStack()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.categoryList.collectLatest {
                categoryAdapter.submitList(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        beforeClicked = 0
        viewModel.getCategories()
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.title = getString(R.string.category_title)
            // 뒤로가기 버튼
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
