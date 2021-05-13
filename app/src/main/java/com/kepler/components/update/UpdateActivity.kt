package com.kepler.components.update

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.ToastUtils
import com.kepler.component.update.presentation.StarterOfUpdate
import com.kepler.component.update.presentation.vm.VersionViewModel
import com.kepler.components.databinding.ActivityUpdateBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateActivity : AppCompatActivity() {

    private val viewModel by viewModel<VersionViewModel>()

    private val updateDialogLazy = lazy {
        UpdateDialog(this@UpdateActivity)
    }

    private val updateDialog by updateDialogLazy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        StarterOfUpdate(application)

        val binding = ActivityUpdateBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnCheckUpdate.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                viewModel.checkUpdate(true)?.apply {
                    updateDialog.updateVersionInfo(this)
                        .showPopupWindow()
                } ?: ToastUtils.showShort("已是最新版")
            }
        }

        binding.btnCheckUpdateAuto.setOnClickListener {

        }

        lifecycleScope.launchWhenResumed {

            viewModel.observeVersionInfo()
                .onEach {
                    if (updateDialogLazy.isInitialized()) {
                        when {
                            it == null -> {
                                updateDialog.dismiss()
                            }
                            updateDialog.isShowing -> {
                                updateDialog.updateVersionInfo(it)
                            }
                            else -> {
                                updateDialog.updateVersionInfo(it)
                                    .showPopupWindow()
                            }
                        }
                    } else if (it != null) {
                        updateDialog.updateVersionInfo(it)
                            .showPopupWindow()
                    }
                }
                .launchIn(this)
        }

    }
}