package com.example.kotlinprac.datastore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.kotlinprac.datastore.data.SortOrder
import com.example.kotlinprac.datastore.data.Task
import com.example.kotlinprac.datastore.data.TasksRepository
import com.example.kotlinprac.datastore.data.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

data class TasksUiModel(
    val tasks: List<Task>,
    val showCompleted: Boolean,
    val sortOrder: SortOrder
)

class TasksViewModel(
    repository: TasksRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    // Keep the show completed filter as a stream of changes
    // 표시 완료 필터를 변경 스트림으로 유지
    private val showCompletedFlow = MutableStateFlow(false)

    // Keep the sort order as a stream of changes
    // 정렬 순서를 변경의 흐름으로 유지
    private val sortOrderFlow = userPreferencesRepository.sortOrderFlow

    // Every time the sort order, the show completed filter or the list of tasks emit, we should recreate the list of tasks
    // 정렬 순서, 완료된 필터 표시 또는 작업 목록이 표시될 때마다 작업 목록을 다시 만들어야 한다
    private val tasksUiModelFlow = combine(
        repository.tasks,
        showCompletedFlow,
        sortOrderFlow
    ) { tasks: List<Task>, showCompleted: Boolean, sortOrder: SortOrder ->
        return@combine TasksUiModel(
            tasks = filterSortTasks(tasks, showCompleted, sortOrder),
            showCompleted = showCompleted,
            sortOrder = sortOrder
        )
    }
    val tasksUiModel = tasksUiModelFlow.asLiveData()

    private fun filterSortTasks(
        tasks: List<Task>,
        showCompleted: Boolean,
        sortOrder: SortOrder
    ): List<Task> {
        // filter the tasks
        val filteredTasks = if (showCompleted) {
            tasks
        } else {
            tasks.filter { !it.completed }
        }
        // sort the tasks
        return when (sortOrder) {
            SortOrder.NONE -> filteredTasks
            SortOrder.BY_DEADLINE -> filteredTasks.sortedByDescending { it.deadline }
            SortOrder.BY_PRIORITY -> filteredTasks.sortedBy { it.priority }
            SortOrder.BY_DEADLINE_AND_PRIORITY -> filteredTasks.sortedWith(
                compareByDescending<Task> { it.deadline }.thenBy { it.priority }
            )
        }
    }

    fun showCompletedTasks(show: Boolean) {
        showCompletedFlow.value = show
    }

    fun enableSortByDeadline(enable: Boolean) = userPreferencesRepository.enableSortByDeadline(enable)

    fun enableSortByPriority(enable: Boolean) = userPreferencesRepository.enableSortByPriority(enable)
}

class TasksViewModelFactory(
    private val repository: TasksRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TasksViewModel(repository, userPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}