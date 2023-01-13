package com.depromeet.knockknock.ui.alarmcreate.bottom

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.FrameLayout
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.depromeet.knockknock.R
import com.depromeet.knockknock.ui.alarmcreate.model.ReservationAlarm
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class BottomAlarmReservationPicker(
    val callback: (sendPosition: ReservationAlarm) -> Unit
) : BottomSheetDialogFragment(

) {
    private lateinit var dlg: BottomSheetDialog

    var reservationDate: String? = null
    var reservationTime: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 이 코드를 실행하지 않으면 XML에서 round 처리를 했어도 적용되지 않는다.
        dlg = (super.onCreateDialog(savedInstanceState).apply {
            // window?.setDimAmount(0.2f) // Set dim amount here
            setOnShowListener {
                val bottomSheet =
                    findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                bottomSheet.setBackgroundResource(android.R.color.transparent)

                val behavior = BottomSheetBehavior.from(bottomSheet)
                behavior.isDraggable = true
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }) as BottomSheetDialog
        return dlg
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(
            R.layout.bottom_sheet_alarm_reservation_picker_layout,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sendReservationToolbar = requireView().findViewById<Toolbar>(R.id.toolbar)
        sendReservationToolbar.setNavigationIcon(R.drawable.ic_allow_back)
        sendReservationToolbar.setNavigationOnClickListener { dismiss() }


        val current = LocalDateTime.now()
        reservationDate = current.format(DateTimeFormatter.ISO_DATE)
        reservationTime = current.format(DateTimeFormatter.ISO_TIME)

        val sendReservationCalendarView =
            requireView().findViewById<CalendarView>(R.id.calendarview)
        sendReservationCalendarView.minDate = System.currentTimeMillis()
        sendReservationCalendarView.setOnDateChangeListener(OnDateChangeListener { view, year, monthOfYear, dayOfMonth ->

            reservationDate = "$year-"
            reservationDate +=
                if ((monthOfYear + 1) < 10) "0" + (monthOfYear + 1).toString() + "-"
                else (monthOfYear + 1).toString() + "-"
            reservationDate +=
                if (dayOfMonth < 10) "0$dayOfMonth"
                else "$dayOfMonth"
        })

        val sendReservationTimePicker = requireView().findViewById<TimePicker>(R.id.timePicker)
        sendReservationTimePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            //TimePicker 특성 상 한자리 시간 입력에 대한 대응을 해줘야 함
            reservationTime =
                if (hourOfDay < 10) "0${hourOfDay}:"
                else "$hourOfDay:"
            reservationTime +=
                if (minute < 10) "0${minute}"
                else "$minute"
        }

        val thisTimeSendCardView =
            requireView().findViewById<CardView>(R.id.this_time_send_card_view)
        thisTimeSendCardView.setOnClickListener {
            callback.invoke(
                ReservationAlarm(
                    reservationDate = reservationDate!!,
                    reservationTime = reservationTime!!
                )
            )
            Toast.makeText(
                requireContext(), ReservationAlarm(
                    reservationDate = reservationDate!!,
                    reservationTime = reservationTime!!
                ).toString(), Toast.LENGTH_SHORT
            ).show()
            dismiss()
        } // 예약 보내기 버튼 클릭시
    }
}

