package com.wafflestudio.written.ui.main.write

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.wafflestudio.written.databinding.FragmentDialogCloseBinding

class CloseDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentDialogCloseBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            binding = FragmentDialogCloseBinding.inflate(layoutInflater)
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
            binding.leaveButton.setOnClickListener {
                requireActivity().finish()
            }
            binding.continueButton.setOnClickListener {
                dismiss()
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}
