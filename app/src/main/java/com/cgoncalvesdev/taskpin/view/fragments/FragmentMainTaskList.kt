package com.cgoncalvesdev.taskpin.view.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cgoncalvesdev.taskpin.R
import com.cgoncalvesdev.taskpin.controller.TaskController
import com.cgoncalvesdev.taskpin.model.pojo.Task
import com.cgoncalvesdev.taskpin.view.adapters.AdapterMainTaskList
import kotlinx.android.synthetic.main.fragment_main_task_list.view.*

class FragmentMainTaskList : Fragment() {
    private var adapter: AdapterMainTaskList? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.recyclerview_main_task_list


        adapter = AdapterMainTaskList(context as TaskTapListener, context as TaskNotificationSetter)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        adapter?.taskList = loadTaskList()
    }


    override fun onStart() {
        super.onStart()
        adapter?.taskList = loadTaskList()
    }

    fun loadTaskList(): MutableList<Task>{
        return TaskController.getTasks(context!!)
    }


    interface TaskTapListener{
        fun taskTapped(task: Task)
    }

    interface TaskNotificationSetter{
        fun setNotificationOn(task: Task)
        fun setNotificationOff(taskId: Int)
    }
}

