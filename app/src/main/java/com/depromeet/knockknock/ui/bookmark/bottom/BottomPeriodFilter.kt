package com.depromeet.knockknock.ui.bookmark.bottom

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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomPeriodFilter(
    val period: Int,
    val callback: (period: Int) -> Unit
) : BottomSheetDialogFragment(){
    private lateinit var dlg : BottomSheetDialog

    private var periodType: PeriodType = PeriodType.Init

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
        return inflater.inflate(R.layout.dialog_bottom_room_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(period) {
            1 -> PeriodType.One
            3 -> PeriodType.Three
            6 -> PeriodType.Six
            12 -> PeriodType.Year
        }

        val one = requireView().findViewById<TextView>(R.id.day_one_month)
        val three = requireView().findViewById<TextView>(R.id.day_three_month)
        val six = requireView().findViewById<TextView>(R.id.day_six_month)
        val year = requireView().findViewById<TextView>(R.id.day_one_year)

        when(periodType) {
            is PeriodType.Init -> Unit
            is PeriodType.One -> clickedPeriod(one)
            is PeriodType.Three -> clickedPeriod(three)
            is PeriodType.Six -> clickedPeriod(six)
            is PeriodType.Year -> clickedPeriod(year)
        }

        one.setOnClickListener {
            callback.invoke(1)
            dismiss()
        }
        three.setOnClickListener {
            callback.invoke(3)
            dismiss()
        }
        six.setOnClickListener {
            callback.invoke(6)
            dismiss()
        }
        year.setOnClickListener {
            callback.invoke(12)
            dismiss()
        }
    }

    private fun clickedPeriod(clicked: TextView) {
        clicked.background = requireContext().getDrawable(R.drawable.custom_yellow_radius16)
        clicked.setTextColor(requireContext().getColor(R.color.black))
    }
}

sealed class PeriodType {
    object Init: PeriodType()
    object One: PeriodType()
    object Three: PeriodType()
    object Six: PeriodType()
    object Year: PeriodType()
}