package com.lipssoftware.wargag.data.entities.dbentities

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.lipssoftware.wargag.data.entities.Author
import java.util.*

class DateConverter {

    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value);
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long = date.time
}