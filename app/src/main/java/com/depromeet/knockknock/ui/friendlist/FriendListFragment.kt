package com.depromeet.knockknock.ui.friendlist

import androidx.fragment.app.viewModels
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentFriendListBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FriendListFragment : BaseFragment<FragmentFriendListBinding, FriendListViewModel>(R.layout.fragment_friend_list) {

    private val TAG = "FriendListFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_friend_list

    override val viewModel : FriendListViewModel by viewModels()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}
