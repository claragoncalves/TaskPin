package com.cgoncalvesdev.taskpin.view.fragments


import android.app.Dialog
import android.os.Bundle
import android.app.TimePickerDialog
import android.support.v4.app.DialogFragment
import android.widget.TimePicker
import java.util.*


class FragmentTimePicker : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        return TimePickerDialog(context,this, hour, minute, true)
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        val timePickListener = parentFragment as TimePickListener
        timePickListener.timePicked(p0)
    }

    interface TimePickListener{
        fun timePicked(timePicker: TimePicker?)
    }

}
