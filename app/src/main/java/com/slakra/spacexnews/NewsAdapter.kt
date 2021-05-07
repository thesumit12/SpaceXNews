package com.slakra.spacexnews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.slakra.domain.entity.ArticleEntity
import com.slakra.spacexnews.databinding.LayoutNewsItemBinding

class NewsAdapter(private val listener: NewsItemClickListener):
        ListAdapter<ArticleEntity, NewsAdapter.NewsItemViewHolder>(newsDiffUtil) {
    private var highlightText: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        val binding = DataBindingUtil.inflate<LayoutNewsItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.layout_news_item,
            parent,
            false
        )
        return NewsItemViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        holder.bind(getItem(position), highlightText)
    }

    fun setHighlightText(input: String) {
        highlightText = input
    }

    class NewsItemViewHolder(private val binding: LayoutNewsItemBinding,
    private val listener: NewsItemClickListener): RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: ArticleEntity, highlightText: String) {
            binding.articleEntity = entity
            binding.searchString = highlightText
            binding.listener = listener
            binding.executePendingBindings()
        }
    }

    companion object {
        private val newsDiffUtil = object : DiffUtil.ItemCallback<ArticleEntity>() {

            override fun areItemsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ArticleEntity,
                newItem: ArticleEntity
            ): Boolean = oldItem == newItem
        }
    }
}