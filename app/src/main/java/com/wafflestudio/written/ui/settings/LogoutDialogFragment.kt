package com.wafflestudio.written.ui.settings

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.wafflestudio.written.databinding.FragmentDialogLogoutBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class LogoutDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentDialogLogoutBinding
    private lateinit var viewModel: SettingsViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            binding = FragmentDialogLogoutBinding.inflate(layoutInflater)
            viewModel = ViewModelProvider(requireActivity()).get(SettingsViewModel::class.java)
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)

            binding.cancelButton.setOnClickListener {
                dismiss()
            }

            binding.logoutButton.setOnClickListener {
                viewModel.confirmLogout()
                viewModel.observeConfirmLogout()
                    .subscribe {
                        Timber.d("boolean! $it")
                    }
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}