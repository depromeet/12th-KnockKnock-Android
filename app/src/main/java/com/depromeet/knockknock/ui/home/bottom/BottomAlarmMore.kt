package com.depromeet.knockknock.ui.home.bottom

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.knockknock.R
import com.depromeet.knockknock.ui.bookmark.adapter.FilterRoomAdapter
import com.depromeet.knockknock.ui.bookmark.model.Room
import com.depromeet.knockknock.ui.friendlist.bottom.FriendMoreType
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.w3c.dom.Text

class BottomAlarmMore(
    val callback: (type: AlarmMoreType) -> Unit
) : BottomSheetDialogFragment(){
    private lateinit var dlg : BottomSheetDialog

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.dialog_bottom_alarm_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val copy = requireView().findViewById<TextView>(R.id.copy_btn)
        val save = requireView().findViewById<TextView>(R.id.save_btn)
        val delete = requireView().findViewById<TextView>(R.id.delete_btn)
        val declare = requireView().findViewById<TextView>(R.id.user_declare_btn)
        val report = requireView().findViewById<TextView>(R.id.report_btn)
        val close = requireView().findViewById<TextView>(R.id.close_btn)

        copy.setOnClickListener {
            callback.invoke(AlarmMoreType.Copy)
            dismiss()
        }
        save.setOnClickListener {
            callback.invoke(AlarmMoreType.Save)
            dismiss()
        }
        delete.setOnClickListener {
            callback.invoke(AlarmMoreType.Delete)
            dismiss()
        }
        declare.setOnClickListener {
            callback.invoke(AlarmMoreType.Declare)
            dismiss()
        }
        report.setOnClickListener {
            callback.invoke(AlarmMoreType.Report)
            dismiss()
        }
        close.setOnClickListener {
            dismiss()
        }
    }
}

sealed class AlarmMoreType {
    object Copy: AlarmMoreType()
    object Save: AlarmMoreType()
    object Delete: AlarmMoreType()
    object Declare: AlarmMoreType()
    object Report: AlarmMoreType()
}

