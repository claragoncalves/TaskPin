package com.cgoncalvesdev.taskpin.model.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.cgoncalvesdev.taskpin.model.pojo.Task

@Dao
interface TaskDBDao{
    @Insert
    fun saveTask(task: Task): Long

    @Query("SELECT * FROM tasks")
    fun getTasks(): MutableList<Task>

    @Query("UPDATE tasks SET is_set = :isSet WHERE id = :taskId")
    fun saveTaskNotificationIsSet(taskId: Long, isSet: Boolean)

    @Query("UPDATE tasks SET is_done = :isDone WHERE id = :taskId")
    fun saveIsTaskDone(taskId: Long, isDone: Boolean)

}