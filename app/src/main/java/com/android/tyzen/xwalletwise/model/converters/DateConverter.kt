package com.android.tyzen.xwalletwise.model.converters

import androidx.room.TypeConverter
import java.util.*


open class DateConverter {
    @TypeConverter
    fun toDate(datetime: Long?): Date? {
        return datetime?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(datetime: Date?): Long? {
        return datetime?.time
    }
}