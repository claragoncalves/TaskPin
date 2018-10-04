package com.cgoncalvesdev.taskpin.view.adapters

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cgoncalvesdev.taskpin.R
import com.cgoncalvesdev.taskpin.model.pojo.Section
import com.cgoncalvesdev.taskpin.view.fragments.FragmentMainTaskList
import kotlinx.android.synthetic.main.cell_section_task_list.view.*


class AdapterSectionTaskList(val taskTapListener: FragmentMainTaskList.TaskTapListener, val taskNotificationSetter: FragmentMainTaskList.TaskNotificationSetter): RecyclerView.Adapter<AdapterSectionTaskList.SectionViewHolder>(){
    var sectionList : MutableList<Section> = mutableListOf()


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SectionViewHolder {
        return SectionViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.cell_section_task_list,p0,false))
    }

    override fun getItemCount(): Int {
        return sectionList.size
    }

    override fun onBindViewHolder(p0: SectionViewHolder, p1: Int) {
        p0.bindSection(sectionList[p1], taskTapListener, taskNotificationSetter)
    }


    class SectionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindSection(section: Section, taskTapListener: FragmentMainTaskList.TaskTapListener, taskNotificationSetter: FragmentMainTaskList.TaskNotificationSetter){
            with(itemView){
                task_list_section_title.text = section.name
                if (section.taskList.size > 0) {
                    task_list_section_nothing.visibility = View.GONE
                    val adapter = AdapterMainTaskList(taskTapListener, taskNotificationSetter)
                    adapter.taskList = section.taskList
                    recyclerview_section_task_list.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                    recyclerview_section_task_list.adapter = adapter
                } else {
                    task_list_section_nothing.visibility = View.VISIBLE
                    task_list_section_nothing.text = "Nothing to show here"
                }
            }
        }
    }
}