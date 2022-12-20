package com.depromeet.knockknock.ui.preview

import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseFragment
import com.depromeet.knockknock.databinding.FragmentPreviewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class PreviewFragment :
    BaseFragment<FragmentPreviewBinding, PreviewViewModel>(R.layout.fragment_preview) {

    private val TAG = "PreviewFragment"

    override val layoutResourceId: Int
        get() = R.layout.fragment_preview
    private val navController by lazy { findNavController() }

    override val viewModel: PreviewViewModel by viewModels()

    override fun initStartView() {
        binding.apply {
            this.vm = viewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        exception = viewModel.errorEvent
        val args: PreviewFragmentArgs by navArgs()
        viewModel.previewTitleEvent.value = args.title
        binding.previewMessageTextView.text = args.message
        viewModel.onImageUriChecked(args.uri)

        if (args.message == "" && args.uri != "") {
            binding.previewMessageTextView.visibility = View.GONE
            binding.ivFold.visibility = View.GONE
            binding.messageImgCardView.layoutParams.height = 150f.toDp()
            binding.messageImgCardView.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        }
        isEllipsis(binding.previewMessageTextView)

        setupEvent()
    }

    // 1이 나온다는 것은 글씨가 줄여졌다는 것이다.
    private fun isEllipsis(textView: TextView) {
        textView.post {
            if (textView.layout.lineCount > 0) {
                if (textView.layout.getEllipsisCount(textView.layout.lineCount - 1) > 0) {
                    binding.ivFold.visibility = View.VISIBLE
                }else{
                    binding.ivFold.visibility = View.GONE
                }
            }
        }
    }

    private fun Float.toDp(): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            resources.displayMetrics
        ).toInt()
    }

    private fun setupEvent() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigationEvent.collectLatest {
                when (it) {
                    is PreviewNavigationAction.NavigateToBackStack -> navController.popBackStack()
                }
            }
        }
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }

}