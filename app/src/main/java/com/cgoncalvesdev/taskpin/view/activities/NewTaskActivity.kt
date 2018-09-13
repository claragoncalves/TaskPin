package com.cgoncalvesdev.taskpin.view.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.cgoncalvesdev.taskpin.R
import com.cgoncalvesdev.taskpin.controller.TaskController
import com.cgoncalvesdev.taskpin.model.pojo.Task
import com.cgoncalvesdev.taskpin.view.fragments.FragmentAddNewTask
import com.cgoncalvesdev.taskpin.view.fragments.FragmentDatePicker
import com.cgoncalvesdev.taskpin.view.fragments.FragmentTimePicker

class NewTaskActivity : AppCompatActivity(),FragmentAddNewTask.TaskAddedListener {

    private val fragmentAddNewTask = FragmentAddNewTask()

    companion object {
        const val FRAGMENT_ADD_NEW_TASK = "fragmentAddNewTask"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)

        placeFragment(fragmentAddNewTask, FRAGMENT_ADD_NEW_TASK)

    }

    fun placeFragment(fragment: Fragment, tag: String){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.new_task_activity_container,fragment,tag)
        transaction.commit()
    }

    override fun taskAdded(task: Task) {
        TaskController.saveTask(this,task)
        finish()
    }

}
