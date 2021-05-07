package com.slakra.domain.usecase

import com.slakra.domain.entity.ArticleEntity
import com.slakra.domain.repository.NewsArticleStore
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import org.junit.Assert.*
import org.junit.Test

@ExperimentalCoroutinesApi
class GetSearchResultsUseCaseTest {

    @Test
    fun When_GetSearchResults_Returns_FlowListArticleEntity_Test() {

        val mockStore: NewsArticleStore = mockk()
        val mockResult: Flow<List<ArticleEntity>> = mockk()

        every { mockStore.getLatestSearchResults(any()) } returns mockResult

        val useCase = GetSearchResultsUseCase(mockStore)
        val res = useCase.execute("abc")
        assertEquals(res, mockResult)
    }
}