package com.jww.alarm.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "AlarmData")
data class AlarmDataEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    val hour: Int = 0,
    val min: Int = 0,
    val year: Int = 0,
    val month: Int = 0,
    val dayOfMonth: Int = 0,
    val sound: Boolean = true,
    val vibration: Boolean = true,
    val soundFileName: String = "소리 파일이 없습니다.",
    val isActive: Boolean = true
)
