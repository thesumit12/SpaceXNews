package com.slakra.data.mapper

import com.slakra.data.model.NewsArticleModel
import com.slakra.domain.entity.ArticleEntity

class NewsArticleMapper: RemoteToEntityMapper<NewsArticleModel, ArticleEntity> {

    override fun map(remote: NewsArticleModel): ArticleEntity {
        return ArticleEntity(id = remote.articleId, title = remote.title,
        url = remote.url, imageUrl = remote.imageUrl, summary = remote.summary)
    }
}