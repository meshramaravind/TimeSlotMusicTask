package com.arvind.timeslotmusic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.arvind.timeslotmusic.domain.models.Result
import com.arvind.timeslotmusic.domain.models.TimeSlot
import com.arvind.timeslotmusic.domain.usecase.TimeSlotUseCase
import com.arvind.timeslotmusic.utils.MusicType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MusicSlotViewModel @Inject constructor(
    private val timeSlotUseCase: TimeSlotUseCase
) : ViewModel() {

    val whiteNoiseSlot: LiveData<TimeSlot?> = timeSlotUseCase.whiteNoiseSlot
    val calmingMusicSlot: LiveData<TimeSlot?> = timeSlotUseCase.calmingMusicSlot
    val toastMessage: LiveData<Result> = timeSlotUseCase.toastMessage

    fun setTimeSlot(startTime: Int, endTime: Int, type: MusicType) {
        timeSlotUseCase.setTimeSlot(startTime, endTime, type)
    }

//    fun getTimeSlot(type: MusicType): TimeSlot? {
//        return timeSlotUseCase.getTimeSlot(type)
//    }
}
