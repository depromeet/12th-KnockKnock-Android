package com.depromeet.knockknock.ui.onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.FragmentOnboardBinding


class OnboardFragment : Fragment() {
    private lateinit var binding : FragmentOnboardBinding
    private val onboardAdapter = OnboardAdapter(
        listOf(
            OnboardData("푸시알림으로\n많은 사람에게 나의 말을\n전해보세요", R.drawable.onboard_img_1),
            OnboardData("친구를 초대하고\n같은 관심사를 가진\n그룹을 만들어요",R.drawable.onboard_img_2),
            OnboardData("이모지를 남기고\n푸시알림에\n생각을 표현해보세요",R.drawable.onboard_img_3)
        )
    )



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_onboard,container,false)
        binding.introViewpager.adapter = onboardAdapter
        binding.springDotsIndicator.attachTo(binding.introViewpager)
        binding.startText.setOnClickListener{
            it.findNavController().navigate(R.id.action_onboardFragment_to_registerFragment)
        }
        return binding.root

    }
}