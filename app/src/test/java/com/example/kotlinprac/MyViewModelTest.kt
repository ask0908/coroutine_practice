package com.example.kotlinprac

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.kotlinprac.paging.prac.GithubRepository
import com.example.kotlinprac.paging.prac.GithubViewModel
import io.mockk.mockk
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MyViewModelTest {
    @get:Rule
    var instantExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var repository: GithubRepository
    private lateinit var viewModel: GithubViewModel

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        viewModel = GithubViewModel(repository)
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
}