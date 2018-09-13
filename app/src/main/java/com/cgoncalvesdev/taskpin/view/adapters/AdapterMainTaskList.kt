package com.cgoncalvesdev.taskpin.view.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cgoncalvesdev.taskpin.R
import com.cgoncalvesdev.taskpin.controller.TaskController
import com.cgoncalvesdev.taskpin.model.pojo.Task
import com.cgoncalvesdev.taskpin.view.fragments.FragmentMainTaskList
import kotlinx.android.synthetic.main.cell_main_task_list.view.*

class AdapterMainTaskList(val taskTapListener: FragmentMainTaskList.TaskTapListener, val taskNotificationSetter: FragmentMainTaskList.TaskNotificationSetter): RecyclerView.Adapter<AdapterMainTaskList.TaskViewHolder>() {
    var taskList : MutableList<Task> = mutableListOf()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TaskViewHolder {
        return TaskViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.cell_main_task_list,p0,false))
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(p0: TaskViewHolder, p1: Int) {
        p0.bindTask(taskList[p1], taskTapListener, taskNotificationSetter)
    }

    class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bindTask (task: Task, taskTapListener: FragmentMainTaskList.TaskTapListener, taskNotificationSetter: FragmentMainTaskList.TaskNotificationSetter){
            with(itemView){
                setOnClickListener { taskTapListener.taskTapped(task) }
                cell_main_task_list_priority_circle.setBackgroundColor(task.priority.color)
                cell_main_task_list_title.text = task.title
                cell_main_task_list_description.text = task.scheduledAt.toString()
                switchNotificationImage(task.isSet)
                if (task.isDone){
                    cell_main_task_list_button_notification.setImageResource(R.drawable.ic_check_black_24dp)
                }else {
                    cell_main_task_list_button_notification.setOnClickListener {
                        if (!task.isSet) {
                            taskNotificationSetter.setNotificationOn(task)
                        } else {
                            taskNotificationSetter.setNotificationOff(task.id.toInt())
                        }
                        task.isSet = !task.isSet
                        TaskController.saveTaskNotificationIsSet(itemView.context, task.id, task.isSet)
                        switchNotificationImage(task.isSet)
                    }
                }
            }
        }

        fun switchNotificationImage(isSet: Boolean){
            if (isSet){
                itemView.cell_main_task_list_button_notification.setImageResource(R.drawable.ic_notifications_on_black_24dp)
            } else {
                itemView.cell_main_task_list_button_notification.setImageResource(R.drawable.ic_notifications_none_black_24dp)
            }
        }
    }

}