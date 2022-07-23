package com.example.kotlinprac.datastore.codelab.data

import android.icu.text.SimpleDateFormat
import kotlinx.coroutines.flow.flowOf
import java.util.*

object TasksRepository {
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)

    val tasks = flowOf(
        listOf(
            // 아래 2개는 Show completed tasks 체크박스를 눌러야 보임
            Task(
                name = "Open codelab",
                deadline = simpleDateFormat.parse("2020-07-03")!!,
                priority = TaskPriority.LOW,
                completed = true
            ),
            Task(
                name = "Import project",
                deadline = simpleDateFormat.parse("2020-04-03")!!,
                priority = TaskPriority.MEDIUM,
                completed = true
            ),
            Task(
                name = "Check out the code",
                deadline = simpleDateFormat.parse("2020-05-03")!!,
                priority = TaskPriority.LOW
            ),
            Task(
                name = "Read about DataStore", deadline = simpleDateFormat.parse("2020-06-03")!!,
                priority = TaskPriority.HIGH
            ),
            Task(
                name = "Implement each step",
                deadline = Date(),
                priority = TaskPriority.MEDIUM
            ),
            Task(
                name = "Understand how to use DataStore",
                deadline = simpleDateFormat.parse("2020-04-03")!!,
                priority = TaskPriority.HIGH
            ),
            Task(
                name = "Understand how to migrate to DataStore",
                deadline = Date(),
                priority = TaskPriority.HIGH
            )
        )
    )
}