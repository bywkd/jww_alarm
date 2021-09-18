package com.jww.alarm.models

data class AlarmItemModel(
    val amAndPm: String = "AM",
    val hour: String = "01",
    val min: String = "55",
    val time: String = "${System.currentTimeMillis()} ~ ${System.currentTimeMillis()}",
    val sound: Boolean = true,
    val vibration: Boolean = true,
    val soundFileName: String = "파일 이름",
    val isActive: Boolean = true
)
