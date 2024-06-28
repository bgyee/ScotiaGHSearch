package com.interview.scotiaghsearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.interview.scotiaghsearch.data.model.UserRepo
import com.interview.scotiaghsearch.data.repository.GitHubRepository
import com.interview.scotiaghsearch.ui.list.ListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ListViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val gitHubRepository: GitHubRepository = mockk(relaxed = true)

    private val listViewModel = ListViewModel(gitHubRepository)

    @Test
    fun `getRepoList loads empty list when getUserRepos returns empty list`() = runTest {
        coEvery { gitHubRepository.getUserRepos(any()) } returns emptyList()

        val expected = emptyList<UserRepo>()
        listViewModel.getRepoList("test")
        val actual = listViewModel.userRepoList.value

        assertEquals(expected, actual)
    }

    @Test
    fun `getRepoList loads null when getUserRepos returns null`() = runTest {
        coEvery { gitHubRepository.getUserRepos(any()) } returns null

        val expected = null
        listViewModel.getRepoList("test")
        val actual = listViewModel.userRepoList.value

        assertEquals(expected, actual)
    }
}