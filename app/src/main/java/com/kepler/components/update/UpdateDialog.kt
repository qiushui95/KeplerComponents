package com.kepler.components.update

import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.kepler.component.update.presentation.dialog.VersionInfoDialog
import com.kepler.components.R
import com.kepler.components.databinding.DialogUpdateBinding

internal class UpdateDialog(
    activity: FragmentActivity,
) : VersionInfoDialog(activity) {

    private val binding by lazy {
        DialogUpdateBinding.bind(contentView)
    }

    init {
        setContentView(R.layout.dialog_update)
    }

    override fun getCloseButton(): View = binding.btnClose

    override fun getContinueButton(): TextView = binding.btnContinue
}