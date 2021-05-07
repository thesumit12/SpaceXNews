package com.slakra.domain.usecase


import com.slakra.domain.arch.ResultState
import com.slakra.domain.entity.ArticleEntity
import com.slakra.domain.repository.ArticleRepo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetNewsArticleFromServerUseCaseTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun When_getArticlesFromServer_Returns_ResultStateListArticleEntity_Test() {

        testCoroutineRule.runBlockingTest {
            val articleRepo: ArticleRepo = mockk()
            val mockResult: ResultState<List<ArticleEntity>> = mockk()
            coEvery { articleRepo.getNewsArticles() } returns mockResult
            val useCase = GetNewsArticleFromServerUseCase(articleRepo)
            val res = useCase.execute()
            assertEquals(res, mockResult)
        }

    }
}