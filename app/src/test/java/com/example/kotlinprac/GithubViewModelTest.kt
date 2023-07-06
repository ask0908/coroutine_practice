package com.example.kotlinprac

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.paging.*
import com.example.kotlinprac.paging.prac.GithubRepository
import com.example.kotlinprac.paging.prac.GithubViewModel
import com.example.kotlinprac.paging.prac.Repo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class GithubViewModelTest {
    @get:Rule
    var instantExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var repository: GithubRepository
    private lateinit var viewModel: GithubViewModel
    private lateinit var observer: Observer<PagingData<Repo>>

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        repository = mockk(relaxed = true)
        viewModel = GithubViewModel(repository)
        observer = mockk(relaxed = true)
        viewModel.repos.observeForever(observer)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
        viewModel.repos.removeObserver(observer)
    }

    @Test
    fun `특정 검색어를 입력하고 검색한 경우 LiveData의 값이 바뀐다`() {
        // Given
        val query = "android"

        // When
        viewModel.searchRepos(query)

        // Then
        assertEquals(viewModel.searchQuery.value, query)
    }

    @Test
    fun `test`() = runTest {
        // Given
        val query1 = "android"
        val query2 = "kotlin"

        val pagingData1 = PagingData.from(listOf(Repo(id = 1, name = "Repo1", fullName = "fullName1", description = "", url = "", stars = 1, forks = 1, language = "language1")))
        val pagingData2 = PagingData.from(listOf(Repo(id = 2, name = "Repo2", fullName = "fullName2", description = "", url = "", stars = 2, forks = 2, language = "language2")))

        // Mock repository responses
        coEvery { repository.getSearchRepoResult(query1) } returns flowOf(pagingData1).asLiveData()
        coEvery { repository.getSearchRepoResult(query2) } returns flowOf(pagingData2).asLiveData()

        // When
        viewModel.searchRepos(query1)

        // Then
        val value1 = viewModel.repos.getOrAwaitValue() // this is an extension function that waits for the LiveData value
        assertEquals(value1, pagingData1)

        // When
        viewModel.searchRepos(query2)

        // Then
        val value2 = viewModel.repos.getOrAwaitValue() // this is an extension function that waits for the LiveData value
        assertEquals(value2, pagingData2)
    }
}