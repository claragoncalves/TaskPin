package com.cgoncalvesdev.taskpin.model.pojo

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.cgoncalvesdev.taskpin.model.dao.TaskDBDao
import com.cgoncalvesdev.taskpin.util.TimeConverter

@Database(version = 1, entities = [(Task::class)/*,(TaskPriority::class)*/], exportSchema = false)
@TypeConverters(TimeConverter::class)
abstract class TaskPinDB: RoomDatabase() {
    abstract fun taskDBDao(): TaskDBDao

    companion object {
        private val databaseName = "taskpin_db"

        private var instance: TaskPinDB? = null

        fun getInstance(context: Context):TaskPinDB?{
            if (instance==null){
                instance = Room.databaseBuilder(context,TaskPinDB::class.java, databaseName).allowMainThreadQueries().build()
            }
            return instance
        }


        fun destroyInstance() {
            instance = null
        }
    }
}