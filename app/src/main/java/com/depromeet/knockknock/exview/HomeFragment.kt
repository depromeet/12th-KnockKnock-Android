package com.dida.android.presentation.views.nav.home

import android.annotation.SuppressLint
import android.os.Build
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.dida.android.R
import com.dida.android.databinding.FragmentHomeBinding
import com.dida.android.presentation.adapter.home.*
import com.dida.android.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    private val TAG = "HomeFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_home

    override val viewModel : HomeViewModel by viewModels()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        initAdapter()
    }

    override fun initDataBinding() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collect {
                when(it) {
                    is HomeNavigationAction.NavigateToHotItem -> { navigate(HomeFragmentDirections.actionHomeFragmentToDetailNftFragment()) }
                    is HomeNavigationAction.NavigateToHotSeller -> {  }
                    is HomeNavigationAction.NavigateToSoldOut -> { navigate(HomeFragmentDirections.actionHomeFragmentToDetailNftFragment()) }
                    is HomeNavigationAction.NavigateToRecentNftItem -> { navigate(HomeFragmentDirections.actionHomeFragmentToDetailNftFragment()) }
                    is HomeNavigationAction.NavigateToCollection -> {  }
                }
            }
        }
    }

    override fun initAfterBinding() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.homeScroll.setOnScrollChangeListener { _, _, scrollY, _, _ ->
                // soldout
                if(binding.hotSellerRecycler.y+binding.hotSellerRecycler.height<= scrollY && scrollY < binding.soldoutMore.y) {
                    binding.tabLayout.selectTab(binding.tabLayout.getTabAt(1))
                }
                // recent
                else if(binding.soldoutMore.y <= scrollY && scrollY < binding.recentnftRecycler.y+100) {
                    binding.tabLayout.selectTab(binding.tabLayout.getTabAt(2))
                }
                // collection
                else if(binding.recentnftRecycler.y+100 <= scrollY) {
                    binding.tabLayout.selectTab(binding.tabLayout.getTabAt(3))
                }
                // hot seller
                else{
                    binding.tabLayout.selectTab(binding.tabLayout.getTabAt(0))
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initAdapter() {
        binding.hotsRecycler.adapter = HotsAdapter(viewModel)
        binding.hotSellerRecycler.adapter = HotSellerAdapter(viewModel)
        binding.soldoutRecycler.adapter = SoldOutAdapter(viewModel)
        binding.collectionRecycler.adapter = CollectionAdapter(viewModel)

        binding.recentnftRecycler.apply {
            adapter = RecentNftAdapter(viewModel)
            layoutManager = GridLayoutManager(context, 2)
        }

        with(binding.tabLayout) {
            addTab(this.newTab().setText(R.string.home_hotitem_tab))
            addTab(this.newTab().setText(R.string.home_soldout_tab))
            addTab(this.newTab().setText(R.string.home_recentnft_tab))
            addTab(this.newTab().setText(R.string.home_collection_tab))

            for (i in 0 until (this.getChildAt(0) as LinearLayout).childCount) {
                (this.getChildAt(0) as LinearLayout).getChildAt(i).setOnTouchListener { _, _ ->
                    moveScroll(i)
                    true
                }
            }
        }
    }

    private fun initToolbar() {
        binding.homeToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_alarm -> {}
                R.id.action_search -> {}
            }
            true
        }
    }

    private fun moveScroll(tabId: Int) {
        with(binding.homeScroll) {
            when(tabId) {
                0 -> { smoothScrollToView(binding.hotSellerRecycler, 100, 1000) }
                1 -> { smoothScrollToView(binding.soldoutTxt, 50, 1000) }
                2 -> { smoothScrollToView(binding.recentnftTxt, 50, 1000) }
                3 -> { smoothScrollToView(binding.collectionTxt, 0, 1000) }
            }
            binding.appBarLayout.setExpanded(false)
        }
    }
}
