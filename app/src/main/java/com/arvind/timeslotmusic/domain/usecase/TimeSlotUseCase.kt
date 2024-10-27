package com.arvind.timeslotmusic.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arvind.timeslotmusic.domain.models.Result
import com.arvind.timeslotmusic.domain.models.TimeSlot
import com.arvind.timeslotmusic.utils.MusicType

class TimeSlotUseCase {

    private val _whiteNoiseSlot = MutableLiveData<TimeSlot?>()
    val whiteNoiseSlot: LiveData<TimeSlot?> get() = _whiteNoiseSlot

    private val _calmingMusicSlot = MutableLiveData<TimeSlot?>()
    val calmingMusicSlot: LiveData<TimeSlot?> get() = _calmingMusicSlot

    private val _toastMessage = MutableLiveData<Result>()
    val toastMessage: LiveData<Result> get() = _toastMessage

    fun setTimeSlot(startTime: Int, endTime: Int, type: MusicType) {
        val newSlot = TimeSlot(startTime, endTime, type)
        when (type) {
            MusicType.WHITE_NOISE -> _whiteNoiseSlot.value = newSlot
            MusicType.CALMING_MUSIC -> _calmingMusicSlot.value = newSlot
        }
        checkOverlapAndAdjust()
    }

    private fun checkOverlapAndAdjust() {
        _whiteNoiseSlot.value?.let { whiteNoise ->
            _calmingMusicSlot.value?.let { calmingMusic ->
                when {
                    // If times are identical, turn off Calming Music
                    whiteNoise.startTime == calmingMusic.startTime && whiteNoise.endTime == calmingMusic.endTime -> {
                        _calmingMusicSlot.value = null
                        _toastMessage.value =
                            Result("Calming Music turned off due to identical time slots with White Noise.")

                    }

                    // If there’s an overlap, adjust Calming Music
                    whiteNoise.overlapsWith(calmingMusic) -> {
                        when {
                            // If fully overlapped, turn off Calming Music
                            /**
                             * White Noise: 10:00 AM - 12:00 PM
                             * Calming Music: 10:30 AM - 11:30 AM
                             */
                            calmingMusic.startTime >= whiteNoise.startTime && calmingMusic.endTime <= whiteNoise.endTime -> {
                                _calmingMusicSlot.value = null
                                _toastMessage.value =
                                    Result("Calming Music fully overlapped by White Noise and turned off.")
                            }

                            // If start time overlaps, adjust Calming Music's start time
                            /**
                             * White Noise: 10:00 AM - 12:00 PM
                             * Calming Music: 11:00 AM - 1:00 PM
                             */

                            calmingMusic.startTime < whiteNoise.endTime -> {
                                _calmingMusicSlot.value =
                                    calmingMusic.copy(startTime = whiteNoise.endTime)
                                _toastMessage.value =
                                    Result("Adjusted Calming Music start time due to overlap with White Noise.")
                            }

                            /**
                             * White Noise: 2:00 PM - 4:00 PM
                             * Calming Music: 3:30 PM - 5:00 PM
                             */

                        }
                    }

                    else -> _toastMessage.value =
                        Result("No overlap detected. Both slots can coexist.")
                }
            }
        }
    }
}
