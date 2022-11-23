package com.depromeet.knockknock.ui.information

import androidx.fragment.app.viewModels
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentInformationBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InformationFragment : BaseFragment<FragmentInformationBinding, InformationViewModel>(R.layout.fragment_information) {

    private val TAG = "InformationFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_information

    override val viewModel : InformationViewModel by viewModels()

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
