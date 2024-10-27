package com.arvind.timeslotmusic.domain.models

import com.arvind.timeslotmusic.utils.MusicType

data class TimeSlot(
    val startTime: Int,
    val endTime: Int,
    val type: MusicType
) {
    fun overlapsWith(other: TimeSlot): Boolean {
        return (startTime < other.endTime && endTime > other.startTime)
    }
}

