package com.wafflestudio.written.ui.main.write

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.wafflestudio.written.R

class CloseDialogFragment : DialogFragment() {

    internal lateinit var listener: CloseDialogListener

    interface CloseDialogListener {
        fun onDialogLeaveClick(dialog: DialogFragment)
        fun onDialogContinueClick(dialog: DialogFragment)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            builder.setView(inflater.inflate(R.layout.fragment_dialog_close, null))
            builder.setTitle(R.string.close_dialog_title)
            val items = arrayOf(R.string.close_dialog_leave, R.string.close_dialog_continue)
//            builder.setItems(items, DialogInterface.OnClickListener { dialog, which ->
//                when (which) {
//                    0 -> listener.onDialogLeaveClick(this)
//                    1 -> listener.onDialogContinueClick(this)
//                }
//            })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

        //        val builder: AlertDialog.Builder? = activity?.let {
//            AlertDialog.Builder(it)
//        }
//
//        builder?.setTitle(R.string.close_dialog_title)
//        builder?.setItems(items, DialogInterface.OnClickListener { dialog, which ->
//            when (which) {
//                0 -> listener.onDialogLeaveClick(this)
//                1 -> listener.onDialogContinueClick(this)
//            }
//        })
//        builder?.create()
//        dialog?.show()

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