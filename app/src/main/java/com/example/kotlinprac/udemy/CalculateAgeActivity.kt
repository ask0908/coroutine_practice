package com.example.kotlinprac.udemy

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityCalculateAgeBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

class CalculateAgeActivity :
    BaseActivity<ActivityCalculateAgeBinding>(R.layout.activity_calculate_age) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            btnDatePicker.setOnClickListener {
                clickDatePicker()
            }
        }
    }

    private fun clickDatePicker() {
        val year: Int
        val month: Int
        val day: Int
        Calendar.getInstance().also {
            year = it.get(Calendar.YEAR)
            month = it.get(Calendar.MONTH)
            day = it.get(Calendar.DAY_OF_MONTH)
        }
        DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                Toast.makeText(this, "선택한 연도 : $year, 월 : $month, 일 : $dayOfMonth", Toast.LENGTH_SHORT).show()
                val selectedDate = "$year-${month + 1}-$dayOfMonth"
                binding.tvSelectedDate.text = selectedDate

                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val theDate = sdf.parse(selectedDate)
                theDate?.let {
                    // 1970.01.01부터 선택한 날짜 사이의 시간을 구함
                    val selectedDateInMinutes = it.time / 60_000
                    // 1970.01.01부터 지금까지의 지난 시간을 밀리초 단위로 가져옴
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let { date ->
                        val currentDateInMinutes = date.time / 60_000
                        val diffInMinutes = currentDateInMinutes - selectedDateInMinutes
                        binding.tvDayInMilliSeconds.text = "${diffInMinutes}밀리초"

                        val selectedCal = Calendar.getInstance().apply {
                            time = sdf.parse(selectedDate)
                        }
                        val currentCal = Calendar.getInstance().apply {
                            time = sdf.parse(sdf.format(System.currentTimeMillis()))
                        }

                        val diffInMillis = currentCal.timeInMillis - selectedCal.timeInMillis
                        val daysDiff = TimeUnit.MILLISECONDS.toDays(diffInMillis)
                        binding.tvYearMonthDay.text = "${daysDiff}일"
                    }
                }
            },
            year, month, day
        ).apply {
            // 1시간 : 360만 밀리초
            // 1시간 x 24 = 8,640만 밀리초 -> 과거 날짜만 선택할 수 있게
            datePicker.maxDate = System.currentTimeMillis() - 86_400_000
        }.show()
    }
}