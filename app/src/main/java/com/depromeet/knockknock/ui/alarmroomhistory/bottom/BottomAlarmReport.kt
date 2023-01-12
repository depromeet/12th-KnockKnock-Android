package com.depromeet.knockknock.ui.alarmroomhistory.bottom

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.depromeet.knockknock.R
import com.depromeet.knockknock.ui.alarmcreate.messageTextOnFocusChangeListener
import com.depromeet.knockknock.util.customOnFocusChangeListener
import com.depromeet.knockknock.util.hideKeyboard
import com.depromeet.knockknock.util.showKeyboard
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomAlarmReport(
    val callback: (period: String) -> Unit
) : BottomSheetDialogFragment(){
    private lateinit var dlg : BottomSheetDialog

    private var spemType: PeriodType = PeriodType.Init

    private val all by lazy { requireView().findViewById<ConstraintLayout>(R.id.zero_btn) }
    private val allCheck by lazy { requireView().findViewById<CheckBox>(R.id.zero_check) }

    private val one by lazy {  requireView().findViewById<ConstraintLayout>(R.id.one_btn) }
    private val oneCheck by lazy {  requireView().findViewById<CheckBox>(R.id.one_check) }

    private val three by lazy {  requireView().findViewById<ConstraintLayout>(R.id.three_btn) }
    private val threeCheck by lazy {  requireView().findViewById<CheckBox>(R.id.three_check) }

    private val six by lazy {  requireView().findViewById<ConstraintLayout>(R.id.six_btn) }
    private val sixCheck by lazy {  requireView().findViewById<CheckBox>(R.id.six_check) }

    private val year by lazy {  requireView().findViewById<ConstraintLayout>(R.id.year_btn) }
    private val yearCheck by lazy {  requireView().findViewById<CheckBox>(R.id.year_check) }

    private val editTextReport by lazy {  requireView().findViewById<EditText>(R.id.edit_text_report) }
    private val linearLayoutEditTextReport by lazy {  requireView().findViewById<LinearLayout>(R.id.linear_layout_edit_text_report) }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NORMAL, R.style.DialogStyle)

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.bottom_sheet_alarm_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val saveBtn = requireView().findViewById<TextView>(R.id.save_btn)
        saveBtn.setOnClickListener {
            when(spemType) {
                is PeriodType.All -> callback.invoke("SPAM")
                is PeriodType.One -> callback.invoke("SPAM")
                is PeriodType.Three -> callback.invoke("SPAM")
                is PeriodType.Six -> callback.invoke("SPAM")
                is PeriodType.Year -> callback.invoke("SPAM")
                else -> callback.invoke("SPAM")
            }
            dismiss()
        }

        val closeBtn = requireView().findViewById<ImageView>(R.id.close_btn)
        closeBtn.setOnClickListener {
            dismiss()
        }

        all.setOnClickListener {
            periodCheck(type = PeriodType.All)
            spemType = PeriodType.All
            requireActivity().hideKeyboard()
            editTextReport.clearFocus()
        }
        one.setOnClickListener {
            periodCheck(type = PeriodType.One)
            spemType = PeriodType.One
            requireActivity().hideKeyboard()
            editTextReport.clearFocus()
        }
        three.setOnClickListener {
            periodCheck(type = PeriodType.Three)
            spemType = PeriodType.Three
            requireActivity().hideKeyboard()
            editTextReport.clearFocus()
        }
        six.setOnClickListener {
            periodCheck(type = PeriodType.Six)
            spemType = PeriodType.Six
            requireActivity().hideKeyboard()
            editTextReport.clearFocus()
        }
        year.setOnClickListener {
            periodCheck(type = PeriodType.Year)
            spemType = PeriodType.Year
            editTextReport.messageTextOnFocusChangeListener(requireContext(),linearLayoutEditTextReport )
            requireActivity().showKeyboard(editTextReport)
            editTextReport.requestFocus()

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