package com.depromeet.knockknock.ui.bookmark.bottom

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.depromeet.knockknock.databinding.DialogBottomRoomFilterBinding
import com.depromeet.knockknock.ui.bookmark.adapter.FilterRoomAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class BottomRoomFilter(
    val callback: (roomList: List<Int>) -> Unit
) : BottomSheetDialogFragment(){

    private val viewModel by viewModels<BottomRoomViewModel>()
    private lateinit var binding: DialogBottomRoomFilterBinding
    private lateinit var dlg : BottomSheetDialog
    private val filterRoomAdapter by lazy { FilterRoomAdapter(eventListener = viewModel) }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 이 코드를 실행하지 않으면 XML에서 round 처리를 했어도 적용되지 않는다.
        dlg = ( super.onCreateDialog(savedInstanceState).apply {
            // window?.setDimAmount(0.2f) // Set dim amount here
            setOnShowListener {
                val bottomSheet = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                bottomSheet.setBackgroundResource(android.R.color.transparent)

                val behavior = BottomSheetBehavior.from(bottomSheet)
                behavior.isDraggable = false
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        } ) as BottomSheetDialog
        return dlg
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogBottomRoomFilterBinding.inflate(inflater, container, false).apply {
            viewmodel = this@BottomRoomFilter.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        lifecycleScope.launchWhenStarted {
            viewModel.roomList.collectLatest {
                filterRoomAdapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.isSelected.collectLatest {
                callback.invoke(viewModel.clickRoomList)
                dismiss()
            }
        }
    }

    private fun initAdapter() {
        binding.roomRecycler.adapter = filterRoomAdapter
    }
}