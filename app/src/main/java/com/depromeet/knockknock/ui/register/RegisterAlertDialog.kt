package com.depromeet.knockknock.ui.register

import android.view.View
import com.depromeet.knockknock.R
import com.depromeet.knockknock.base.BaseDialog
import com.depromeet.knockknock.databinding.DialogYellowDefaultAlertWithImageBinding
import com.depromeet.knockknock.databinding.DialogYellowDefaultAlertWithSingleButtonBinding


data class RegisterAlertDialogModel(
    val title: String?,
    val description: String?,
    val image: Int?,
    val negativeContents: String?,
    val positiveContents: String
)

class RegisterDefaultYellowAlertDialog(
    private val alertDialogModel: RegisterAlertDialogModel,
    private val clickToNegative: () -> Unit,
    private val clickToPositive: () -> Unit,
) : BaseDialog<DialogYellowDefaultAlertWithImageBinding>(layoutId = R.layout.dialog_yellow_default_alert_with_image) {

    override val layoutResourceId: Int
        get() = R.layout.dialog_yellow_default_alert_with_image

    override fun initStartView() {
        binding.model = alertDialogModel
    }

    override fun initDataBinding() = Unit

    override fun initAfterBinding() {
        if(alertDialogModel.title == null) {
            binding.alertTitle.visibility = View.GONE
        }
        if(alertDialogModel.description == null) {
            binding.alertDescription.visibility = View.GONE
        }

        if(alertDialogModel.image == null) {
            binding.alertImage.visibility = View.GONE
        }

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

class RegisterDefaultYellowAlertSingleButtonDialog(
    private val alertDialogModel: RegisterAlertDialogModel,
    private val clickToPositive: () -> Unit,
) : BaseDialog<DialogYellowDefaultAlertWithSingleButtonBinding>(layoutId = R.layout.dialog_yellow_default_alert_with_single_button) {

    override val layoutResourceId: Int
        get() = R.layout.dialog_yellow_default_alert_with_single_button

    override fun initStartView() {
        binding.model = alertDialogModel
    }

    override fun initDataBinding() = Unit

    override fun initAfterBinding() {
        if(alertDialogModel.title == null) {
            binding.alertTitle.visibility = View.GONE
        }
        if(alertDialogModel.description == null) {
            binding.alertDescription.visibility = View.GONE
        }

        if(alertDialogModel.image == null) {
            binding.alertImage.visibility = View.GONE
        }

        binding.positiveBtn.setOnClickListener {
            clickToPositive.invoke()
            dismiss()
        }
    }
}

