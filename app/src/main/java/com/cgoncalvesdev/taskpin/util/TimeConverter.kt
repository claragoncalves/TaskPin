package com.cgoncalvesdev.taskpin.util

import android.arch.persistence.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class TimeConverter{

        @TypeConverter
        fun longToDate(value: Long): Date{
            return Date(value)
        }

        @TypeConverter
        fun dateToLong(date: Date): Long{
            return date.time
        }

}