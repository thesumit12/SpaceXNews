package com.slakra.spacexnews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.slakra.common.ProgressState
import com.slakra.domain.arch.ResultState
import com.slakra.domain.entity.ArticleEntity
import com.slakra.domain.usecase.GetNewsArticleFromServerUseCase
import com.slakra.domain.usecase.GetSearchResultsUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()


    @Test
    fun getNewsArticleSuccessTest() {
        testCoroutineRule.runBlockingTest {
            val getNewsArticleFromServerUseCase: GetNewsArticleFromServerUseCase = mockk()
            val getSearchResultsUseCase: GetSearchResultsUseCase = mockk()
            val testContextProvider = TestContextProvider()

            val articleEntity = ArticleEntity("id", "title", "url","imageUrl", "summary")
            val mockResult = ResultState.Success(arrayListOf(articleEntity))
            coEvery { getNewsArticleFromServerUseCase.execute() } returns mockResult
            every { getSearchResultsUseCase.execute(any()) } returns createMockList()

            val viewModel = MainViewModel(getNewsArticleFromServerUseCase, getSearchResultsUseCase, testContextProvider)
            assertEquals(viewModel.searchString.get(), "")
            assertEquals(viewModel.articleLiveData.value, null)

            viewModel.getNewsArticles()
            assertEquals(viewModel.state.value, ProgressState.SUCCESS)
            assertEquals(viewModel.articleLiveData.value?.size, 1)
        }
    }

    @Test
    fun getNewsArticleFailureTest() {
        testCoroutineRule.runBlockingTest {
            val getNewsArticleFromServerUseCase: GetNewsArticleFromServerUseCase = mockk()
            val getSearchResultsUseCase: GetSearchResultsUseCase = mockk()
            val testContextProvider = TestContextProvider()

            val mockResult = ResultState.InvalidData
            coEvery { getNewsArticleFromServerUseCase.execute() } returns mockResult

            val viewModel = MainViewModel(getNewsArticleFromServerUseCase, getSearchResultsUseCase, testContextProvider)

            viewModel.getNewsArticles()
            assertEquals(viewModel.state.value, ProgressState.ERROR)
            assertEquals(viewModel.articleLiveData.value, null)
        }
    }

    private fun createMockList(): Flow<List<ArticleEntity>> = flow {
        val item1 = ArticleEntity("id1", "title1", "url1", "imageUrl1", "summary1")
        val item2 = ArticleEntity("id2", "title2", "url2", "imageUrl2", "summary2")
        val item3 = ArticleEntity("id3", "title3", "url3", "imageUrl3", "summary3")

        val list = arrayListOf(item1, item2, item3)

        emit(list)
    }
}