package com.depromeet.knockknock.ui.information

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentInformationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class InformationFragment : BaseFragment<FragmentInformationBinding, InformationViewModel>(R.layout.fragment_information) {

    private val TAG = "InformationFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_information

    override val viewModel : InformationViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun initStartView() {
        binding.apply {
            this.viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when(it) {
                    is InformationNavigationAction.NavigateToUserConsents -> toActionView(requireContext().getString(R.string.user_consent))
                    is InformationNavigationAction.NavigateToUserPrivacy -> toActionView(requireContext().getString(R.string.user_privacy))
                    is InformationNavigationAction.NavigateToAppMakers -> navigate(InformationFragmentDirections.actionInformationFragmentToMakersFragment())
                }
            }
        }
    }

    override fun initAfterBinding() {
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            this.title = getString(R.string.information_title)

            // 뒤로가기 버튼
            this.setNavigationIcon(R.drawable.ic_allow_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    private fun toActionView(uri: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    }
}
