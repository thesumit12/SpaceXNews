package com.slakra.data.repository

import com.slakra.data.TestCoroutineRule
import com.slakra.data.local.NewsArticleDao
import com.slakra.data.mapper.NewsArticleMapper
import com.slakra.data.model.NewsArticleModel
import com.slakra.domain.entity.ArticleEntity
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsArticleStoreImplTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun When_GetLatestSearchResults_Returns_ListArticleEntity_Test() {

        val mockDao: NewsArticleDao = mockk()
        val mapper = NewsArticleMapper()
        val mockSearchList: Flow<List<NewsArticleModel>> = createMockList()
        val item1 = ArticleEntity("id1", "title1", "url1", "imageUrl1", "summary1")

        every { mockDao.getSearchResults(any()) } returns mockSearchList

        val repo = NewsArticleStoreImpl(mockDao, mapper)

        val res = repo.getLatestSearchResults("abc")
        testCoroutineRule.runBlockingTest {
            res.collect {
                assertEquals(it[0], item1)
            }
        }
    }

    private fun createMockList(): Flow<List<NewsArticleModel>> = flow {
        val item1 = NewsArticleModel("id1", "title1", "url1", "imageUrl1", "summary1")
        val item2 = NewsArticleModel("id2", "title2", "url2", "imageUrl2", "summary2")
        val item3 = NewsArticleModel("id3", "title3", "url3", "imageUrl3", "summary3")

        val list = arrayListOf(item1, item2, item3)

        emit(list)
    }
}