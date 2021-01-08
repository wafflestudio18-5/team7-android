package com.wafflestudio.written.ui.main.write

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.wafflestudio.written.databinding.FragmentDialogCloseBinding

class CloseDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentDialogCloseBinding
    internal lateinit var listener: CloseDialogListener

    interface CloseDialogListener {
        fun onDialogLeaveClick(dialog: DialogFragment)
        fun onDialogContinueClick(dialog: DialogFragment)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            binding = FragmentDialogCloseBinding.inflate(layoutInflater)
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
            binding.leaveButton.setOnClickListener {
                listener.onDialogLeaveClick(this)
            }
            binding.continueButton.setOnClickListener {
                listener.onDialogContinueClick(this)
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as CloseDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                (context.toString() + " must implement CloseDialogListener")
            )
        }
    }

}
