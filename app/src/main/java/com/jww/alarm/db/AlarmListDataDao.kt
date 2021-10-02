package com.jww.alarm.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlarmListDataDao {
    @Query("SELECT * FROM AlarmData")
    fun getAlarmList(): LiveData<List<AlarmDataEntity>>

    @Insert
    fun insert(alarmData: AlarmDataEntity)

    @Query("UPDATE AlarmData set hour = :hour, min = :min, year = :year, month = :month, dayOfMonth = :dayOfMonth, sound = :sound, vibration = :vibration, soundFileName = :soundFileName, isActive = :isActive WHERE uid = :uid")
    fun update(
        uid: Int,
        hour: Int,
        min: Int,
        year: Int,
        month: Int,
        dayOfMonth: Int,
        sound: Boolean,
        vibration: Boolean,
        soundFileName: String,
        isActive: Boolean
    )

    @Delete
    fun delete(alarmData: AlarmDataEntity)

    @Query("DELETE FROM alarmdata WHERE uid = :uid")
    fun delete(uid: Int)
}