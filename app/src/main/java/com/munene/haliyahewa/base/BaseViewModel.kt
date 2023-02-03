package com.munene.haliyahewa.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren

open class BaseViewModel : ViewModel() {

    private val mainJob = SupervisorJob()
    val uiScope = CoroutineScope(Dispatchers.Main + mainJob)
    val bgScope = CoroutineScope(Dispatchers.IO + mainJob)

    fun <R> bindObserver(observer: MediatorLiveData<R>, source: LiveData<R>) {
        observer.apply {
            addSource(source) {
                postValue(it)
            }
        }
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        mainJob.cancelChildren()
    }
}
