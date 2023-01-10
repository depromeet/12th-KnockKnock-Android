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
        // 정은님
        binding.ivMakersServer01Link.setOnClickListener {
            showGithub("mybloom")
        }
        // 서준님
        binding.ivMakersServer02Link.setOnClickListener {
            showGithub("leeseojune53")
        }
        // 찬진님
        binding.ivMakersServer03Link.setOnClickListener {
            showGithub("ImNM")
        }
        // 영준님
        binding.ivMakersAos01Link.setOnClickListener {
            val url = "https://velog.io/@leeyjwinter"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
        // 준장님
        binding.ivMakersAos02Link.setOnClickListener {
            showGithub("junjange")
        }
        // 현정님
        binding.ivMakersAos03Link.setOnClickListener {
            showGithub("hyunjung-choi")
        }
        // 규일님
        binding.ivMakersAos04Link.setOnClickListener {
            val url = "https://juicy-capacity-601.notion.site/832119efdd1e49d2a4778ff8d4878b3d?v=3ac3fc832e8b4b82bb782e904efd6cd6"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
        // 나영님
        binding.ivMakersDesign01Link.setOnClickListener {
            val url = "https://www.behance.net/402zzang"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
        // 수연님
        binding.ivMakersDesign02Link.setOnClickListener {
            val url = "https://www.behance.net/sypak120c57e"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
        // 승희님
        binding.ivMakersDesign03Link.setOnClickListener {
            val url = "https://www.behance.net/kb1658280b"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    private fun showGithub(githubId: String) {
        val url = "https://github.com/"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("${url}${githubId}"))
        startActivity(intent)
    }
}