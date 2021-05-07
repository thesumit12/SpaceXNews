package com.slakra.data.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "news_article")
data class NewsArticleModel(

    @SerializedName("id")
    @PrimaryKey
    @NonNull
    val articleId: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("imageUrl")
    val imageUrl: String,

    @SerializedName("summary")
    val summary: String
)
