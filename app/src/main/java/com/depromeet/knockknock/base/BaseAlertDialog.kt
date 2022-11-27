package com.depromeet.knockknock.base

import com.depromeet.knockknock.R
import com.depromeet.knockknock.databinding.DialogRedDefaultAlertBinding
import com.depromeet.knockknock.databinding.DialogYellowDefaultAlertBinding


data class AlertDialogModel(
    val title : String?,
    val description : String?,
    val negativeContents : String,
    val positiveContents : String
)

class DefaultYellowAlertDialog(
    private val alertDialogModel: AlertDialogModel,
    private val clickToNegative: () -> Unit,
    private val clickToPositive: () -> Unit,
) : BaseDialog<DialogYellowDefaultAlertBinding>(layoutId = R.layout.dialog_yellow_default_alert) {

    override val layoutResourceId: Int
        get() = R.layout.dialog_yellow_default_alert

    override fun initStartView() {
        binding.model = alertDialogModel
    }

    override fun initDataBinding() = Unit

    override fun initAfterBinding() {
        binding.negativeBtn.setOnClickListener {
            clickToNegative.invoke()
            dismiss()
        }
        binding.positiveBtn.setOnClickListener {
            clickToPositive.invoke()
            dismiss()
        }
    }
}

class DefaultRedAlertDialog(
    private val alertModel: AlertDialogModel,
    private val clickToNegative: () -> Unit,
    private val clickToPositive: () -> Unit,
) : BaseDialog<DialogRedDefaultAlertBinding>(layoutId = R.layout.dialog_red_default_alert) {

    override val layoutResourceId: Int
        get() = R.layout.dialog_red_default_alert

    override fun initStartView() {
        binding.model = alertModel
    }

    override fun initDataBinding() = Unit

    override fun initAfterBinding() {
        binding.negativeBtn.setOnClickListener {
            clickToNegative.invoke()
            dismiss()
        }
        binding.positiveBtn.setOnClickListener {
            clickToPositive.invoke()
            dismiss()
        }
    }
}