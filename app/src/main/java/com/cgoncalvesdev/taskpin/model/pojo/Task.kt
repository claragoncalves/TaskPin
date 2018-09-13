package com.cgoncalvesdev.taskpin.model.pojo

import android.arch.persistence.room.*
import android.support.v7.widget.DialogTitle
import com.cgoncalvesdev.taskpin.util.TimeConverter
import java.io.Serializable
import java.util.*

@Entity(tableName = "tasks")
class Task: Serializable{
    @PrimaryKey(autoGenerate = true)var id: Long = 0
    @ColumnInfo(name = "title")var title: String = ""
    @Ignore var content: TaskContent = TaskContentText("", "")
    @Ignore var priority: TaskPriority = TaskPriority("normal",8792886)
    @ColumnInfo(name = "scheduled_at")@TypeConverters((TimeConverter::class))var scheduledAt: Date = Date()
    @ColumnInfo(name = "is_set")var isSet: Boolean = false
    @ColumnInfo(name = "is_done")var isDone: Boolean = false
    @ColumnInfo(name = "created_at")@TypeConverters((TimeConverter::class))var createdAt: Date = Date()

    constructor(id: Long, title: String, scheduledAt: Date, isSet: Boolean, isDone: Boolean, createdAt: Date){
        this.id = id
        this.title = title
        this.scheduledAt = scheduledAt
        this.isSet = isSet
        this.isDone = isDone
        this.createdAt = createdAt
    }

    @Ignore
    constructor(title: String, content: TaskContent, priority: TaskPriority, scheduledAt: Date){
        this.title = title
        this.content = content
        this.priority = priority
        this.scheduledAt = scheduledAt
    }

    @Ignore
    constructor(title: String, content: TaskContent, priority: TaskPriority){
        this.title = title
        this.content = content
        this.priority = priority
        this.createdAt = Date()
        this.scheduledAt = createdAt
    }

}