package com.arvind.timeslotmusic.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.arvind.timeslotmusic.R
import com.arvind.timeslotmusic.databinding.ActivityMainBinding
import com.arvind.timeslotmusic.domain.models.TimeSlot
import com.arvind.timeslotmusic.utils.MusicType
import com.arvind.timeslotmusic.viewmodel.MusicSlotViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MusicSlotViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpInitView()

    }

    private fun setUpInitView() = with(binding) {
        viewModel.toastMessage.observe(this@MainActivity) { message ->
            message?.let {
                Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
            }
        }


        viewModel.whiteNoiseSlot.observe(this@MainActivity) {
            updateWhiteNoiseUI(it)
        }
        viewModel.calmingMusicSlot.observe(this@MainActivity) {
            updateCalmingMusicUI(it)
        }

        btnSubmit.setOnClickListener {
            val whiteNoiseStart =
                tpWhiteNoiseStart.hour * 60 + binding.tpWhiteNoiseStart.minute
            val whiteNoiseEnd = binding.tpWhiteNoiseEnd.hour * 60 + binding.tpWhiteNoiseEnd.minute
            val calmingMusicStart =
                tpCalmingMusicStart.hour * 60 + binding.tpCalmingMusicStart.minute
            val calmingMusicEnd =
                tpCalmingMusicEnd.hour * 60 + binding.tpCalmingMusicEnd.minute


            viewModel.setTimeSlot(whiteNoiseStart, whiteNoiseEnd, MusicType.WHITE_NOISE)
            viewModel.setTimeSlot(calmingMusicStart, calmingMusicEnd, MusicType.CALMING_MUSIC)
        }


    }

    private fun updateWhiteNoiseUI(slot: TimeSlot?) {
        binding.tvWhiteNoiseSlot.text = slot?.let {
            val formattedStart = formatTime(it.startTime)
            val formattedEnd = formatTime(it.endTime)
            "White Noise: $formattedStart - $formattedEnd"
        } ?: "Not Set"
    }

    private fun updateCalmingMusicUI(slot: TimeSlot?) {
        binding.tvCalmingMusicSlot.text = slot?.let {
            val formattedStart = formatTime(it.startTime)
            val formattedEnd = formatTime(it.endTime)
            "Calming Music: $formattedStart - $formattedEnd"
        } ?: "Not Set"
    }

    private fun formatTime(minutesFromMidnight: Int): String {
        val hours = (minutesFromMidnight / 60) % 24
        val minutes = minutesFromMidnight % 60
        val period = if (hours >= 12) "PM" else "AM"
        val adjustedHour = if (hours % 12 == 0) 12 else hours % 12

        return String.format("%02d:%02d %s", adjustedHour, minutes, period)
    }

}