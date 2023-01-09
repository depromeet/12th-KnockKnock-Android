package com.depromeet.knockknock.ui.makers

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentMakersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MakersFragment :
    BaseFragment<FragmentMakersBinding, MakersViewModel>(R.layout.fragment_makers) {

    private val TAG = "MakersFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_makers
    private val navController by lazy { findNavController() }

    override val viewModel: MakersViewModel by viewModels()

    override fun initStartView() {
        binding.apply {
            this.viewmodel = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        initToolbar()
        clickWhoIAm()
    }

    override fun initDataBinding() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationHandler.collectLatest {
                when (it) {
                }
            }
        }
    }

    private fun initToolbar() {
        with(binding.toolbarMakers) {
            this.title = "만든 사람들"

            // 뒤로가기 버튼
            this.setNavigationIcon(com.depromeet.knockknock.R.drawable.ic_allow_back)
            this.setNavigationOnClickListener { navController.popBackStack() }
        }
    }

    override fun initAfterBinding() {
    }

    private fun clickWhoIAm() {
        // 정은님 Github
        binding.ivMakersServer01Link.setOnClickListener {
            showGithub("mybloom")
        }
        // 서준님 Github
        binding.ivMakersServer02Link.setOnClickListener {
            showGithub("leeseojune53")
        }
        // 찬진님 Github
        binding.ivMakersServer03Link.setOnClickListener {
            showGithub("ImNM")
        }
        // 영준님 Github
        binding.ivMakersAos01Link.setOnClickListener {
            showGithub("leeyjwinter")
        }
        // 준장님 Github
        binding.ivMakersAos02Link.setOnClickListener {
            showGithub("junjange")
        }
        // 현정님 Github
        binding.ivMakersAos03Link.setOnClickListener {
            showGithub("hyunjung-choi")
        }
        // 규일님 Github
        binding.ivMakersAos04Link.setOnClickListener {
            showGithub("Gyuil-Hwnag")
        }
        // 나영님
        binding.ivMakersDesign01Link.setOnClickListener {

        }
        // 수연님
        binding.ivMakersDesign02Link.setOnClickListener {

        }
        // 승희님
        binding.ivMakersDesign03Link.setOnClickListener {

        }
    }

    private fun showGithub(githubId: String) {
        val url = "https://github.com/"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("${url}${githubId}"))
        startActivity(intent)
    }
}