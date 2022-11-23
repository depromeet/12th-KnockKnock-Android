package com.depromeet.knockknock.ui.alarmsetting

import androidx.fragment.app.viewModels
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentAlarmSettingBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AlarmSettingFragment : BaseFragment<FragmentAlarmSettingBinding, AlarmSettingViewModel>(R.layout.fragment_alarm_setting) {

    private val TAG = "AlarmSettingFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_alarm_setting

    override val viewModel : AlarmSettingViewModel by viewModels()

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
