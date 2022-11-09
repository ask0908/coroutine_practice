package com.example.kotlinprac.other

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivityDateTimeBinding
import kotlinx.android.synthetic.main.activity_date_time.*

class DateTimeActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName
    var date1 = "2022-11-10T10:10:00"
    var result: String = ""

    private val binding by lazy {
        ActivityDateTimeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ArrayAdapter.createFromResource(this, R.array.format_date, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            this.spinner.adapter = adapter

            this.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        1 -> {
                            var dayMonth = date1
                            dayMonth = dayMonth.convertDefaultWithoutTime(DateFormats.DEFAULT_FORMAT.format).toString()
                            result = dayMonth
                        }
                        2 -> {
                            var trimHour = date1
                            trimHour = trimHour.convertDateToYMDFullTimeDate(DateFormats.DEFAULT_FORMAT.format).toString()
                            result = trimHour
                        }
                        3 -> {
                            var timeAMPM = date1
                            timeAMPM = timeAMPM.convertDateToYMDFullTimeDate(DateFormats.DEFAULT_FORMAT.format)
                                .toString()
                            result = timeAMPM
                        }
                        4 -> {
                            var timeAMPM = date1
                            timeAMPM = timeAMPM.trimTimeFromDateMDY(
                                DateFormats.DEFAULT_FORMAT.format,
                            ).toString()
                            result = timeAMPM
                        }

                        5 -> {
                            var timeAMPM = date1
                            timeAMPM = timeAMPM.convertDateToDMYFullTimeDate(
                                DateFormats.DEFAULT_FORMAT.format
                            ).toString()
                            result = timeAMPM
                        }
                        6 -> {
                            var timeAMPM = date1
                            timeAMPM = timeAMPM.convertDateToYMD(
                                DateFormats.DEFAULT_FORMAT.format
                            ).toString()
                            result = timeAMPM
                        }
                        7 -> {
                            var timeAMPM = date1
                            timeAMPM = timeAMPM.convertDateToMonthNameYear(
                                DateFormats.DEFAULT_FORMAT.format
                            ).toString()
                            result = timeAMPM
                        }
                        8 -> {
                            var timeAMPM = date1
                            timeAMPM = timeAMPM.convertDateToWeekNameYear(
                                DateFormats.DEFAULT_FORMAT.format
                            ).toString()
                            result = timeAMPM
                        }
                        9 -> {
                            var timeAMPM = date1
                            timeAMPM = timeAMPM.convertDateMonthName(
                                DateFormats.DEFAULT_FORMAT.format
                            ).toString()
                            result = timeAMPM
                        }
                        10 -> {
                            var timeAMPM = date1
                            timeAMPM = timeAMPM.convertDateCustom(
                                DateFormats.DEFAULT_FORMAT.format
                            ).toString()
                            result = timeAMPM
                        }
                        11 -> {
                            var timeAMPM = date1
                            timeAMPM = timeAMPM.convertDateToAmPm(
                                DateFormats.DEFAULT_FORMAT.format
                            ).toString()
                            result = timeAMPM
                        }
                    }
                    text1.text = result
                    Toast.makeText(this@DateTimeActivity, "변환 결과 : $result", Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {}

            }
        }
    }
}