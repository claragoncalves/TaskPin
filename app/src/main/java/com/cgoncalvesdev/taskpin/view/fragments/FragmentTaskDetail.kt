package com.cgoncalvesdev.taskpin.view.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cgoncalvesdev.taskpin.R
import com.cgoncalvesdev.taskpin.model.pojo.Task
import kotlinx.android.synthetic.main.fragment_task_detail.view.*

class FragmentTaskDetail : Fragment() {

    companion object {
        private const val KEY_TASK = "task"
        fun fragmentTaskDetailFactory(task: Task) : FragmentTaskDetail{
            val fragmentTaskDetail = FragmentTaskDetail()
            val bundle = Bundle()
            bundle.putSerializable(KEY_TASK, task)
            fragmentTaskDetail.arguments = bundle
            return fragmentTaskDetail
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_task_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        val task = bundle?.getSerializable(KEY_TASK) as Task
        view.task_detail_title.text = task.title

    }


}
