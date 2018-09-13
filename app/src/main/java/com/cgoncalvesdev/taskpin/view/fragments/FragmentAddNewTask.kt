package com.cgoncalvesdev.taskpin.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import com.cgoncalvesdev.taskpin.R
import com.cgoncalvesdev.taskpin.model.pojo.Task
import com.cgoncalvesdev.taskpin.model.pojo.TaskContentText
import com.cgoncalvesdev.taskpin.model.pojo.TaskPriority
import kotlinx.android.synthetic.main.fragment_add_new_task.*
import java.util.*


class FragmentAddNewTask : Fragment(), FragmentDatePicker.DatePickListener, FragmentTimePicker.TimePickListener {

    private var datePicker: DatePicker? = null
    private var timePicker: TimePicker? = null
    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minutes = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_new_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add_task_button_ok.setOnClickListener {
            val listener = context as TaskAddedListener
            listener.taskAdded(buildTask())
        }

        add_task_date.setOnClickListener{
            val datePickerFragment = FragmentDatePicker()
            datePickerFragment.show(childFragmentManager, "datePicker")
        }

        add_task_time.setOnClickListener {
            val newFragment = FragmentTimePicker()
            newFragment.show(childFragmentManager, "timePicker")
        }
    }

    fun buildTask() : Task{
        val priorityChequedId = add_task_priority_group.checkedRadioButtonId
        var taskPriority = TaskPriority("", Color.YELLOW)
        when (priorityChequedId){
            add_task_priority_low.id -> taskPriority = TaskPriority(add_task_priority_low.text.toString(), Color.BLUE)
            add_task_priority_normal.id -> taskPriority = TaskPriority(add_task_priority_normal.text.toString(), Color.BLACK)
            add_task_priority_high.id -> taskPriority = TaskPriority(add_task_priority_high.text.toString(),Color.RED)
        }

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minutes)
        val scheduledDate = calendar.time
        val task: Task
        if (scheduledDate.time <= Date().time){
            task = Task(add_task_title.text.toString(),
                    TaskContentText(add_task_content.text.toString(), add_task_content.text.toString()),
                    taskPriority)
        } else{
            task = Task(add_task_title.text.toString(),
                    TaskContentText(add_task_content.text.toString(), add_task_content.text.toString()),
                    taskPriority, scheduledDate)
        }
        return task
    }

    interface TaskAddedListener{
        fun taskAdded(task: Task)
    }

    override fun datePicked(datePicker: DatePicker?) {
        this.datePicker = datePicker
        day = datePicker?.dayOfMonth ?: 0
        month = datePicker?.month ?: 0
        year = datePicker?.year ?: 0
        buildTextDate()
    }

    override fun timePicked(timePicker: TimePicker?) {
        this.timePicker = timePicker
        hour = timePicker?.hour ?: 0
        minutes = timePicker?.minute ?: 0
        buildTextTime()
    }

    fun buildTextDate(){
        add_task_date.text = "Selected date: " + day + "/" + month + "/" + year
    }

    fun buildTextTime(){
        add_task_time.text = "Selected time: " + hour + ":" + minutes
    }
}
