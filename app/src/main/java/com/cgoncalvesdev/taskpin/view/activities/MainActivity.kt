package com.cgoncalvesdev.taskpin.view.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.cgoncalvesdev.taskpin.R
import com.cgoncalvesdev.taskpin.model.pojo.Task
import com.cgoncalvesdev.taskpin.util.NotificationReceiver
import com.cgoncalvesdev.taskpin.view.fragments.FragmentMainTaskList
import com.cgoncalvesdev.taskpin.view.fragments.FragmentTaskDetail
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

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
        setupToolbar()
    }

    fun setupToolbar(){
        val toolbar: Toolbar = findViewById(R.id.toolbar_include)
        toolbar.title = resources.getString(R.string.app_name)
        toolbar.setNavigationIcon(R.drawable.ic_pin)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId?.equals(R.id.add_action)!!){
            var intent = Intent(this, NewTaskActivity::class.java)
            startActivity(intent)
            return true
        }else {
            return super.onOptionsItemSelected(item)
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
