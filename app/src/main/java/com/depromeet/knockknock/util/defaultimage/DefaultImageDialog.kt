package com.depromeet.knockknock.util.defaultimage

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.depromeet.domain.model.Profile
import com.depromeet.knockknock.databinding.DialogBottomDefaultImageBinding
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
class DefaultImageDialog(
    val isCheckedImage: Profile,
    val callback: (profile: Profile) -> Unit
) : BottomSheetDialogFragment() {

    private val viewModel by viewModels<DefaultImageViewModel>()
    private lateinit var binding: DialogBottomDefaultImageBinding
    private lateinit var dlg : BottomSheetDialog
    private val defaultImageAdapter by lazy { DefaultImageAdapter(eventListener = viewModel, isCheckedImage = isCheckedImage) }

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
        binding = DialogBottomDefaultImageBinding.inflate(inflater, container, false).apply {
            viewmodel = this@DefaultImageDialog.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            launch {
                viewModel.imageList.collectLatest {
                    defaultImageAdapter.submitList(it)
                }
            }

            launch {
                viewModel.clickImageUrl.collectLatest {
                    it?.let {
                        callback.invoke(it!!)
                        dismiss()
                    }
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
            this.adapter = defaultImageAdapter
            this.layoutManager = flexLayoutManager
        }
    }
}