package com.depromeet.knockknock.ui.alarmroomhistory.bottom

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.depromeet.knockknock.databinding.BottomSheetAlarmCopyRoomBinding
import com.depromeet.knockknock.ui.alarmroomhistory.adapter.AlarmCopyRoomAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class BottomAlarmCopyRoom(
    val callback: (roomId: Int) -> Unit
) : BottomSheetDialogFragment() {

    private val viewModel by viewModels<BottomAlarmCopyRoomViewModel>()
    private lateinit var binding: BottomSheetAlarmCopyRoomBinding
    private lateinit var dlg: BottomSheetDialog
    private val alarmCopyRoomAdapter by lazy { AlarmCopyRoomAdapter(eventListener = viewModel) }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 이 코드를 실행하지 않으면 XML에서 round 처리를 했어도 적용되지 않는다.
        dlg = (super.onCreateDialog(savedInstanceState).apply {
            // window?.setDimAmount(0.2f) // Set dim amount here
            setOnShowListener {
                val bottomSheet =
                    findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                bottomSheet.setBackgroundResource(android.R.color.transparent)

                val behavior = BottomSheetBehavior.from(bottomSheet)
                behavior.isDraggable = false
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }) as BottomSheetDialog
        return dlg
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetAlarmCopyRoomBinding.inflate(inflater, container, false).apply {
            viewmodel = this@BottomAlarmCopyRoom.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        lifecycleScope.launchWhenStarted {
            viewModel.roomList.collectLatest {
                alarmCopyRoomAdapter.submitData(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.isSelected.collectLatest {
                callback.invoke(viewModel.clickRoomList)
                dismiss()
            }
        }

        binding.closeBtn.setOnClickListener {
            dismiss()
        }
    }

    private fun initAdapter() {
        binding.roomRecycler.adapter = alarmCopyRoomAdapter
    }
}