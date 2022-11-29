package com.depromeet.knockknock.ui.alarmcreate

import androidx.fragment.app.viewModels
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentAlarmCreateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmCreateFragment : BaseFragment<FragmentAlarmCreateBinding, AlarmCreateViewModel>(R.layout.fragment_alarm_create) {

    private val TAG = "AlarmCreateFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_alarm_create

    override val viewModel : AlarmCreateViewModel by viewModels()

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