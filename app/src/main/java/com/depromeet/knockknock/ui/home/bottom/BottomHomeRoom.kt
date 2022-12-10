package com.depromeet.knockknock.ui.home.bottom

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.knockknock.R
import com.depromeet.knockknock.ui.bookmark.adapter.FilterRoomAdapter
import com.depromeet.knockknock.ui.bookmark.model.Room
import com.depromeet.knockknock.ui.home.HomeActionHandler
import com.depromeet.knockknock.ui.home.adapter.HomeRoomAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.w3c.dom.Text

class BottomHomeRoom(
    val roomList: List<Room>,
    val eventListener: HomeActionHandler
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
        return inflater.inflate(R.layout.dialog_bottom_home_room, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutEmpty = requireView().findViewById<ConstraintLayout>(R.id.layout_empty)
        val recycler = requireView().findViewById<RecyclerView>(R.id.room_recycler)
        val searchBtn = requireView().findViewById<TextView>(R.id.search_btn)
        val createBtn = requireView().findViewById<TextView>(R.id.create_btn)

        val adapter = HomeRoomAdapter(eventListener)
        adapter.submitList(roomList)
        recycler.adapter = adapter

        if(roomList.isEmpty()) {
            layoutEmpty.visibility = View.VISIBLE
            recycler.visibility = View.GONE
        } else {
            layoutEmpty.visibility = View.GONE
            recycler.visibility = View.VISIBLE
        }

        searchBtn.setOnClickListener {
            eventListener.onSearchRoomClicked()
            dismiss()
        }
        createBtn.setOnClickListener {
            eventListener.onCreateRoomClicked()
            dismiss()
        }
    }
}
