package com.slakra.spacexnews

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.slakra.common.BaseActivity
import com.slakra.spacexnews.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main), NewsItemClickListener {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: ActivityMainBinding

    override fun initComponents(savedInstanceState: Bundle?, binding: ActivityMainBinding) {
        this.binding = binding
        binding.viewModel = viewModel
        viewModel.getNewsArticles()
        initializeNewsView()
        observeLiveEvents()
    }

    private fun initializeNewsView() {
        newsAdapter = NewsAdapter(this)
        binding.rvNewsFeed.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = newsAdapter
        }
    }

    private fun observeLiveEvents() {
        viewModel.articleLiveData.observe(this, {
            if (it.isEmpty()) {
                binding.layoutNoResult.visibility = View.VISIBLE
            }else {
                binding.layoutNoResult.visibility = View.GONE
            }
            newsAdapter.notifyDataSetChanged()
            newsAdapter.setHighlightText(viewModel.searchString.get() ?: "")
            newsAdapter.submitList(it)
        })
    }

    override fun onNewsItemClick(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}