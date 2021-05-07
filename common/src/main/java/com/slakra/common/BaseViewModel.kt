package com.slakra.common

import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    protected val _state = MutableLiveData<ProgressState>()
    val state: LiveData<ProgressState>
        get() = _state

    @Suppress("UNCHECKED_CAST")
    fun <T : Observable> T.addOnPropertyChanged(callback: (T) -> Unit) =
            object : Observable.OnPropertyChangedCallback() {
                @Suppress("UNCHECKED_CAST")
                override fun onPropertyChanged(observable: Observable?, i: Int) =
                        callback(observable as T)
            }.also { addOnPropertyChangedCallback(it) }
}