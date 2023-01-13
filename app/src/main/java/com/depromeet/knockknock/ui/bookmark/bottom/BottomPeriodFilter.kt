package com.depromeet.knockknock.ui.bookmark.bottom

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.depromeet.knockknock.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomPeriodFilter(
    val period: Int,
    val callback: (period: Int) -> Unit
) : BottomSheetDialogFragment(){
    private lateinit var dlg : BottomSheetDialog

    private var periodType: PeriodType = PeriodType.Init

    private val all by lazy { requireView().findViewById<ConstraintLayout>(R.id.all_btn) }
    private val allCheck by lazy { requireView().findViewById<CheckBox>(R.id.all_check) }

    private val one by lazy {  requireView().findViewById<ConstraintLayout>(R.id.one_btn) }
    private val oneCheck by lazy {  requireView().findViewById<CheckBox>(R.id.one_check) }

    private val three by lazy {  requireView().findViewById<ConstraintLayout>(R.id.three_btn) }
    private val threeCheck by lazy {  requireView().findViewById<CheckBox>(R.id.three_check) }

    private val six by lazy {  requireView().findViewById<ConstraintLayout>(R.id.six_btn) }
    private val sixCheck by lazy {  requireView().findViewById<CheckBox>(R.id.six_check) }

    private val year by lazy {  requireView().findViewById<ConstraintLayout>(R.id.year_btn) }
    private val yearCheck by lazy {  requireView().findViewById<CheckBox>(R.id.year_check) }

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
        return inflater.inflate(R.layout.dialog_bottom_period_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(period) {
            0 -> periodCheck(type = PeriodType.All)
            1 -> periodCheck(type = PeriodType.One)
            3 -> periodCheck(type = PeriodType.Three)
            6 -> periodCheck(type = PeriodType.Six)
            12 -> periodCheck(type = PeriodType.Year)
        }

        val saveBtn = requireView().findViewById<TextView>(R.id.save_btn)
        saveBtn.setOnClickListener {
            when(periodType) {
                is PeriodType.All -> callback.invoke(0)
                is PeriodType.One -> callback.invoke(1)
                is PeriodType.Three -> callback.invoke(3)
                is PeriodType.Six -> callback.invoke(6)
                is PeriodType.Year -> callback.invoke(12)
            }
            dismiss()
        }

        val closeBtn = requireView().findViewById<ImageView>(R.id.close_btn)
        closeBtn.setOnClickListener {
            dismiss()
        }

        all.setOnClickListener {
            periodCheck(type = PeriodType.All)
            periodType = PeriodType.All
        }
        one.setOnClickListener {
            periodCheck(type = PeriodType.One)
            periodType = PeriodType.One
        }
        three.setOnClickListener {
            periodCheck(type = PeriodType.Three)
            periodType = PeriodType.Three
        }
        six.setOnClickListener {
            periodCheck(type = PeriodType.Six)
            periodType = PeriodType.Six
        }
        year.setOnClickListener {
            periodCheck(type = PeriodType.Year)
            periodType = PeriodType.Year
        }
    }

    private fun periodCheck(type: PeriodType) {
        allCheck.isChecked = false
        oneCheck.isChecked = false
        threeCheck.isChecked = false
        sixCheck.isChecked = false
        yearCheck.isChecked = false

        when(type) {
            is PeriodType.All -> allCheck.isChecked = true
            is PeriodType.One -> oneCheck.isChecked = true
            is PeriodType.Three -> threeCheck.isChecked = true
            is PeriodType.Six -> sixCheck.isChecked = true
            is PeriodType.Year -> yearCheck.isChecked = true
            else -> Unit
        }
    }
}

sealed class PeriodType {
    object Init: PeriodType()
    object All: PeriodType()
    object One: PeriodType()
    object Three: PeriodType()
    object Six: PeriodType()
    object Year: PeriodType()
}