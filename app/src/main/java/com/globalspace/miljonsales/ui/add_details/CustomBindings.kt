package com.globalspace.miljonsales.ui.add_details

import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.ui.add_details_dashboard.FetchSpeciality


@BindingAdapter("IcontouchListener")
fun bindingsetTouchListener(textView: TextView, addDetailsViewModel: AddDetailsViewModel) {
    textView.setOnTouchListener(View.OnTouchListener { view, event ->
        val DRAWABLE_RIGHT = 2// Check if the button is PRESSED
        try {
            val data1 = textView.compoundDrawables.get(DRAWABLE_RIGHT)
            if ((event.action == MotionEvent.ACTION_DOWN) && data1 != null) {
                if (event.rawX >= textView.getRight() - textView.getCompoundDrawables()
                        .get(DRAWABLE_RIGHT).getBounds().width()
                ) {

                    addDetailsViewModel.CallAlert(addDetailsViewModel.CurrentFlag, "Enter Other ${addDetailsViewModel.CurrentFlag}")
                }
            }
            return@OnTouchListener true
        } catch (e: Exception) {
            val msg = e.printStackTrace()
        }
        view?.onTouchEvent(event) ?: true
    })

}