package com.slakra.spacexnews

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.slakra.common.BaseViewModel
import com.slakra.common.ProgressState
import com.slakra.common.utils.CoroutineContextProvider
import com.slakra.domain.arch.ResultState
import com.slakra.domain.entity.ArticleEntity
import com.slakra.domain.usecase.GetNewsArticleFromServerUseCase
import com.slakra.domain.usecase.GetSearchResultsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
        private val getNewsArticleFromServerUseCase: GetNewsArticleFromServerUseCase,
        private val getSearchResultsUseCase: GetSearchResultsUseCase,
        private val contextProvider: CoroutineContextProvider) : BaseViewModel() {

    val searchString = ObservableField("")
    private val _articleLiveData = MutableLiveData<List<ArticleEntity>>()
    val articleLiveData: LiveData<List<ArticleEntity>>
    get() = _articleLiveData

    fun getNewsArticles() {
        _state.value = ProgressState.LOADING
        viewModelScope.launch(contextProvider.Main) {
            when (val result = getNewsArticleFromServerUseCase.execute()) {
                is ResultState.Success<List<ArticleEntity>> -> {
                    _state.value = ProgressState.SUCCESS
                    _articleLiveData.value = result.data!!
                    searchArticle()
                }
                else -> {
                    _state.value = ProgressState.ERROR
                }
            }
        }
    }


    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun searchArticle() {
        viewModelScope.launch(contextProvider.Main) {
            getSearchStateFlow()
                    .debounce(300)
                    .distinctUntilChanged()
                    .flatMapLatest { query ->
                        getSearchResultsUseCase.execute(query)
                    }.collect {
                        _articleLiveData.value = it
                    }
        }

    }

    private fun getSearchStateFlow(): StateFlow<String> {
        val query = MutableStateFlow("")
        searchString.addOnPropertyChanged {
            query.value = it.get() ?: ""
        }
        return query
    }
}