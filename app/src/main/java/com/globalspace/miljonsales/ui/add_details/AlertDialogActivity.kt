package com.globalspace.miljonsales.ui.add_details

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.databinding.LayoutOtherDialogBinding


class AlertDialogActivity(context: Context) : Dialog(context) {

    fun showdialog(message: String) {
        val alertDialog = AlertDialog.Builder(context)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Alert")
            .setMessage(message)
            .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, i ->
                dialog.dismiss()
            })
            .show()
    }

    fun othershowdialog(addDetailsViewModel: AddDetailsViewModel) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Other Reason")

        val customLayout: View = layoutInflater
            .inflate(
                R.layout.layout_other_dialog,
                null
            )
        val binding = LayoutOtherDialogBinding.bind(customLayout)
        binding.otherdata = addDetailsViewModel
        builder.setView(customLayout)
        builder
            .setPositiveButton(
                "OK"
            ) { dialog, which -> // send data from the
                // AlertDialog to the Activity
                try {
                    if (binding.edittextReason.text.toString().equals("")) {
                        Toast.makeText(context, "Please Enter Other Reason", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        dialog.dismiss()
                    }
                } catch (e: Exception) {
                    val msg = e.message
                }
            }
        builder
            .setNegativeButton(
                "Close"
            ) { dialog, which -> // send data from the
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }

    fun showdialog(activity: AddDetailsActivity) {
        val alertDialog = AlertDialog.Builder(context)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Alert")
            .setMessage("Do you wish to go back without Saving the Data?")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, i ->
               activity.finish()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, i ->
                dialog.dismiss()
            })
            .show()
    }

}