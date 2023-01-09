package com.depromeet.knockknock.util.defaultreaction

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.depromeet.domain.model.MyReactionInfo
import com.depromeet.domain.model.Profile
import com.depromeet.knockknock.databinding.DialogBottomDefaultImageBinding
import com.depromeet.knockknock.databinding.DialogBottomDefaultReactionBinding
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DefaultReactionDialog(
    val isCheckedImage: MyReactionInfo,
    val callback: (clickId: Int) -> Unit
) : BottomSheetDialogFragment() {

    private val viewModel by viewModels<DefaultReactionViewModel>()
    private lateinit var binding: DialogBottomDefaultReactionBinding
    private lateinit var dlg : BottomSheetDialog
    private val defaultReactionAdapter by lazy { DefaultReactionAdapter(eventListener = viewModel, isCheckedImage = isCheckedImage) }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 이 코드를 실행하지 않으면 XML에서 round 처리를 했어도 적용되지 않는다.
        dlg = ( super.onCreateDialog(savedInstanceState).apply {
            // window?.setDimAmount(0.2f) // Set dim amount here
            setOnShowListener {
                val bottomSheet = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                bottomSheet.setBackgroundResource(android.R.color.transparent)

                val behavior = BottomSheetBehavior.from(bottomSheet)
                behavior.isDraggable = true
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        } ) as BottomSheetDialog
        return dlg
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogBottomDefaultReactionBinding.inflate(inflater, container, false).apply {
            viewmodel = this@DefaultReactionDialog.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            launch {
                viewModel.imageList.collectLatest {
                    defaultReactionAdapter.submitList(it)
                }
            }

            launch {
                viewModel.clickImageUrl.collectLatest {
                    callback.invoke(it!!)
                    dismiss()
                }
            }
        }
    }

    private fun initAdapter() {
        val flexLayoutManager = FlexboxLayoutManager(requireContext()).apply {
            this.flexDirection = FlexDirection.ROW
            this.justifyContent = JustifyContent.CENTER
            this.alignItems = AlignItems.FLEX_START
        }

        binding.imageRecycler.apply {
            this.adapter = defaultReactionAdapter
            this.layoutManager = flexLayoutManager
        }
    }
}