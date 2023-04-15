package com.example.kotlinprac.paging.prac

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.paging.*
import com.example.kotlinprac.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class GithubViewModelTest {

    private lateinit var service: GithubApiService
    private lateinit var repository: GithubRepository
    private lateinit var viewModel: GithubViewModel

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)

        service = mockk(relaxed = true)
        repository = GithubRepository(service)
        viewModel = GithubViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `test empty query`() = runTest {
        // Given
        val emptyQuery = ""

        // When
        val adapter = RepoListAdapter()
        adapter.submitData(PagingData.empty())
        val result = adapter.loadStateFlow.first()

        // Then
        assertTrue(result.refresh is LoadState.NotLoading)
    }

}