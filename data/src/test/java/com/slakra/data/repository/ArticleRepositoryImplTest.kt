package com.slakra.data.repository

import com.slakra.common.utils.CoroutineContextProvider
import com.slakra.data.TestContextProvider
import com.slakra.data.TestCoroutineRule
import com.slakra.data.local.NewsArticleDao
import com.slakra.data.mapper.NewsArticleMapper
import com.slakra.data.model.NewsArticleModel
import com.slakra.data.remote.NewsArticleService
import com.slakra.domain.arch.ResultState
import com.slakra.domain.entity.ArticleEntity
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class ArticleRepositoryImplTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun getNewsArticleSuccessTest() {
        testCoroutineRule.runBlockingTest {
            val remote: NewsArticleService = mockk()
            val newsArticleDao: NewsArticleDao = mockk()
            val articleMapper = NewsArticleMapper()
            val contextProvider: CoroutineContextProvider = TestContextProvider()

            val item1 = NewsArticleModel("id1", "title1", "url1", "imageUrl1", "summary1")
            val item2 = NewsArticleModel("id2", "title2", "url2", "imageUrl2", "summary2")
            var mockResponse: Response<List<NewsArticleModel>> = Response.success(arrayListOf(item1, item2))

            coEvery { remote.getNewsArticles() } returns mockResponse
            every { newsArticleDao.insertAllWithReplaceStrategy(any()) } just runs

            val repo = ArticleRepositoryImpl(remote, newsArticleDao, articleMapper, contextProvider)

            when(val res = repo.getNewsArticles()) {
                is ResultState.Success<List<ArticleEntity>> -> {
                    assertEquals(res.data.size, 2)
                }
                else -> {

                }
            }

            mockResponse = Response.success(null)
            coEvery { remote.getNewsArticles() } returns mockResponse
            val res = repo.getNewsArticles()
            assert(res is ResultState.InvalidData)
        }

    }

    @Test
    fun getNewsArticleFailureTest() {
        testCoroutineRule.runBlockingTest {
            val remote: NewsArticleService = mockk()
            val newsArticleDao: NewsArticleDao = mockk()
            val articleMapper = NewsArticleMapper()
            val contextProvider: CoroutineContextProvider = TestContextProvider()

            val mockResponse: Response<List<NewsArticleModel>> = mockk()
            coEvery { remote.getNewsArticles() } returns mockResponse
            every { mockResponse.isSuccessful } returns false
            every { mockResponse.code() } returns 403
            every { mockResponse.message() } returns "forbidden"

            val repo = ArticleRepositoryImpl(remote, newsArticleDao, articleMapper, contextProvider)

            var res = repo.getNewsArticles()
            assert(res is ResultState.HttpErrors.ResourceForbidden)

            every { mockResponse.code() } returns 404
            every { mockResponse.message() } returns "ResourceNotFound"
            res = repo.getNewsArticles()
            assert(res is ResultState.HttpErrors.ResourceNotFound)
            var message = (res as ResultState.HttpErrors.ResourceNotFound).exceptionMsg
            assertEquals(message, "ResourceNotFound")

            every { mockResponse.code() } returns 500
            every { mockResponse.message() } returns "InternalServerError"
            res = repo.getNewsArticles()
            assert(res is ResultState.HttpErrors.InternalServerError)
            message = (res as ResultState.HttpErrors.InternalServerError).exceptionMsg
            assertEquals(message, "InternalServerError")

            every { mockResponse.code() } returns 502
            every { mockResponse.message() } returns "BadGateWay"
            res = repo.getNewsArticles()
            assert(res is ResultState.HttpErrors.BadGateWay)
            message = (res as ResultState.HttpErrors.BadGateWay).exceptionMsg
            assertEquals(message, "BadGateWay")

            every { mockResponse.code() } returns 301
            every { mockResponse.message() } returns "ResourceRemoved"
            res = repo.getNewsArticles()
            assert(res is ResultState.HttpErrors.ResourceRemoved)
            message = (res as ResultState.HttpErrors.ResourceRemoved).exceptionMsg
            assertEquals(message, "ResourceRemoved")

            every { mockResponse.code() } returns 302
            every { mockResponse.message() } returns "RemovedResourceFound"
            res = repo.getNewsArticles()
            assert(res is ResultState.HttpErrors.RemovedResourceFound)
            message = (res as ResultState.HttpErrors.RemovedResourceFound).exceptionMsg
            assertEquals(message, "RemovedResourceFound")

            every { mockResponse.code() } returns 800
            every { mockResponse.message() } returns "Unknown Error"
            res = repo.getNewsArticles()
            assert(res is ResultState.Error)
            message = (res as ResultState.Error).errorMsg
            assertEquals(message, "Unknown Error")
        }
    }
}