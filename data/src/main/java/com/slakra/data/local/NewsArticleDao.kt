package com.slakra.data.local

import androidx.room.Dao
import androidx.room.Query
import com.slakra.data.model.NewsArticleModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsArticleDao: RoomBaseDao<NewsArticleModel> {

    @Query("SELECT * FROM news_article WHERE title LIKE '%' || :input || '%'")
    fun getSearchResults(input: String): Flow<List<NewsArticleModel>>
}