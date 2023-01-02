package com.depromeet.knockknock.ui.settingroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.domain.model.Member
import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.HolderExportMemberBinding
import com.depromeet.knockknock.ui.friendlist.model.User
import com.depromeet.knockknock.ui.settingroom.SettingRoomActionHandler

class ExportMemberAdapter(
    val eventListener: SettingRoomActionHandler
) : ListAdapter<Member, ExportMemberAdapter.ViewHolder>(FriendListItemDiffCallback){

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding: HolderExportMemberBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.holder_export_member,
            parent,
            false
        )
        viewDataBinding.exportBtn.setOnClickListener {
            eventListener.onExportClicked(viewDataBinding.holder!!.user_id)
        }
        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: HolderExportMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Member) {
            binding.holder = item
            binding.executePendingBindings()
        }
    }

    internal object FriendListItemDiffCallback : DiffUtil.ItemCallback<Member>() {
        override fun areItemsTheSame(oldItem: Member, newItem: Member) =
            oldItem.user_id == newItem.user_id

        override fun areContentsTheSame(oldItem: Member, newItem: Member) =
            oldItem == newItem
    }
}