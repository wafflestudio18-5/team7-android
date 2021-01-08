package com.wafflestudio.written.ui.main.write

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.wafflestudio.written.databinding.FragmentDialogCloseBinding
import com.wafflestudio.written.ui.main.my.detail_posting.MyDetailPostingViewModel

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
