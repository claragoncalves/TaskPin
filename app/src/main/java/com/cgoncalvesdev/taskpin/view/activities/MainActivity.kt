package com.cgoncalvesdev.taskpin.view.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.cgoncalvesdev.taskpin.R
import com.cgoncalvesdev.taskpin.model.pojo.Task
import com.cgoncalvesdev.taskpin.util.NotificationReceiver
import com.cgoncalvesdev.taskpin.view.fragments.FragmentMainTaskList
import com.cgoncalvesdev.taskpin.view.fragments.FragmentTaskDetail
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FragmentMainTaskList.TaskTapListener, FragmentMainTaskList.TaskNotificationSetter {

    val fragmentMainTaskList = FragmentMainTaskList()

    companion object{
        const val FRAGMENT_MAIN_TASK_LIST = "fragmentMainTaskList"
        const val FRAGMENT_TASK_DETAIL = "fragmentTaskDetail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        placeFragment(fragmentMainTaskList, FRAGMENT_MAIN_TASK_LIST)
        button_add_task.setOnClickListener {
            val intent = Intent(this, NewTaskActivity::class.java)
            startActivity(intent)
        }
    }

    fun placeFragment(fragment: Fragment, tag: String){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.main_activity_container,fragment,tag)
        transaction.commit()
        if (tag.equals(FRAGMENT_TASK_DETAIL)){
            transaction.addToBackStack(null)
        }
    }

    override fun taskTapped(task: Task) {
        placeFragment(FragmentTaskDetail.fragmentTaskDetailFactory(task), FRAGMENT_TASK_DETAIL)
    }

    override fun setNotificationOn(task: Task) {
        val notificationReceiver = NotificationReceiver()
        if (task.createdAt != task.scheduledAt) {
            notificationReceiver.scheduleNotification(this, task)
        } else {
            notificationReceiver.setNotification(this, task)
        }
    }

    override fun setNotificationOff(taskId: Int) {
        val notificationReceiver = NotificationReceiver()
        notificationReceiver.cancelNotification(this, taskId)
    }


}
