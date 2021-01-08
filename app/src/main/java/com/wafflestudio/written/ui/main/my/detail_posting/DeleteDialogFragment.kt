package com.wafflestudio.written.ui.main.my.detail_posting

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.wafflestudio.written.databinding.FragmentDialogDeleteBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.lang.IllegalStateException

@AndroidEntryPoint
class DeleteDialogFragment: DialogFragment() {

    private lateinit var binding: FragmentDialogDeleteBinding
    private lateinit var viewModel: MyDetailPostingViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            binding = FragmentDialogDeleteBinding.inflate(layoutInflater)
            viewModel = ViewModelProvider(requireActivity()).get(MyDetailPostingViewModel::class.java)
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)

            binding.deleteButton.setOnClickListener {
                viewModel.confirmDelete()
                viewModel.observeConfirmDelete()
                    .subscribe {
                        Timber.d("boolean! $it")
                    }
                dismiss()
            }

            binding.cancelButton.setOnClickListener {
                viewModel.cancelDelete()
                dismiss()
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}