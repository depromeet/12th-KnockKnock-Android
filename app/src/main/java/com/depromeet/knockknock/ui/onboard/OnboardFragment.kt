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
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OnboardFragment : Fragment() {
    private lateinit var binding : FragmentOnboardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        val onboardAdapter = OnboardAdapter(
            listOf(
                OnboardData(getString(R.string.onboard_description_one), R.drawable.onboard_img_1),
                OnboardData(getString(R.string.onboard_description_two),R.drawable.onboard_img_2),
                OnboardData(getString(R.string.onboard_description_three),R.drawable.onboard_img_3)
            )
        )
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_onboard,container,false)
        binding.introViewpager.adapter = onboardAdapter
        binding.springDotsIndicator.attachTo(binding.introViewpager)
        binding.startText.setOnClickListener{
            it.findNavController().navigate(R.id.action_onboardFragment_to_registerFragment)
        }
        return binding.root

    }
}