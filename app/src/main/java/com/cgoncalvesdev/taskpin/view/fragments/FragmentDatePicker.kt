package com.cgoncalvesdev.taskpin.view.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import java.util.*


class FragmentDatePicker : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        return DatePickerDialog(context,this, year, month, day)
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        val datePickListener = parentFragment as DatePickListener
        datePickListener.datePicked(p0)
    }



    interface DatePickListener{
        fun datePicked(datePicker: DatePicker?)
    }
}
