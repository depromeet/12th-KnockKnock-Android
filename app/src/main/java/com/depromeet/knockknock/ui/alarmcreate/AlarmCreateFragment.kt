package com.depromeet.knockknock.ui.alarmcreate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentAlarmCreateBinding
import com.depromeet.knockknock.databinding.FragmentHomeBinding
import com.depromeet.knockknock.ui.home.HomeViewModel

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