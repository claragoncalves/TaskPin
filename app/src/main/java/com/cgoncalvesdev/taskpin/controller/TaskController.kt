package com.cgoncalvesdev.taskpin.controller

import android.content.Context
import com.cgoncalvesdev.taskpin.model.pojo.Section
import com.cgoncalvesdev.taskpin.model.pojo.Task
import com.cgoncalvesdev.taskpin.model.pojo.TaskPinDB

class TaskController {
    companion object {

        fun saveTask(context: Context, task: Task) {
            TaskPinDB.getInstance(context)?.taskDBDao()?.saveTask(task)
        }

        fun getTasks(context: Context): MutableList<Task> {
            return TaskPinDB.getInstance(context)?.taskDBDao()?.getTasks() ?: mutableListOf()
        }

        fun saveTaskNotificationIsSet(context: Context, taskId: Long, isSet: Boolean){
            TaskPinDB.getInstance(context)?.taskDBDao()?.saveTaskNotificationIsSet(taskId, isSet)
        }

        fun saveIsTaskDone(context: Context, taskId: Long, isDone: Boolean){
            TaskPinDB.getInstance(context)?.taskDBDao()?.saveIsTaskDone(taskId, isDone)
        }

        fun getTasksDividedBySections(context: Context): MutableList<Section>{
            val sections = mutableListOf<Section>()
            val sectionToDo = Section("To Do")
            val sectionDone = Section("Done")
            val taskList = getTasks(context)
            for (task in taskList) {
                if (task.isDone){
                    sectionDone.taskList.add(task)
                } else{
                    sectionToDo.taskList.add(task)
                }
            }
            sections.add(sectionToDo)
            sections.add(sectionDone)
            return sections
        }
    }
}