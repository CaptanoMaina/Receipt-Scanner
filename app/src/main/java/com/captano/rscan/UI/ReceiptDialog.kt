package com.captano.rscan.UI

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.captano.rscan.R

class ReceiptDialog(val content : String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            val view : View = inflater.inflate(R.layout.dialog_receipt, null)
            val textview :TextView = view.findViewById(R.id.textview_main_content)
            builder.setView(view)
                .setPositiveButton(R.string.Ok,
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })

            textview.text = content
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}